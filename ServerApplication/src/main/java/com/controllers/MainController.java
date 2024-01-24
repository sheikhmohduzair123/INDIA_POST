package com.controllers;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import javax.net.ssl.SSLContext;
import javax.servlet.http.Cookie;
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
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

import com.constants.Status;
import com.domain.Client;
import com.domain.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.repositories.ClientRepository;
import com.services.ClientService;
import com.services.impl.LoginAttemptService;
import com.services.impl.UserDetailsServiceImpl;
import com.util.WebUtils;
import com.vo.JwtTokenResponse;

@Controller
@Profile({ "client", "server", "clientonly" })
@RequestMapping("/")
public class MainController {

	/**
	 * Additional logger to use when no handler mapping is found for a request.
	 */
	protected Logger log = LoggerFactory.getLogger(MainController.class);

	@Autowired
	private UserDetailsServiceImpl userUpdateService;

	@Autowired
	private ClientService clientService;

	@Autowired
	private Environment environment;

	@Autowired
	private ClientRepository clientRepository;

	@Autowired
    private LoginAttemptService loginAttemptService;

	@Value("${app.baseurl}")
	private String baseurl;

	@Value("${cookie.maxage}")
	private String cookieMaxAge;

	@Value("${spring.profiles.active}")
	private String activeProfile;

	@Value("${rateAPI}")
	private String rateURL;

	@Value("${server.url}")
	private String serverURL;

	@RequestMapping(value = { "/", "/welcome" }, method = RequestMethod.GET)
	/**
	 *
	 * @param model
	 * @return the WelcomePage
	 */
	public String welcomePage(Model model) {

		log.info("Returning to the Welcome Page");
		return "redirect:/login";
	}

	/** Returning to home page */
	/**
	 *
	 * @param model
	 * @param principal
	 * @return the page AdminPage
	 */

	@RequestMapping("/health")
	@ResponseBody
	public String checkHealth() {
		return "Server is running";
	}

	@RequestMapping(value = "/admin", method = RequestMethod.GET)
	public String adminPage(Model model, Principal principal) {

		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);

		log.info("Returning to Admin page with Username: " + loginedUser.getUsername());
		return "adminPage";
	}

	/**
	 * Returning to Login Page
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loginPage(Model model, HttpServletRequest req, HttpServletResponse res) throws Exception {
		log.info("checking if user is already login");
		String[] activeProfiles = environment.getActiveProfiles();
		if ((!userUpdateService.getUserDetailsLocal() || !clientService.getClientDetailsLocal())
				&& (activeProfiles[0].equals("client") || (activeProfiles[0].equals("clientonly")))) {
			log.info("inside client commissioning");
			if (activeProfiles[0].equals("clientonly"))
				return "redirect:/client/commissionClientOnly";
			else
				return "redirect:/client/commissionClient";
		}

		String ip = getClientIP(req);

        if (loginAttemptService.isBlocked(ip)) {
        	log.info("USER BLOCKED: maximum login attempts reached");
        	model.addAttribute("blocked", "blocked");
        	return "loginPage";
        }
        else if (SecurityContextHolder.getContext().getAuthentication() != null
				&& SecurityContextHolder.getContext().getAuthentication().isAuthenticated() &&
				// when Anonymous Authentication is enabled
				!(SecurityContextHolder.getContext().getAuthentication() instanceof AnonymousAuthenticationToken)) {
			log.info("user is already logged in, skipping login page");
			User loginedUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// if(loginedUser.isFirstLogin() && !(activeProfile.equals("server")))
			if (loginedUser.isFirstLogin()) {
				log.info("user logging first time, forcing to change password && generating cookie");
				if (activeProfile.equals("client")) {
					// generate jwt token for rate
					List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved",
							Enum.valueOf(Status.class, "ACTIVE"));
					Client client = clientList.get(0);
					JwtAuthenticate(client, res);

					int server_flag = 0;

					// getting token from cookie
					Cookie cookie[] = req.getCookies();
					for (Cookie c : cookie) {
						if (c.getName().equals("server")) {
							log.debug("server token found::" + c.getValue());
							server_flag = 1;
						}
					}

					if (server_flag == 0) {
						log.debug("server token regenerating");
						// generate jwt token for server
						JwtServerAuthenticate(client, res);
					}
				}
				return "redirect:/changePasswordHome";
			}
			if (!(activeProfile.equals("server"))) {
				int rate_flag = 0;

				// getting token from cookie
				Cookie cookie[] = req.getCookies();
				for (Cookie c : cookie) {
					if (c.getName().equals("rate")) {
						rate_flag = 1;
					}
				}
				List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved",
						Enum.valueOf(Status.class, "ACTIVE"));
				Client client = clientList.get(0);

				if (rate_flag == 0) {
					log.debug("rate api token regenerating");
					// generate jwt token for rate
					JwtAuthenticate(client, res);
				}
			}

			if (activeProfile.equals("client")) {
				int server_flag = 0;

				// getting token from cookie
				Cookie cookie[] = req.getCookies();
				for (Cookie c : cookie) {
					if (c.getName().equals("server")) {
						server_flag = 1;
					}
				}
				List<Client> clientList = clientRepository.findByClientStatusAndStatus("approved",
						Enum.valueOf(Status.class, "ACTIVE"));
				Client client = clientList.get(0);

				if (server_flag == 0) {
					log.debug("server token regenerating");
					// generate jwt token for server
					JwtServerAuthenticate(client, res);
				}
			}
			if ((activeProfile.equals("server")) && ((loginedUser.getRole().getName().equals("ROLE_RMS_USER"))
					|| (loginedUser.getRole().getName().equals("ROLE_PO_USER")))) {
				log.info("\nLogin as UserName:: " + loginedUser.getName() + " with Role:: "
						+ loginedUser.getRole().getName());
				return "redirect:/tracking/bagInward";
			}

			if (activeProfiles[0].equals("client") || activeProfiles[0].equals("clientonly"))
				return "redirect:/client/senderHome";
			else
				return "redirect:/server/registerClient";
		}
		log.debug("Returning to the loginpage");
		return "loginPage";

	}

	private String getClientIP(HttpServletRequest request) {
	    String xfHeader = request.getHeader("X-Forwarded-For");
	    if (xfHeader == null){
	        return request.getRemoteAddr();
	    }
	    return xfHeader.split(",")[0];
	}

	/** Checking the user and authentication type */
	@RequestMapping(value = "/userInfo", method = RequestMethod.GET)
	public String userInfo(Model model, Principal principal) {

		String userName = principal.getName();
		User loginedUser = (User) ((Authentication) principal).getPrincipal();
		String userInfo = WebUtils.toString(loginedUser);
		model.addAttribute("userInfo", userInfo);
		if (log.isDebugEnabled()) {
			log.debug("User Name: " + userName + " Authorities: " + loginedUser.getAuthorities() + " userInfo: "
					+ userInfo);
		}
		return "userInfoPage";
	}

	/** Checking User and Authentication type */
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessDenied(Model model, Principal principal) {

		if (principal != null) {

			log.error("Not allowed to access this page with: " + principal.getName());

			User loginedUser = (User) ((Authentication) principal).getPrincipal();
			String userInfo = WebUtils.toString(loginedUser);
			model.addAttribute("userInfo", userInfo);// binding the UserInfo with the model Object
			String message = "Hi " + principal.getName() //
					+ "<br> You do not have permission to access this page!";
			model.addAttribute("message", message);
		}
		log.info("Returning to page 403Page");
		return "403Page";
	}

	@RequestMapping(value = "/changePasswordHome", method = RequestMethod.GET)
	public String changePasswordHome(Principal principal) {
		log.info("inside changePasswordHome");
		com.domain.User loginedUser = (com.domain.User) ((Authentication) principal).getPrincipal();
		// if user has already set the password, redirect to senderHome page(default
		// client parcel booking page)

		if (!loginedUser.isFirstLogin()) {
			if ((activeProfile.equals("server")) && ((loginedUser.getRole().getName().equals("ROLE_RMS_USER"))
					|| (loginedUser.getRole().getName().equals("ROLE_PO_USER")))) {
				return "redirect:/tracking/bagInward";
			} else
				return "redirect:/server/registerClient";

		}

		return "changePassword";
	}

	private HttpComponentsClientHttpRequestFactory getClientHttpRequestFactory() throws Exception {
		HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();

		clientHttpRequestFactory.setHttpClient(httpClient());

		return clientHttpRequestFactory;
	}

	private HttpClient httpClient() throws Exception {
		final TrustStrategy acceptingTrustStrategy = (certificate, authType) -> true;
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, acceptingTrustStrategy).build();
		SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext,
				NoopHostnameVerifier.INSTANCE);
		HttpClient client = HttpClientBuilder.create().setSSLSocketFactory(socketFactory).build();
		return client;
	}

	private RestTemplate restTemplate() throws Exception {

		HttpComponentsClientHttpRequestFactory factory = getClientHttpRequestFactory();
		return new RestTemplate(factory);
	}

	// create header for rest template
	private HttpHeaders createHeaders(String jwtToken) {
		return new HttpHeaders() {
			/**
			*
			*/
			private static final long serialVersionUID = 1L;
			{
				setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
				String authHeader = "Bearer " + jwtToken;
				add("Authorization", authHeader);
			}
		};
	}

	// getting jwt token from rateapi
	private void JwtAuthenticate(Client client, HttpServletResponse res) throws Exception {

		RestTemplate restTemplate = restTemplate();
		String jwtToken = restTemplate.postForObject(rateURL + "/parcel/authenticate", client, String.class);
		ObjectMapper objectMapper = new ObjectMapper();
		JwtTokenResponse jwtTokenResponse = objectMapper.readValue(jwtToken, JwtTokenResponse.class);
		jwtToken = jwtTokenResponse.getToken();

		//
		Cookie cookie = writeCookie(jwtToken);
		res.addCookie(cookie);

//			return jwtToken;
	}

	// write cookie(token) to browser
	private Cookie writeCookie(String token) {

		Cookie cookie = new Cookie("rate", token);

		// cookie.setMaxAge(365 * 24 * 60 * 60); // expires in for one
		cookie.setMaxAge(Integer.parseInt(cookieMaxAge)); // for one hrs
		// cookie.setSecure(true);
		// cookie.setDomain(baseurl);
		// cookie.setPath("/");
		cookie.setHttpOnly(true);
		return cookie;

	}

	// rest call for getting token from serverApi
	private void JwtServerAuthenticate(Client client, HttpServletResponse res) throws Exception {
		log.info(" getting token on rest call " + client);
		RestTemplate restTemplate = restTemplate();
		try {
			String jwtToken = restTemplate.postForObject(serverURL + "/server/authenticate", client, String.class);
			ObjectMapper objectMapper = new ObjectMapper();
			JwtTokenResponse jwtTokenResponse = objectMapper.readValue(jwtToken, JwtTokenResponse.class);
			jwtToken = jwtTokenResponse.getToken();
			Cookie cookie = writeServerCookie(jwtToken);
			res.addCookie(cookie);
		} catch (Exception e) {
			log.error("Error creating jwt token for server api:: ",e);
		}
	}

	// write cookie(token) to browser
	private Cookie writeServerCookie(String token) {

		log.info("writing cookieToken to browser ");
		Cookie cookie = new Cookie("server", token);
		// cookie.setMaxAge(365 * 24 * 60 * 60); // expires in for one year
		cookie.setMaxAge(Integer.parseInt(cookieMaxAge)); // for one day
		// cookie.setSecure(true);
		cookie.setHttpOnly(true);
		// cookie.setDomain(baseurl);
		// cookie.setPath("/");
		return cookie;
	}

}
