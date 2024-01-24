package com.services.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import com.constants.Status;
import com.domain.PasswordHistory;
import com.domain.User;
import com.repositories.SUserPasswordRepository;
import com.repositories.SUserRepository;
import com.services.SUserService;
import com.util.UpdatePassword;
import com.util.UpdatePasswordError;

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

	public String userPasswordUpdate(String loginedUser, UpdatePassword updatePassword) {
		log.info("inside userPasswordUpdate");
		String newPassword = updatePassword.getNewPassword();
		StringBuilder patternBuilder = new StringBuilder("((?=.*[a-z])(?=.*[0-9])(?=.*[@#$%])(?=.*[A-Z]).{6,15})");
		String pattern = patternBuilder.toString();
		Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(newPassword);
		User currentUser = sUserRepository.findUserByUsernameAndStatus(loginedUser, Status.ACTIVE);
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder(4);
        //check if password matches validation pattern
		if(m.matches())
        {
    	// Check given old password = old password from user table
		if (bc.matches(updatePassword.getOldPassword(),	currentUser.getPassword())) {
			if (!updatePassword.getOldPassword().equals(updatePassword.getNewPassword())) {
					if (!isPreviouslyUsed(currentUser, newPassword)) {

						// Save password in password_history table
						createUserPasswordHistory(
								bc.encode(updatePassword.getNewPassword()),
								currentUser);

						// Update password in user Table
						currentUser.setPassword(bc.encode(newPassword));
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

	public String resetPassword(User currentUser, String newPassword) {

		log.info("inside resetPassword");
		BCryptPasswordEncoder bc = new BCryptPasswordEncoder(4);
//				if (!bc.matches(newPassword, currentUser.getPassword())) {

					if (!isPreviouslyUsed(currentUser, newPassword)) {
						log.debug("changing password now");
						createUserPasswordHistory(bc.encode(newPassword), currentUser);

						// Update password in user Table
						currentUser.setPassword(bc.encode(newPassword));
						currentUser.setActivationCode(null);
						currentUser.setFirstLogin(false);
						sUserRepository.save(currentUser);
						log.info("Password Reset Successfully");
						return updatePasswordError.getPasswordresetsuccessful();

					} else {
						return updatePasswordError.getCannotusepreviouslyused();
					}
				/*} else {
					return updatePasswordError.getCannotbeSameAsold();
				}*/
		}
}
