package com.services.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.transaction.Transactional;

import org.apache.commons.lang3.RandomStringUtils;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.constants.Status;
import com.controllers.UserController;
import com.domain.Control;
import com.domain.RmsTable;
import com.domain.Role;
import com.domain.User;
import com.repositories.ControlRepository;
import com.repositories.RmsRepository;
import com.repositories.RoleRepository;
import com.repositories.SUserRepository;
import com.services.UserRegistrationService;
import com.util.MailEngine;
import com.util.UpdatePasswordError;
import com.vo.CreateUserVo;
import com.vo.UserVo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {
	protected Logger log = LoggerFactory.getLogger(UserRegistrationServiceImpl.class);

	@Autowired
	private SUserRepository userRepository;

	@Value("${appUrl}")
	private String appUrl;

	@Value("${server.url}")
	private String serverUrl;

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

	@Autowired
	private  RoleRepository roleRepository;

	private static String applicationSecret = "asd67@we";

	// Register User On Server
	@Override
	@Transactional
	public String registerUser(CreateUserVo userVo,User loginedUser) {
		String activationCode = createRegistrationToken(userVo.getEmail());
		try {
			String password = RandomStringUtils.randomAlphanumeric(9); // Generate Random alphanumeric password
			Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(userVo.getDateOfBirth());
			Date date = new Date();
			User user=new User();

			Role role_rms = roleRepository.findOneByName("ROLE_RMS_USER");
			Role role_po = roleRepository.findOneByName("ROLE_PO_USER");
			Role role_frontdesk_user = roleRepository.findOneByName("ROLE_FRONT_DESK_USER");

			// Check if user already exists
			if (userRepository.findUserByEmailContainingIgnoreCaseAndStatus(userVo.getEmail(),Status.ACTIVE) == null)
			{
					if((userVo.getRole().equals(role_frontdesk_user.getDisplayName()))||(userVo.getRole().equals(role_po.getDisplayName())))
					{
						user.setPostalCode(Long.parseLong(userVo.getPostalCode()));
					}

					if(userVo.getRole().equals(role_rms.getDisplayName()))
					{
						String[] rmsdata = userVo.getRmsId().split(",");
						RmsTable rmsId = rmsRepository.findByRmsNameAndRmsTypeAndRmsAddress_PostalCodeAndStatus(rmsdata[0], rmsdata[1], Integer.parseInt(rmsdata[2]), Status.ACTIVE);
						user.setRmsId(rmsId.getId());
					}
							user.setFirstLogin(true);
							user.setEmail(userVo.getEmail().toLowerCase());
							user.setName(userVo.getName());
							Role role = roleRepository.findOneByDisplayName(userVo.getRole());
							user.setRole(role);
							user.setUsername(userVo.getEmail().toLowerCase());
							user.setDob(dob);
							user.setStatus(Enum.valueOf(Status.class, "ACTIVE"));
							user.setActivationCode(activationCode);
							user.setCredentialsExpired(false);
							BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
							user.setPassword(bc.encode(password));

							user.setCreatedBy(loginedUser.getId());
							user.setUpdatedBy(loginedUser.getId());
							user.setCreatedOn(date);
							user.setUpdatedOn(date);

							userRepository.save(user);

							sendRegistrationMail(user, activationCode, password);
							List<Control> controlList = controlRepository.findAll();
							Control control = controlList.get(0);
					        Timestamp timestamp = new Timestamp(user.getUpdatedOn().getTime());
							control.setServerMasterDataUpdateTimestamp(timestamp);
							controlRepository.save(control);
				}
			else {
				return message.getEmailAlreadyExits();
			}
		} catch (Exception e) {
			log.error("Error ocuured while updating user "+userVo.getEmail()+": ", e);
			return message.getSomethingGotWrong();
		}
		return message.getMailsent();
	}


	///Update user
	@Override
	@Transactional
	public CreateUserVo updateUser(CreateUserVo userVo,User loginedUser) {
		try {

			User olduser = userRepository.findUserByEmailContainingIgnoreCaseAndStatus(userVo.getEmail(), Status.ACTIVE);
			if(olduser==null) {
				return null;
			}
			Date date = new Date();
			RmsTable rmsId2=null;

			Role role_rms = roleRepository.findOneByName("ROLE_RMS_USER");
			Role role_po = roleRepository.findOneByName("ROLE_PO_USER");
			Role role_frontdesk_user = roleRepository.findOneByName("ROLE_FRONT_DESK_USER");

			if(!(userVo.getRmsId()==null)) {
				String[] rmsdata2 = userVo.getRmsId().split(",");
			     rmsId2 = rmsRepository.findByRmsNameAndRmsTypeAndRmsAddress_PostalCodeAndStatus(rmsdata2[0], rmsdata2[1], Integer.parseInt(rmsdata2[2]), Status.ACTIVE);
				}

			//if postal code or rms or role is changed, then new id is generated
			if(!((olduser.getRole().getDisplayName()).equals(userVo.getRole())) || (( (olduser.getRole().equals(role_po)) ||(olduser.getRole().equals(role_frontdesk_user))  ) && !((olduser.getPostalCode()+"").equals(userVo.getPostalCode()))) || (olduser.getRole().equals(role_rms) && !((olduser.getRmsId()).equals(rmsId2.getId())) ) )
			{
				User newUser = new User();


				Role role = roleRepository.findOneByDisplayName(userVo.getRole());
				newUser.setRole(role);

				if((role.equals(role_po))||(role.equals(role_frontdesk_user)))
				{
					newUser.setPostalCode(Long.parseLong(userVo.getPostalCode()));
				}

				if(role.equals(role_rms))
				{
					newUser.setRmsId(rmsId2.getId());
				}
				newUser.setAccountExpired(olduser.isAccountExpired());
				newUser.setAccountLocked(olduser.isAccountExpired());
				newUser.setActivationCode(olduser.getActivationCode());
				newUser.setCreatedBy(olduser.getCreatedBy());
				newUser.setCreatedOn(olduser.getCreatedOn());
				newUser.setCredentialsExpired(olduser.isCredentialsExpired());
				Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(userVo.getDateOfBirth());
				newUser.setDob(dob);
				newUser.setEmail(olduser.getEmail());
				newUser.setEnabled(olduser.isEnabled());
				newUser.setFirstLogin(olduser.isFirstLogin());
				newUser.setIdentificationId(olduser.getIdentificationId());
				newUser.setLastLogin(olduser.getLastLogin());
				newUser.setName(userVo.getName());
				newUser.setPassword(olduser.getPassword());
				newUser.setStatus(Status.ACTIVE);
				newUser.setUpdatedBy(loginedUser.getId());
				newUser.setUpdatedOn(date);
				newUser.setUsername(olduser.getUsername());
				userRepository.save(newUser);

				olduser.setStatus(Status.DISABLED);
				olduser.setUpdatedBy(loginedUser.getId());
				olduser.setUpdatedOn(date);
				userRepository.save(newUser);
				List<Control> controlList = controlRepository.findAll();
				Control control = controlList.get(0);
		        Timestamp timestamp = new Timestamp(date.getTime());
				control.setServerMasterDataUpdateTimestamp(timestamp);
				controlRepository.save(control);

				userVo.setDateOfBirth(newUser.getDob().toString());
				userVo.setEmail(newUser.getEmail());
				userVo.setName(newUser.getName());
				userVo.setRole(newUser.getRole().getDisplayName());
				userVo.setPostalCode(newUser.getPostalCode()+"");
				userVo.setRmsId(newUser.getRmsId()+"");

			}
			//else old id is updated & new entry is disabled one
			else if(!(olduser.getName().equals(userVo.getName())) || !(olduser.getDob().toString().equals(userVo.getDateOfBirth())))
			{
				User newUser = new User();
				newUser.setPostalCode(olduser.getPostalCode());
				newUser.setRole(olduser.getRole());
				newUser.setRmsId(olduser.getRmsId());
				newUser.setAccountExpired(olduser.isAccountExpired());
				newUser.setAccountLocked(olduser.isAccountExpired());
				newUser.setActivationCode(olduser.getActivationCode());
				newUser.setCreatedBy(olduser.getCreatedBy());
				newUser.setCreatedOn(olduser.getCreatedOn());
				newUser.setCredentialsExpired(olduser.isCredentialsExpired());
				newUser.setDob(olduser.getDob());
				newUser.setEmail(olduser.getEmail());
				newUser.setEnabled(olduser.isEnabled());
				newUser.setFirstLogin(olduser.isFirstLogin());
				newUser.setIdentificationId(olduser.getIdentificationId());
				newUser.setLastLogin(olduser.getLastLogin());
				newUser.setName(olduser.getName());
				newUser.setPassword(olduser.getPassword());
				newUser.setStatus(Status.DISABLED);
				newUser.setUpdatedBy(loginedUser.getId());
				newUser.setUpdatedOn(date);
				newUser.setUsername(olduser.getUsername());
				userRepository.save(newUser);

				olduser.setUpdatedBy(loginedUser.getId());
				olduser.setUpdatedOn(date);
				Date dob = new SimpleDateFormat("yyyy-MM-dd").parse(userVo.getDateOfBirth());
				olduser.setDob(dob);
				olduser.setName(userVo.getName());
				userRepository.save(newUser);
				List<Control> controlList = controlRepository.findAll();
				Control control = controlList.get(0);
		        Timestamp timestamp = new Timestamp(date.getTime());
				control.setServerMasterDataUpdateTimestamp(timestamp);
				controlRepository.save(control);

				userVo.setDateOfBirth(olduser.getDob().toString());
				userVo.setName(olduser.getName());
				userVo.setEmail(olduser.getEmail());
				userVo.setRole(olduser.getRole().getDisplayName());
				userVo.setPostalCode(olduser.getPostalCode()+"");
				userVo.setRmsId(olduser.getRmsId()+"");

			}
		} catch (Exception e) {
			log.error("Error ocuured while updating user "+userVo.getEmail()+": ", e);
		}
		return userVo;

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

	/*
	 * private HttpComponentsClientHttpRequestFactory
	 * getClientHttpRequestFactory(String clientId, String password) throws
	 * Exception { HttpComponentsClientHttpRequestFactory clientHttpRequestFactory =
	 * new HttpComponentsClientHttpRequestFactory();
	 *
	 * clientHttpRequestFactory.setHttpClient(httpClient(clientId,password));
	 *
	 * return clientHttpRequestFactory; }
	 *
	 * private HttpClient httpClient(String clientId, String paswword) throws
	 * Exception { CredentialsProvider credentialsProvider = new
	 * BasicCredentialsProvider();
	 *
	 * credentialsProvider.setCredentials(AuthScope.ANY, new
	 * UsernamePasswordCredentials(clientId,paswword));
	 *
	 * final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
	 * SSLContext sslContext = new SSLContextBuilder() .loadTrustMaterial(null,
	 * acceptingTrustStrategy) .build(); SSLConnectionSocketFactory socketFactory =
	 * new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
	 *
	 * HttpClient client = HttpClientBuilder .create()
	 * .setDefaultCredentialsProvider(credentialsProvider)
	 * .setSSLSocketFactory(socketFactory) .build(); return client; }
	 */
	/*
	 * private RestTemplate restTemplate(String clientId, String clientToken) throws
	 * Exception {
	 *
	 * HttpComponentsClientHttpRequestFactory factory =
	 * getClientHttpRequestFactory(clientId, clientToken); return new
	 * RestTemplate(factory); }
	 *
	 * //create header for rest call private HttpHeaders createHeaders(String
	 * jwtToken) { return new HttpHeaders() { {
	 * setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
	 * setContentType(MediaType.APPLICATION_JSON); String authHeader = "Bearer " +
	 * jwtToken; add("Authorization", authHeader ); } }; }
	 *
	 */	//update user activities

			@Override
			@Transactional
			public UserVo enableAndDisableUserService(String email,User loginedUser) {

				User user = userRepository.findUserByEmailContainingIgnoreCaseAndStatus(email, Status.ACTIVE);
				if(user==null) {
					return null;
				}
				Date date = new Date();

				if(user.isEnabled()==false) {
					user.setEnabled(true);
					user.setUpdatedOn(date);
					user.setUpdatedBy(loginedUser.getId());
					user.setActivationCode(null);
					BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
					user.setPassword(bc.encode("111111"));
					user.setFirstLogin(true);
				}else {
					user.setEnabled(false);
					user.setUpdatedOn(date);
					user.setUpdatedBy(loginedUser.getId());
				}
				userRepository.save(user);

				List<Control> controlList = controlRepository.findAll();
				Control control = controlList.get(0);
				Timestamp timestamp = new Timestamp(date.getTime());
				control.setServerMasterDataUpdateTimestamp(timestamp);
				controlRepository.save(control);

				UserVo userVo=new UserVo();
				userVo.setStatus(user.getStatus());
				userVo.setEnabled(user.isEnabled());
				return userVo;
			}


			@Override
			@Transactional
			public UserVo updateUserStatus(String emailid,User loginedUser) {
				User user = userRepository.findUserByEmailContainingIgnoreCaseAndStatus(emailid, Status.ACTIVE);
				if(user == null)
				{
					return null;
				}
				else
				{
					user.setStatus(Status.DISABLED);
					user.setEnabled(false);
					user.setUpdatedOn(new Date());
					user.setUpdatedBy(loginedUser.getId());
					userRepository.save(user);
					List<Control> controlList = controlRepository.findAll();
					Control control = controlList.get(0);
					Timestamp timestamp = new Timestamp(user.getUpdatedOn().getTime());
					control.setServerMasterDataUpdateTimestamp(timestamp);
					controlRepository.save(control);

					UserVo userVo=new UserVo();
					userVo.setStatus(user.getStatus());
					userVo.setEnabled(user.isEnabled());
					return userVo;
				}
			}

			@Override
			public String resendMailUserService(String email,User loginedUser) {
				User currentUser = userRepository.findUserByEmailContainingIgnoreCaseAndStatusAndEnabled(email, Status.ACTIVE, true);
				//if(currentUser.isEnabled()==true) {
				if (currentUser == null)
				{
					return message.getSomethingGotWrong();
				}
				Date date = new Date();
				String activationCode = createRegistrationToken(currentUser.getEmail());
				//try {
					String password = RandomStringUtils.randomAlphanumeric(9); // Generate Random alphanumeric password

					currentUser.setUsername(currentUser.getEmail());
					currentUser.setActivationCode(activationCode);
						BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
						currentUser.setPassword(bc.encode(password));
						sendRegistrationMail(currentUser, activationCode, password);
						currentUser.setFirstLogin(true);
						currentUser.setUpdatedOn(date);
						currentUser.setUpdatedBy(loginedUser.getId());
						userRepository.save(currentUser);
					List<Control> controlList = controlRepository.findAll();
					Control control = controlList.get(0);
					Timestamp timestamp = new Timestamp(date.getTime());
					control.setServerMasterDataUpdateTimestamp(timestamp);
					controlRepository.save(control);

				//}
					/*catch (Exception e) {
					e.printStackTrace();
					return message.getSomethingGotWrong();
				}*/
				/*}else {

				}*/
				return message.getResentmail();

			}


	/*
	 * @Override public List<User> findAll(User loginedUser) { List<User>
	 * userList=userRepository.findByOrderByIdAsc(); userList.remove(loginedUser);
	 * return userList; }
	 *
	 *
	 * @Override public List<User> findAllByEnableAccoutTrue(User loginedUser) {
	 * List<User>
	 * userListwithStatus=userRepository.findAllByEnabledAndStatusOrderByIdAsc(true,
	 * Status.ACTIVE); userListwithStatus.remove(loginedUser); return
	 * userListwithStatus; }
	 *
	 *
	 * @Override public List<User> findAllByEnableAccoutFalse(User loginedUser) {
	 * List<User>
	 * userListwithStatus=userRepository.findAllByEnabledOrderByIdAsc(false);
	 * userListwithStatus.remove(loginedUser); return userListwithStatus; }
	 */

			@Override
			public List<UserVo> findUserListByStatus(User loginedUser, String status) {
		  		List<User> activeuserlist = userRepository.findAllByEnabledAndStatusOrderByIdAsc(true, Status.ACTIVE);

		  		if(status.equalsIgnoreCase("active")) {
					activeuserlist.sort(Comparator.comparing(User::getUpdatedOn).reversed());
					if(activeuserlist.contains(loginedUser))
						activeuserlist.remove(loginedUser);

					List<UserVo> activeuserVolist = new ArrayList<UserVo>();
					for (User u : activeuserlist) {
						UserVo userVo = new UserVo();
						userVo.setEmail(u.getEmail());
						userVo.setDob(u.getDob());
						userVo.setEnabled(u.isEnabled());
						userVo.setName(u.getName());
						userVo.setPostalCode(u.getPostalCode());
						if(!(u.getRmsId() == null))
						{
							RmsTable rms = rmsRepository.findRmsTableByIdAndStatus(u.getRmsId(), Status.ACTIVE);
							userVo.setPostalCode(rms.getRmsAddress().getPostalCode());
							userVo.setRmsName(rms.getRmsName());
							userVo.setRmsType(rms.getRmsType());
						}
						userVo.setRole(u.getRole());
						userVo.setUsername(u.getUsername());
						userVo.setStatus(u.getStatus());
						activeuserVolist.add(userVo);
					}
					return activeuserVolist;
				}

	  			List<User> disableduserlist = userRepository.findAllByEnabledAndStatusOrderByIdAsc(false, Status.ACTIVE);
				if(status.equalsIgnoreCase("disabled")) {
					disableduserlist.sort(Comparator.comparing(User::getUpdatedOn).reversed());
					if(disableduserlist.contains(loginedUser))
						disableduserlist.remove(loginedUser);

					List<UserVo> disableduserVolist = new ArrayList<UserVo>();
					for (User u : disableduserlist) {
						UserVo userVo = new UserVo();
						userVo.setEmail(u.getEmail());
						userVo.setDob(u.getDob());
						userVo.setEnabled(u.isEnabled());
						userVo.setName(u.getName());
						userVo.setPostalCode(u.getPostalCode());
						if(!(u.getRmsId() == null))
						{
							RmsTable rms = rmsRepository.findRmsTableByIdAndStatus(u.getRmsId(), Status.ACTIVE);
							userVo.setPostalCode(rms.getRmsAddress().getPostalCode());
							userVo.setRmsName(rms.getRmsName());
							userVo.setRmsType(rms.getRmsType());
						}
						userVo.setRmsId(u.getRmsId());
						userVo.setRole(u.getRole());
						userVo.setUsername(u.getUsername());
						userVo.setStatus(u.getStatus());
						disableduserVolist.add(userVo);
					}

					return disableduserVolist;
				}

				List<User> deleteduserlist = userRepository.findAllByEnabledAndStatusOrderByIdAsc(false, Status.DISABLED);

				if(status.equalsIgnoreCase("deleted")) {
					deleteduserlist.sort(Comparator.comparing(User::getUpdatedOn).reversed());
					if(deleteduserlist.contains(loginedUser))
						deleteduserlist.remove(loginedUser);

					List<UserVo> deleteduserVolist = new ArrayList<UserVo>();
					for (User u : deleteduserlist) {
						UserVo userVo = new UserVo();
						userVo.setEmail(u.getEmail());
						userVo.setDob(u.getDob());
						userVo.setEnabled(u.isEnabled());
						userVo.setName(u.getName());
						userVo.setPostalCode(u.getPostalCode());
						if(!(u.getRmsId() == null))
						{
							RmsTable rms = rmsRepository.findRmsTableByIdAndStatus(u.getRmsId(), Status.ACTIVE);
							userVo.setPostalCode(rms.getRmsAddress().getPostalCode());
							userVo.setRmsName(rms.getRmsName());
							userVo.setRmsType(rms.getRmsType());
						}
						userVo.setRmsId(u.getRmsId());
						userVo.setRole(u.getRole());
						userVo.setUsername(u.getUsername());
						userVo.setStatus(u.getStatus());
						deleteduserVolist.add(userVo);
					}

					return deleteduserVolist;
			  	}

				if(status.equalsIgnoreCase("all")) {
					List<User> list = new ArrayList<User>();

					list.addAll(activeuserlist);
					list.addAll(disableduserlist);
					list.addAll(deleteduserlist);

					list.sort(Comparator.comparing(User::getUpdatedOn).reversed());
					if(list.contains(loginedUser))
						list.remove(loginedUser);

					List<UserVo> Volist = new ArrayList<UserVo>();
					for (User u : list) {
						UserVo userVo = new UserVo();
						userVo.setEmail(u.getEmail());
						userVo.setDob(u.getDob());
						userVo.setEnabled(u.isEnabled());
						userVo.setName(u.getName());
						userVo.setPostalCode(u.getPostalCode());
						if(!(u.getRmsId() == null))
						{
							RmsTable rms = rmsRepository.findRmsTableByIdAndStatus(u.getRmsId(), Status.ACTIVE);
							userVo.setPostalCode(rms.getRmsAddress().getPostalCode());
							userVo.setRmsName(rms.getRmsName());
							userVo.setRmsType(rms.getRmsType());
						}
						userVo.setRmsId(u.getRmsId());
						userVo.setRole(u.getRole());
						userVo.setUsername(u.getUsername());
						userVo.setStatus(u.getStatus());
						Volist.add(userVo);
						}
						return Volist;
				  }
				return null;
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
