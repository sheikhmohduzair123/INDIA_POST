package com.services.impl;

import com.domain.User;
import com.repositories.SUserRepository;
import com.services.UserRegistrationService;
import com.util.UpdatePasswordError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationServiceImpl implements UserRegistrationService {

	@Autowired
	private SUserRepository userRepository;

	/*
	 * @Autowired private BCryptPasswordEncoder bc;
	 */

	@Autowired
	private UpdatePasswordError message;

	private static String applicationSecret = "asd67@we";

	/*
	 * @Override public String activateServerUser(String registrationCode) { User
	 * currentUser = userRepository.findUserByActivationCode(registrationCode); if
	 * (currentUser != null) { if (currentUser.isEnabled() != true) {
	 * System.out.println("\n ACTIVATING USER:::"); currentUser.setEnabled(true);
	 * currentUser.setActivationCode(null); userRepository.save(currentUser); } else
	 * { return message.getUserAlreadyActive(); } } else { return
	 * message.getSomethingGotWrong(); } return "Success"; }
	 */
}
