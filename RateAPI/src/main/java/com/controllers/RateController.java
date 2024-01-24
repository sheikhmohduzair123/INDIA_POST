package com.controllers;

import com.configuration.JwtTokenUtil;
import com.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import com.services.RateService;
import com.services.impl.JwtUserDetailsService;

/*
 * This class will have all methods which will be related to Parcel creation
 * Tracking related methods will not come here
 */


@RestController
@RequestMapping("/parcel")
public class RateController {

	protected Logger log = LoggerFactory.getLogger(RateController.class);

    @Autowired
    private RateService rateService;

    @Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private JwtUserDetailsService userDetailsService;


	//jwt authenticate api
    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
	public ResponseEntity<?> createAuthenticationToken(@RequestBody Client client) throws Exception {

    	log.info("Authenticate Client Id :" + client.getClientId()+"----"+client.getClientToken());
		authenticate(client.getClientId(), client.getClientToken());
		final UserDetails userDetails = userDetailsService
				.loadUserByUsername(client.getClientId());
		final String token = jwtTokenUtil.generateToken(userDetails);

		log.info("Sending Token Back to RestTemplate  :");
		return ResponseEntity.ok(new JwtResponse(token));
	}

    /** Returning to Logout page after logout */
    @RequestMapping(value = "/getRate", method = RequestMethod.POST)
    public Parcel calculateRateForParcel(@RequestBody Parcel parcel, Model model) {
    	log.info("inside calculateRateForParcel");
    	log.debug("parcel:"+parcel);
    	Parcel p = rateService.getRate(parcel);

        log.debug("parcel return:"+p);
        return p;
    }


	//method jwt auth
    private void authenticate(String username, String password) throws Exception {

  		try {
  			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
  		} catch (DisabledException e) {
  			throw new Exception("USER_DISABLED", e);
  		} catch (BadCredentialsException e) {
  			throw new Exception("INVALID_CREDENTIALS", e);
  		}
  	}

}

