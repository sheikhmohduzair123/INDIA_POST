package com.services.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

import javax.net.ssl.SSLContext;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.constants.Status;
import com.domain.Control;
import com.domain.RmsTable;
import com.domain.User;
import com.repositories.ControlRepository;
import com.repositories.RmsRepository;
import com.repositories.SUserRepository;
import com.services.UserRegistrationService;
import com.util.MailEngine;
import com.util.UpdatePasswordError;
import com.vo.CreateUserVo;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	@Autowired
	private SUserRepository userRepository;

	/*
	 * @Autowired private ParcelService parcelService;
	 */

	@Value("${appUrl}")
	private String appUrl;

	@Value("${server.port}")
	private String serverPort;

	@Autowired
	private MailEngine mailEngine;

	@Autowired
	private UpdatePasswordError message;

	@Autowired
	private RmsRepository rmsRepository;
	@Autowired
	private ControlRepository controlRepository;

	private static String applicationSecret = "asd67@we";

	// Register User On Server
	@Override
	public String registerUser(CreateUserVo userVo,User loginedUser) {
		String activationCode = createRegistrationToken(userVo.getEmail());
		//User loginedUserObj=userRepository.getOne(loginedUser.getId());
		try {
			String password = RandomStringUtils.randomAlphanumeric(9); // Generate Random alphanumeric password
			Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(userVo.getDateOfBirth());

			User user=new User();

			// Check if user already exists
			if (userRepository.findUserByEmailContainingIgnoreCaseAndStatus(userVo.getEmail(),Status.ACTIVE) == null)
			{
					if((userVo.getRole().getName().equals("ROLE_FRONT_DESK_USER"))||(userVo.getRole().getName().equals("ROLE_PO_USER")))
					{
						user.setPostalCode(Long.parseLong(userVo.getPostalCode()));
					}

					if(userVo.getRole().getName().equals("ROLE_RMS_USER"))
					{
							user.setRmsId(Long.parseLong(userVo.getRmsId()));
					}

							user.setFirstLogin(true);
							user.setEmail(userVo.getEmail());
							user.setName(userVo.getName());
							user.setRole(userVo.getRole());
							user.setUsername(userVo.getEmail());
							user.setDob(dob);
							user.setStatus(Enum.valueOf(Status.class, "ACTIVE"));
							
							user.setAddressLine1(userVo.getAddressLine1());
							user.setAddressLine2(userVo.getAddressLine2());
							user.setMobileNumber(userVo.getMobileNumber());
							user.setActivationCode(activationCode);
							user.setCredentialsExpired(false);
							BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
							user.setPassword(bc.encode(password));

							userRepository.save(user);

							sendRegistrationMail(user, activationCode, password);
							List<Control> controlList = controlRepository.findAll();
							Control control = controlList.get(0);
					        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
							control.setServerMasterDataUpdateTimestamp(timestamp);
							controlRepository.save(control);


				}
			else {
				return message.getEmailAlreadyExits();
			}
		} catch (Exception e) {
			e.printStackTrace();
			return message.getSomethingGotWrong();
		}
		return message.getMailsent();
	}


	///Update user

	@Override
	public void updateUser(CreateUserVo userVo,Integer uId,User loginedUser) {
		try {
		User oldUserObj=userRepository.findById(uId).orElse(null);
			User newUserObj=new User();
			Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(userVo.getDateOfBirth());

			if((userVo.getRole().getName().equals("ROLE_FRONT_DESK_USER"))||(userVo.getRole().getName().equals("ROLE_PO_USER")))
			{

				newUserObj.setPostalCode(Long.parseLong(userVo.getPostalCode()));
				newUserObj.setRmsId(null);
			}
		   if(userVo.getRole().getName().equals("ROLE_ADMIN"))
		    {

			   newUserObj.setPostalCode(0);

			   newUserObj.setRmsId(null);
			}
		  if(userVo.getRole().getName().equals("ROLE_RMS_USER"))
			{
					    newUserObj.setPostalCode(0);

						newUserObj.setRmsId(Long.parseLong(userVo.getRmsId()));

			}
		            oldUserObj.setStatus(Enum.valueOf(Status.class, "DISABLED"));

		            newUserObj.setFirstLogin(oldUserObj.isFirstLogin());
					newUserObj.setActivationCode(oldUserObj.getActivationCode());
					newUserObj.setPassword(oldUserObj.getPassword());
					newUserObj.setCredentialsExpired(oldUserObj.isCredentialsExpired());
					newUserObj.setAccountExpired(oldUserObj.isAccountExpired());
					newUserObj.setAccountLocked(oldUserObj.isAccountLocked());
					newUserObj.setIdentificationId(oldUserObj.getIdentificationId());

					newUserObj.setLastLogin(oldUserObj.getLastLogin());
					newUserObj.setName(userVo.getName());

					newUserObj.setRole(userVo.getRole());
					newUserObj.setUsername(oldUserObj.getUsername());
					newUserObj.setEmail(oldUserObj.getEmail());
					newUserObj.setStatus(Status.ACTIVE);
					
					newUserObj.setAddressLine1(oldUserObj.getAddressLine1());
					newUserObj.setAddressLine2(oldUserObj.getAddressLine2());
					newUserObj.setMobileNumber(oldUserObj.getMobileNumber());
					newUserObj.setDob(dob);
					newUserObj.setEnabled(true);

					oldUserObj.setEnabled(false);

					userRepository.save(newUserObj);

			    List<Control> controlList = controlRepository.findAll();
				Control control = controlList.get(0);
		        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				control.setServerMasterDataUpdateTimestamp(timestamp);
				controlRepository.save(control);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}


	// Send Mail to user with user Email, Password and activation Code
	private void sendRegistrationMail(User serverUser, String activationCode, String password) {

		String link = appUrl + serverPort+ "/user/activateUser?registrationCode=" + activationCode + " \n Username : "
				+ serverUser.getEmail() + "\n Password : " + password + "\n";

		/*
		 * String link = appUrl + "user/activateUser?registrationCode=" + activationCode
		 * + " \n Username : " + serverUser.getEmail() + "\n Password : " + password +
		 * "\n";
		 */
		Locale locale = LocaleContextHolder.getLocale();
		mailEngine.sendEmail(serverUser.getEmail(), "Activation Link", link, null, "_forgotPassword.html", locale);
	}

	// Create User registration Link
	public String createRegistrationToken(String email) {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
		String activationToken = bc.encode(email + applicationSecret);
		return activationToken;
	}

	  public List<RmsTable> findAllRms(){
		   return rmsRepository.findRmsByStatus(Status.ACTIVE);
	   }


/*	@Override
	public String activateUser(String registrationCode, String cookieToken) throws Exception
	{
		String uri = serverUrl + "/server/activateSeverUser?registrationCode=" + registrationCode;
		Client sysClient = parcelService.getClientDetails();
		String command = "wmic csproduct get UUID";

		String machineId = "";
		Process p = Runtime.getRuntime().exec(command);
		BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
		String line;
		while ((line = input.readLine()) != null) {
			machineId += line;
		}
		input.close();
		RestTemplate restTemplate = restTemplate(sysClient.getClientId(), sysClient.getClientToken() + ";"
				+ machineId.substring(machineId.length() - 38, machineId.length() - 2));
	  //String status = restTemplate.getForObject(uri, String.class);
		ResponseEntity<String> status = restTemplate.exchange(uri, HttpMethod.POST,
				new HttpEntity<String>(createHeaders(cookieToken)), String.class);
		return status.getBody();
	}*/

	@Override
	public String activateUser(String registrationCode) throws Exception
	{
		User currentUser = userRepository.findUserByActivationCodeAndStatus(registrationCode, Status.ACTIVE);
		if (currentUser != null) {
			if (currentUser.isEnabled() != true) {
				currentUser.setEnabled(true);
				currentUser.setActivationCode(null);
				userRepository.save(currentUser);
			} else {
				return message.getUserAlreadyActive();
			}
		} else {
			return message.getSomethingGotWrong();
		}
		return "success";

	 }

	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory(String clientId, String password) throws Exception
	{
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
				= new HttpComponentsClientHttpRequestFactory();

		clientHttpRequestFactory.setHttpClient(httpClient(clientId,password));

		return clientHttpRequestFactory;
	}

	private HttpClient httpClient(String clientId, String paswword) throws Exception
	{
		CredentialsProvider credentialsProvider = new BasicCredentialsProvider();

		credentialsProvider.setCredentials(AuthScope.ANY,
				new UsernamePasswordCredentials(clientId,paswword));

		final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
		SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(null, acceptingTrustStrategy)
				.build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);

		HttpClient client = HttpClientBuilder
				.create()
				.setDefaultCredentialsProvider(credentialsProvider)
				.setSSLSocketFactory(socketFactory)
				.build();
		return client;
	}

	private RestTemplate restTemplate(String clientId, String clientToken) throws Exception {

		HttpComponentsClientHttpRequestFactory factory = getClientHttpRequestFactory(clientId, clientToken);
		return new RestTemplate(factory);
	}

	//create header for rest call
	private HttpHeaders createHeaders(String jwtToken)
	  { return new HttpHeaders() {
		  {
			  setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			  setContentType(MediaType.APPLICATION_JSON);
			  String authHeader = "Bearer " + jwtToken;
			  add("Authorization", authHeader );
	      }
	    };
	  }

	//update user activities

			@Override
			public void enableAndDisableUserService(Integer id,User loginedUser) {

				Optional<User> userList = userRepository.findById(id);
				User currentUser = userList.get();
				if(currentUser.isEnabled()==false) {
		     		currentUser.setEnabled(true);

				}else {
						currentUser.setEnabled(false);

				   	  }
					userRepository.save(currentUser);
				List<Control> controlList = controlRepository.findAll();
				Control control = controlList.get(0);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				control.setServerMasterDataUpdateTimestamp(timestamp);
				controlRepository.save(control);

			}


			@Override
			public void updateUserStatus(Integer id,User loginedUser) {
				Optional<User> userList = userRepository.findById(id);
				User currentUser = userList.get();
				currentUser.setStatus(Status.DISABLED);
				currentUser.setEnabled(false);
				userRepository.save(currentUser);

				List<Control> controlList = controlRepository.findAll();
				Control control = controlList.get(0);
				Timestamp timestamp = new Timestamp(System.currentTimeMillis());
				control.setServerMasterDataUpdateTimestamp(timestamp);
				controlRepository.save(control);
			}

			@Override
			public String resendMailUserService(Integer id,User loginedUser) {
				Optional<User> userList = userRepository.findById(id);
				User currentUser = userList.get();
				//if(currentUser.isEnabled()==true) {

				String activationCode = createRegistrationToken(currentUser.getEmail());
				try {
					String password = RandomStringUtils.randomAlphanumeric(9); // Generate Random alphanumeric password

					currentUser.setUsername(currentUser.getEmail());
					currentUser.setActivationCode(activationCode);
						BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
						currentUser.setPassword(bc.encode(password));
						sendRegistrationMail(currentUser, activationCode, password);
						currentUser.setFirstLogin(true);

						userRepository.save(currentUser);

					List<Control> controlList = controlRepository.findAll();
					Control control = controlList.get(0);
					Timestamp timestamp = new Timestamp(System.currentTimeMillis());
					control.setServerMasterDataUpdateTimestamp(timestamp);
					controlRepository.save(control);

				} catch (Exception e) {
					e.printStackTrace();
					return message.getSomethingGotWrong();
				}
				/*}else {

				}*/
				return message.getResentmail();

			}


			@Override
			public List<User> findAll(User loginedUser) {
				List<User> userList=userRepository.findByOrderByIdAsc();
				userList.remove(loginedUser);
				return userList;
			}


			@Override
			public List<User> findAllByEnableAccoutTrue(User loginedUser) {
				List<User> userListwithStatus=userRepository.findAllByEnabledAndStatusOrderByIdAsc(true,Status.ACTIVE);
				userListwithStatus.remove(loginedUser);
				return userListwithStatus;
			}


			@Override
			public List<User> findAllByEnableAccoutFalse(User loginedUser) {
				List<User> userListwithStatus=userRepository.findAllByEnabledOrderByIdAsc(false);
				userListwithStatus.remove(loginedUser);
				return userListwithStatus;
			}




	/*
	 * @Override public List<User> findAllByStatusActive(User loginedUser) {
	 *
	 * List<User>
	 * userListwithStatus=userRepository.findAllByEnabledAndStatusOrderByIdAsc(true,
	 * Status.ACTIVE); userListwithStatus.remove(loginedUser); return
	 * userListwithStatus; }
	 *
	 * @Override public List<User> findAllByStatusDeactive(User loginedUser) {
	 * List<User>
	 * userListwithStatus=userRepository.findAllByStatusOrderByIdAsc(Status.DISABLED
	 * ); userListwithStatus.remove(loginedUser); return userListwithStatus; }
	 */

}
