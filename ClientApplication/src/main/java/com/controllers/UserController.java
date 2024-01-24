package com.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.constants.Status;
import com.domain.Client;
import com.domain.Role;
import com.repositories.ClientRepository;
import com.repositories.RoleRepository;
import com.services.DataSyncService;
import com.services.ParcelService;
import com.services.UserRegistrationService;
import com.services.impl.LoginAttemptService;
import com.services.impl.UserDetailsServiceImpl;
import com.util.ServerTokenUtils;
import com.util.UpdatePassword;
import com.util.UpdatePasswordError;

@Controller
@Profile({ "client", "server", "clientonly" })
@RequestMapping("/user")
public class UserController {

	protected Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDetailsServiceImpl userUpdateService;

	@Autowired
	private UpdatePasswordError messages;

	@Autowired
	private UserRegistrationService registrationService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ParcelService parcelService;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
	ServerTokenUtils tokenUtils;

	@Autowired
	LoginAttemptService loginAttemptService;

	@Autowired
	DataSyncService dataSyncService;

	@Value("${appUrl}")
	private String appUrl;

	@Value("${server.url}")
	private String serverUrl;

	@Value("${serverApp.url}")
	private String serverAppUrl;

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public String getForgotPassword(Model model, com.domain.User user) {
		model.addAttribute("user", user);
		return "redirect:" + serverAppUrl + "/user/forgotPassword";

	}

	/*
	 * @RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	 * public String forgotPassword(@Valid com.domain.User user, BindingResult
	 * bindingResult, Model model) {
	 *
	 * log.info("inside forgot password"); Locale locale =
	 * LocaleContextHolder.getLocale(); // validate if email is not blank try { //
	 * to-do // fetch user by emailId com.domain.User existingUser =
	 * userService.findUserByEmail(user.getEmail());// fetch by email if
	 * (bindingResult.hasErrors()) { return "forgotPassword"; } else { if
	 * (existingUser != null) { // String activationCode =
	 * createResetPasswordToken(existingUser); // save this activationCode in DB
	 * userService.sendEmail(existingUser); model.addAttribute("success",
	 * messages.getMailsent()); return "forgotPassword"; } else {
	 * model.addAttribute("error", messages.getWrongemail()); return
	 * "forgotPassword"; } } } catch (UsernameNotFoundException e) {
	 * log.error("error:", e); return "forgotPassword";
	 *
	 * } }
	 *
	 * @RequestMapping(value = "/resetPassword", method = RequestMethod.GET) public
	 * String resetPassword(@RequestParam("activationCode") String activationCode,
	 * Model model, com.domain.User user) {
	 * log.debug("inside resetPassword, activation:"); try { com.domain.User u =
	 * userService.findUserByActivationCode(activationCode); String code =
	 * u.getActivationCode(); if (code != null) { return "reset"; } else {
	 * model.addAttribute("message", messages.getLinkbroken()); return "403Page"; }
	 * } catch (Exception e) { log.error("error:", e); return "403Page"; } }
	 *
	 * @RequestMapping(value = "/resetPassword", method = RequestMethod.POST) public
	 * String resetPasswordSuccess(@ModelAttribute("activationCode") String
	 * activationCode,
	 *
	 * @ModelAttribute("newPassword") String newPassword, Model model) {
	 * log.debug("inside resetPasswordSuccess, activation:"); try
	 *
	 * {
	 *
	 * com.domain.User userObj =
	 * userService.findUserByActivationCode(activationCode);
	 *
	 * if (userObj != null) {
	 *
	 * String status = userUpdateService.resetPassword(userObj, newPassword); if
	 * (status.equals(messages.getPasswordresetsuccessful())) {
	 *
	 * model.addAttribute("successMessage", status); return "reset"; }
	 * model.addAttribute("message", status); return "reset";
	 *
	 * } else { model.addAttribute("error", messages.getCannotchangepassword());
	 * return "reset"; }
	 *
	 * } catch (Exception e) { log.error("error:", e); return "reset"; }
	 *
	 * }
	 */

	// Update Password
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public String updatePassword(@ModelAttribute UpdatePassword updatePassword, Principal principal,
			HttpServletResponse res) throws Exception {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		String status = userUpdateService.userPasswordUpdate(loginedUser.getUsername(), updatePassword, res);
		return status;

	}

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView changePassword(@ModelAttribute("newPassword") String newPassword, Model model,
			Principal principal, HttpServletRequest req, HttpServletResponse res) throws Exception {
		StringBuilder patternBuilder = new StringBuilder("((?=.*[a-z])(?=.*[0-9])(?=.*[@#$%])(?=.*[A-Z]).{6,15})");
		String pattern = patternBuilder.toString();
		Pattern p = Pattern.compile(pattern);
		Matcher m = p.matcher(newPassword);

		if (m.matches()) {

			log.debug("inside changePassword:::");
			com.domain.User user = (com.domain.User) ((Authentication) principal).getPrincipal();

			try {
				String status = userUpdateService.resetPassword(user, newPassword, res);

				if (status.equals(messages.getPasswordresetsuccessful())) {
					if (activeProfile.equals("client") || activeProfile.equals("clientOnly")) {
						loginAttemptService.syncBalance();
					}
					model.addAttribute("successMessage", status);
					return new ModelAndView("redirect:/login");

				} else if (status.equals(messages.getCannotbeSameAsold())) {
					model.addAttribute("Message", messages.getCannotbeSameAsold());
					return new ModelAndView("changePassword");
				} else if (status.equals(messages.getCannotusepreviouslyused())) {
					model.addAttribute("Message2", messages.getCannotusepreviouslyused());
					return new ModelAndView("changePassword");
				} else if (status.equals(messages.getIsOldUser())) {
					model.addAttribute("error", messages.getIsOldUser());
					return new ModelAndView("changePassword");
				} else {
					model.addAttribute("error", messages.getCannotchangepassword());
					return new ModelAndView("changePassword");
				}
			} catch (Exception e) {
				log.error("error:", e);
				model.addAttribute("error", messages.getCannotchangepassword());
				return new ModelAndView("changePassword");
			}
		}
		model.addAttribute("error", messages.getPasswordValidationFailure());
		return new ModelAndView("changePassword");
	}

	@RequestMapping("/getAllRoles")
	@ResponseBody
	public List<Role> getAllRoles() {
		List<Role> allRoles = roleRepository.findAll();
		return allRoles;
	}

	// Show user Registration Page
	@RequestMapping("/registerUserPage")
	public String createUserPage(@ModelAttribute com.domain.User user, Model model, Principal principal) {
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		model.addAttribute("user", loginedUser.getName());
		model.addAttribute("role", loginedUser.getRole().getName());
		return "createUser";
	}

	@RequestMapping(value = "/activateUser", method = RequestMethod.GET)
	public ModelAndView activateUser(@RequestParam String registrationCode, Model model)
			throws Exception {
		// String cookieToken = getTokenFromCookie(req);
		// String status =
		// registrationService.activateUser(registrationCode,cookieToken);
		// model.addAttribute("message", status);
		// return "redirect:/login";
		log.info("Returning to Activate User Page");

		String status = registrationService.activateUser(registrationCode);
		model.addAttribute("message", status);
		return new ModelAndView("activateUser");
	}

	@RequestMapping(value = "/syncUserDetails", method = RequestMethod.GET)
	public String syncUserDetails(HttpServletRequest req, HttpServletResponse res) throws Exception {
		log.info("Syncing user details before login");
		String status = null;
		try {
			status = dataSyncService.syncUserDetails(ServerTokenUtils.globalServerToken);
			if (status.equalsIgnoreCase("success"))
				log.info("User details Syncing before login Successfull");
			else if (status.equalsIgnoreCase("failure"))
				log.info("User details Syncing before login Failed");

		} catch (Exception e) {
			log.error("Error occurred while syncing user details:: ", e);
			if (e.getMessage().equalsIgnoreCase("401 Unauthorized")) {
				List<String> clientStauses = new ArrayList<>();
				clientStauses.add("approval required");
				clientStauses.add("approved");
				List<Client> client = clientRepository.findByClientStatusInAndStatus(clientStauses, Status.ACTIVE);
				ServerTokenUtils.globalServerToken = tokenUtils.JwtAuthenticate(client.get(0), res);
			}
			status = dataSyncService.syncUserDetails(ServerTokenUtils.globalServerToken);
			if (status.equalsIgnoreCase("success"))
				log.info("User details Syncing before login Successfull");
			else if (status.equalsIgnoreCase("failure"))
				log.info("User details Syncing before login Failed");
		}
		return "redirect:/login";
	}

	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() throws Exception {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

		clientHttpRequestFactory.setHttpClient(httpClient());

		return clientHttpRequestFactory;
	}

	private HttpClient httpClient() throws Exception {
		// CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
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
		final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
		SSLContext sslContext = new SSLContextBuilder()
				.loadTrustMaterial(null, acceptingTrustStrategy)
				.build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
				NoopHostnameVerifier.INSTANCE);
		// credentialsProvider.setCredentials(AuthScope.ANY,
		// new UsernamePasswordCredentials(sysClient.getClientId(),
		// sysClient.getClientToken() + ";" + machineId.substring(machineId.length() -
		// 38, machineId.length() - 2)));

		HttpClient client = HttpClientBuilder
				.create()
				// .setDefaultCredentialsProvider(credentialsProvider)
				.setSSLSocketFactory(socketFactory)
				.build();
		return client;
	}

	/*
	 * private RestTemplate restTemplate() throws Exception {
	 *
	 * HttpComponentsClientHttpRequestFactory factory =
	 * getClientHttpRequestFactory(); return new RestTemplate(factory); }
	 */ // create header for rest call
	private HttpHeaders createHeaders(String jwtToken) {
		return new HttpHeaders() {
			{

				setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				String authHeader = "Bearer " + jwtToken;
				add("Authorization", authHeader);
			}
		};
	}
}
