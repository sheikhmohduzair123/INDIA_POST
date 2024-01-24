package com.services.impl;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletResponse;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.constants.Status;
import com.domain.Client;
import com.domain.PasswordHistory;
import com.domain.User;
import com.repositories.ClientRepository;
import com.repositories.SUserPasswordRepository;
import com.repositories.SUserRepository;
import com.services.ParcelService;
import com.services.SUserService;
import com.util.ServerTokenUtils;
import com.util.UpdatePassword;
import com.util.UpdatePasswordError;
import com.vo.UserVo;



@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	/**
	 * Additional logger to use when no user details handler is found for a
	 * request.
	 */
	protected Logger log = LoggerFactory
			.getLogger(UserDetailsServiceImpl.class);
	@Autowired
	private SUserService suserService;// code7

	@Autowired
	private SUserRepository sUserRepository;

	@Autowired
	private SUserPasswordRepository userPasswordRepository;

	@Autowired
	UpdatePasswordError updatePasswordError;

	@Autowired
	private ParcelService parcelService;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	ServerTokenUtils tokenUtils;


	@Value("${server.url}")
	private String serverUrl;

	@Value("${spring.profiles.active}")
	private String activeProfile;

	public boolean getUserDetailsLocal() {
		List<User> c = sUserRepository.findUserByEnabledAndStatus(true, Status.ACTIVE);
		if (c.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public User loadUserByUsername(String userName)	throws UsernameNotFoundException {

		User user = suserService.findUserByEmail(userName);
		if (user == null) {

			log.error("User not found with Name: " + userName);
			throw new UsernameNotFoundException("UserName " + userName	+ " not found");
		}

		log.info("Returning user with the UserName: " + user.getUsername() + " Authorities: " + user.getAuthorities());
		return user;

	}


	public String getUserByUsername(String username) {
		User currentUser = sUserRepository.findUserByUsernameAndStatus(username, Status.ACTIVE);
		return currentUser.getName();
	}


	public String userPasswordUpdate(String loginedUser, UpdatePassword updatePassword, HttpServletResponse res) throws Exception {
		log.info("inside userPasswordUpdate");

		String newPassword = updatePassword.getNewPassword();
		StringBuilder patternBuilder = new StringBuilder("((?=.*[a-z])(?=.*[0-9])(?=.*[@#$%])(?=.*[A-Z]).{6,15})");
		String pattern = patternBuilder.toString();
		Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(newPassword);

		User currentUser = sUserRepository.findUserByUsernameAndStatus(loginedUser, Status.ACTIVE);
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder(4);
		//check if pattern validation matches
		if(m.matches()) {
		// Check given old password = old password from user table
		if (bc.matches(updatePassword.getOldPassword(),currentUser.getPassword())) {
				if (!updatePassword.getOldPassword().equals(updatePassword.getNewPassword())) {
					if (!isPreviouslyUsed(currentUser, newPassword)) {

						// Update password in user Table
						String changedPassword = bc.encode(newPassword);
						currentUser.setPassword(changedPassword);

						//changing password at Sever First
						Client sysClient = parcelService.getClientDetails();
						String command = "wmic csproduct get UUID";

						String machineId = "";
						Process process = Runtime.getRuntime().exec(command);
						BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
						String line;
						while ((line = input.readLine()) != null) {
							machineId += line;
						}
						input.close();
						RestTemplate restTemplate = restTemplate(sysClient.getClientId(), sysClient.getClientToken() + ";"
								+ machineId.substring(machineId.length() - 38, machineId.length() - 2));

						UserVo userVo = new UserVo();
						userVo.setEmail(currentUser.getEmail());
						userVo.setAccountExpired(currentUser.isAccountExpired());
						userVo.setAccountLocked(currentUser.isAccountLocked());
						userVo.setCredentialsExpired(currentUser.isCredentialsExpired());
						userVo.setDob(currentUser.getDob());
						userVo.setEnabled(currentUser.isEnabled());
						userVo.setFirstLogin(currentUser.isFirstLogin());
						userVo.setId(currentUser.getId());
						userVo.setIdentificationId(currentUser.getIdentificationId());
						userVo.setLastLogin(currentUser.getLastLogin());
						userVo.setName(currentUser.getName());
						userVo.setPassword(changedPassword);
						userVo.setPostalCode(currentUser.getPostalCode());
						userVo.setRmsId(currentUser.getRmsId());
						userVo.setRole(currentUser.getRole());
						userVo.setStatus(currentUser.getStatus());
						
						userVo.setAddressLine1(currentUser.getAddressLine1());
						userVo.setAddressLine2(currentUser.getAddressLine2());
						userVo.setMobileNumber(currentUser.getMobileNumber());
						userVo.setUsername(currentUser.getUsername());
						ResponseEntity<UserVo> usr;
						try {
							usr=restTemplate.exchange(serverUrl+"/server/saveChangePassword", HttpMethod.POST, new HttpEntity<Object>(userVo,createHeaders(ServerTokenUtils.globalServerToken)),UserVo.class);
						}
						catch(Exception e)
						{

							log.error("Error connecting to server api:",e);

							if(e.getMessage().equalsIgnoreCase("401 Unauthorized")) {
								List<Client> client =clientRepository.findByClientStatusAndStatus("approved", Status.ACTIVE);
								ServerTokenUtils.globalServerToken=tokenUtils.JwtAuthenticate(client.get(0), res);

							}
							usr=restTemplate.exchange(serverUrl+"/server/saveChangePassword", HttpMethod.POST, new HttpEntity<Object>(userVo,createHeaders(ServerTokenUtils.globalServerToken)),UserVo.class);
						}

						//rework block
						// Save password in password_history table
						createUserPasswordHistory(
								bc.encode(updatePassword.getNewPassword()),
								currentUser);

						//saving in client application
						sUserRepository.save(currentUser);
						log.info("Password Changed Successfully");
						return "{\"Status\": \"Success\"}";
					} else {
						log.error("Change Password Error::::"
								+ updatePasswordError.getCannotusepreviouslyused());
						return "{\"Status\": \"Failure\", \"Error\": \""
								+ updatePasswordError.getCannotusepreviouslyused()
								+ "\"}";
					}
				} else {
					log.error("Change Password Error::::"
							+ updatePasswordError.getCannotbeSameAsold());
					return "{\"Status\": \"Failure\", \"Error\": \""
							+ updatePasswordError.getCannotbeSameAsold() + "\"}";
				}

		} else {
			log.error("Change Password Error::::"
					+ updatePasswordError.getIncorrectPassword());
			return "{\"Status\": \"Failure\", \"Error\": \""
					+ updatePasswordError.getIncorrectPassword() + "\"}";
		}
		}
		else {
			log.error("Change Password Error::::"
					+ updatePasswordError.getPasswordValidationFailure());
			return "{\"Status\": \"Failure\", \"Error\": \""
					+ updatePasswordError.getPasswordValidationFailure() + "\"}";
		}
	}

	private boolean isPreviouslyUsed(User currentUser, String newPassword) {
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder(4);
		ArrayList<PasswordHistory> passwordHistoryList = userPasswordRepository
				.findTop5ByUserIdOrderByCreatedOn(currentUser.getId());
		log.debug("passwordHistoryList:"+passwordHistoryList);
		for (PasswordHistory history : passwordHistoryList) {
			if (bc.matches(newPassword, history.getPassword())) {
				log.debug("password matched");
				return true;
			}
		}
		return false;
	}

	// Getting Current Time Stamp
	private Timestamp currentTime() {
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		return timestamp;
	}

	// Storing password details to password_history table
	private void createUserPasswordHistory(String oldPassword, User currentUser) {
		PasswordHistory previousPassword = new PasswordHistory();
		previousPassword.setCreatedOn(currentTime());
		previousPassword.setUser(currentUser);
		previousPassword.setPassword(oldPassword);

		// Save previous password List
		userPasswordRepository.save(previousPassword);
	}

	public String resetPassword(User currentUser, String newPassword, HttpServletResponse res) throws Exception {

		log.info("inside resetPassword");
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder(4);
		if(currentUser.isFirstLogin()) {

		//	if (!bc.matches(newPassword, currentUser.getPassword())) {

			log.debug("checking previous 5 passwords history...");
				if (!isPreviouslyUsed(currentUser, newPassword)) {
					log.debug("changing password now");

					// Update password in user Table
					currentUser.setPassword(bc.encode(newPassword));

					if(activeProfile.equals("client"))
					{

						/*
						 * String cookieToken=getTokenFromCookie(req); if(cookieToken.isBlank()) return
						 * new ModelAndView("redirect:/login");
						 */

						Client sysClient = parcelService.getClientDetails();
						String command = "wmic csproduct get UUID";

						String machineId = "";
						Process process = Runtime.getRuntime().exec(command);
						BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));
						String line;
						while ((line = input.readLine()) != null) {
							machineId += line;
						}
						input.close();
						RestTemplate restTemplate = restTemplate(sysClient.getClientId(), sysClient.getClientToken() + ";"
								+ machineId.substring(machineId.length() - 38, machineId.length() - 2));

						UserVo userVo = new UserVo();
						userVo.setEmail(currentUser.getEmail());
						userVo.setAccountExpired(currentUser.isAccountExpired());
						userVo.setAccountLocked(currentUser.isAccountLocked());
						userVo.setCredentialsExpired(currentUser.isCredentialsExpired());
						userVo.setDob(currentUser.getDob());
						userVo.setEnabled(currentUser.isEnabled());
						userVo.setFirstLogin(false);
						userVo.setId(currentUser.getId());
						userVo.setIdentificationId(currentUser.getIdentificationId());
						userVo.setLastLogin(currentUser.getLastLogin());
						userVo.setName(currentUser.getName());
						userVo.setPassword(currentUser.getPassword());
						userVo.setPostalCode(currentUser.getPostalCode());
						userVo.setRmsId(currentUser.getRmsId());
						userVo.setRole(currentUser.getRole());
						userVo.setStatus(currentUser.getStatus());
						userVo.setUsername(currentUser.getUsername());
						
						userVo.setAddressLine1(currentUser.getAddressLine1());
						userVo.setAddressLine2(currentUser.getAddressLine2());
						userVo.setMobileNumber(currentUser.getMobileNumber());
						ResponseEntity<UserVo> usr;
						//user = restTemplate.postForObject(serverUrl + "/server/saveChangePassword", users,com.domain.User.class);
						try {
					 usr=restTemplate.exchange(serverUrl+"/server/saveChangePassword", HttpMethod.POST, new HttpEntity<Object>(userVo,createHeaders(ServerTokenUtils.globalServerToken)),UserVo.class);
						}
						catch(Exception e)
				        {

							log.error("Error connecting to server api:",e);
							  if(e.getMessage().equalsIgnoreCase("401 Unauthorized")) {
							  List<Client> client =clientRepository.findByClientStatusAndStatus("approved", Status.ACTIVE);
							  ServerTokenUtils.globalServerToken=tokenUtils.JwtAuthenticate(client.get(0), res);

							  }
								 usr=restTemplate.exchange(serverUrl+"/server/saveChangePassword", HttpMethod.POST, new HttpEntity<Object>(userVo,createHeaders(ServerTokenUtils.globalServerToken)),UserVo.class);
				        }
						UserVo uservo=usr.getBody();
					}

					createUserPasswordHistory(bc.encode(newPassword), currentUser);

					currentUser.setActivationCode(null);
					currentUser.setFirstLogin(false);
					sUserRepository.save(currentUser);
					log.info("Password Reset Successfully");
					return updatePasswordError.getPasswordresetsuccessful();

				} else {
					return updatePasswordError.getCannotusepreviouslyused();
				}
			/*
			 * } else { return updatePasswordError.getCannotbeSameAsold(); }
			 */
		}
		else {
			log.info("Error:: Old user trying to change password from first time change password page");
			return updatePasswordError.getIsOldUser();
		}

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
}
