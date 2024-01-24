package com.controllers;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.net.ssl.SSLContext;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.domain.Client;
import com.domain.Role;
import com.repositories.RoleRepository;
import com.services.ParcelService;
import com.services.SUserService;
import com.services.UserRegistrationService;
import com.services.impl.UserDetailsServiceImpl;
import com.util.UpdatePassword;
import com.util.UpdatePasswordError;
import com.vo.UserVo;


@Controller
@Profile({"client","server","clientonly"})
@RequestMapping("/user")
public class UserController {

	protected Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserDetailsServiceImpl userUpdateService;

	@Autowired
	private SUserService userService;

	@Autowired
	private UpdatePasswordError messages;

	@Autowired
	private UserRegistrationService registrationService;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private ParcelService parcelService;

	@Autowired
	UpdatePasswordError updatePasswordError;

	@Value("${appUrl}")
	private String appUrl;

	@Value("${server.url}")
	private String serverUrl;

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.GET)
	public String getForgotPassword(Model model, com.domain.User user) {
		model.addAttribute("user", user);
		return "forgotPassword";
	}

	@RequestMapping(value = "/forgotPassword", method = RequestMethod.POST)
	public String forgotPassword(@Valid com.domain.User user, BindingResult bindingResult, Model model) {

		log.info("inside forgot password");
	//	Locale locale = LocaleContextHolder.getLocale();
		// validate if email is not blank
		try {
			// to-do
			// fetch user by emailId
			com.domain.User existingUser = userService.findUserByEmail(user.getEmail());// fetch by email
			if (bindingResult.hasFieldErrors("email")) {
				model.addAttribute("noEmail", messages.getEmptyEmail());
				return "forgotPassword";
			} else {
				if (existingUser != null) {
					// String activationCode = createResetPasswordToken(existingUser);
					// save this activationCode in DB
					userService.sendEmail(existingUser);
					model.addAttribute("success", messages.getForgotPasswordMail());
					return "forgotPassword";
				} else {
					model.addAttribute("error", messages.getWrongemail());
					return "forgotPassword";
				}
			}
		} catch (UsernameNotFoundException e) {
			log.error("error:", e);
			return "forgotPassword";

		}
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.GET)
	public String resetPassword(@RequestParam("activationCode") String activationCode, Model model,
			com.domain.User user) {
		log.debug("returning reset password page");
		try {
			com.domain.User u = userService.findUserByActivationCode(activationCode);
			String code = u.getActivationCode();
			if (code != null) {
				return "reset";

			} else {
				model.addAttribute("message", messages.getLinkbroken());
				return "403Page";
			}
		} catch (Exception e) {
			log.error("error:", e);
			return "403Page";
		}
	}

	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST)
	public String resetPasswordSuccess(@ModelAttribute("activationCode") String activationCode,
			@ModelAttribute("newPassword") String newPassword, Model model) {
		log.debug("inside reset password");
		try
		{
			StringBuilder patternBuilder = new StringBuilder("((?=.*[a-z])(?=.*[0-9])(?=.*[@#$%])(?=.*[A-Z]).{6,15})");
			String pattern = patternBuilder.toString();
			Pattern p = Pattern.compile(pattern);
			Matcher m = p.matcher(newPassword);
	         if(m.matches()) {
			com.domain.User userObj = userService.findUserByActivationCode(activationCode);

			if (userObj != null) {

				String status = userUpdateService.resetPassword(userObj, newPassword);
				if (status.equals(messages.getPasswordresetsuccessful())) {

					if(userObj.getRole().getName().equals("ROLE_FRONT_DESK_USER"))
					{
						String role="client";
						model.addAttribute("role",role);
					}
					else {
						String role="server";
						model.addAttribute("role",role);
					}

					model.addAttribute("successMessage", status);
					return "reset";
				}
				model.addAttribute("message", status);
				return "reset";

			} else {
				model.addAttribute("error", messages.getCannotchangepassword());
				return "reset";
			}
	         }
	         else {
	        	 model.addAttribute("error", messages.getPasswordValidationFailure());
					return "reset";
	         }

		} catch (Exception e) {
			log.error("error:", e);
			return "reset";
		}

	}


	// Update Password
	@RequestMapping(value = "/updatePassword", method = RequestMethod.POST)
	@ResponseBody
	public String updatePassword(@ModelAttribute UpdatePassword updatePassword, Principal principal) {
	       	com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
    		String status = userUpdateService.userPasswordUpdate(loginedUser.getUsername(), updatePassword);
    		return status;
    }

	@RequestMapping(value = "/changePassword", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView changePassword(@ModelAttribute("newPassword") String newPassword, Model model, Principal principal,HttpServletRequest req) throws Exception {
		StringBuilder patternBuilder = new StringBuilder("((?=.*[a-z])(?=.*[0-9])(?=.*[@#$%])(?=.*[A-Z]).{6,15})");
		String pattern = patternBuilder.toString();
		Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(newPassword);

        if(m.matches())
        {
    		log.debug("inside changePassword:::");
    		com.domain.User user = (com.domain.User) ((Authentication) principal).getPrincipal();

    		try
    		{
    			if(user.isFirstLogin()) {
        			String status = userUpdateService.resetPassword(user, newPassword);
        			com.domain.User users = userService.findUserByEmail(user.getEmail());
        			if (status.equals(messages.getPasswordresetsuccessful())) {
        				if(activeProfile.equals("client"))
        				{
        					String cookieToken=getTokenFromCookie(req);
        					if(cookieToken.isBlank())
        						return new ModelAndView("redirect:/login");

        					RestTemplate restTemplate = restTemplate();
        					UserVo userVo = new UserVo();
        					userVo.setEmail(users.getEmail());
        					userVo.setAccountExpired(users.isAccountExpired());
        					userVo.setAccountLocked(users.isAccountLocked());
        					userVo.setCredentialsExpired(users.isCredentialsExpired());
        					userVo.setDob(users.getDob());
        					userVo.setEnabled(users.isEnabled());
        					userVo.setFirstLogin(users.isFirstLogin());
        					userVo.setId(users.getId());
        					userVo.setIdentificationId(users.getIdentificationId());
        					userVo.setLastLogin(users.getLastLogin());
        					userVo.setName(users.getName());
        					userVo.setPassword(users.getPassword());
        					userVo.setPostalCode(users.getPostalCode());
        					userVo.setRmsId(users.getRmsId());
        					userVo.setRole(users.getRole());
							userVo.setStatus(users.getStatus());
							
        					userVo.setUsername(users.getUsername());
        					//user = restTemplate.postForObject(serverUrl + "/server/saveChangePassword", users,com.domain.User.class);
        					ResponseEntity<UserVo> usr=restTemplate.exchange(serverUrl+"/server/saveChangePassword", HttpMethod.POST, new HttpEntity<Object>(userVo,createHeaders(cookieToken)),UserVo.class);
        					UserVo uservo=usr.getBody();
        				}
        				model.addAttribute("successMessage", status);
        				return new ModelAndView("redirect:/login");

        			} else if (status.equals(messages.getCannotbeSameAsold())) {
        				model.addAttribute("Message", messages.getCannotbeSameAsold());
        				return new ModelAndView("changePassword");
        			} else if (status.equals(messages.getCannotusepreviouslyused())) {
        				model.addAttribute("Message2", messages.getCannotusepreviouslyused());
        				return new ModelAndView("changePassword");
        			}else {
        				model.addAttribute("error", messages.getCannotchangepassword());
        				return new ModelAndView("changePassword");
        			}
    			}
    			else {
    				log.info("Error:: Old user trying to change password from first time change password page");

    				model.addAttribute("error", messages.getIsOldUser());
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

	@RequestMapping(value="/getAllRoles", method = RequestMethod.POST)
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
	//	String cookieToken = getTokenFromCookie(req);
	//	String status = registrationService.activateUser(registrationCode,cookieToken);
	//	model.addAttribute("message", status);
	//	return "redirect:/login";
		log.info("Returning to Activate User Page");

		String status = registrationService.activateUser(registrationCode);
		model.addAttribute("message", status);
		return new ModelAndView("activateUser");
	}

	//read token from cookie
	private String getTokenFromCookie(HttpServletRequest req) {

		String token="";
		Cookie cookies[] = req.getCookies();
        for(Cookie c : cookies) {
            if(c.getName().equals("server")) {

                token=c.getValue();
                return token;
            }
        }
        return token;
	}


	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() throws Exception
	{
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory
				= new HttpComponentsClientHttpRequestFactory();

		clientHttpRequestFactory.setHttpClient(httpClient());

		return clientHttpRequestFactory;
	}

	private HttpClient httpClient() throws Exception
	{
	//	CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
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
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
	//	credentialsProvider.setCredentials(AuthScope.ANY,
			//	new UsernamePasswordCredentials(sysClient.getClientId(), sysClient.getClientToken() + ";" + machineId.substring(machineId.length() - 38, machineId.length() - 2)));

		HttpClient client = HttpClientBuilder
				.create()
			//	.setDefaultCredentialsProvider(credentialsProvider)
				.setSSLSocketFactory(socketFactory)
				.build();
		return client;
	}

	private RestTemplate restTemplate() throws Exception {

		HttpComponentsClientHttpRequestFactory factory = getClientHttpRequestFactory();
		return new RestTemplate(factory);
	}
	//create header for rest call
	private HttpHeaders createHeaders(String jwtToken)
	  { return new HttpHeaders() {
		  {

			  setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
			  String authHeader = "Bearer " + jwtToken;
			  add("Authorization", authHeader );
	      }
	    };
	  }
}



