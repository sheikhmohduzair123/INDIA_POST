package com.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.constants.Status;
import com.domain.PasswordHistory;
import com.domain.User;
import com.repositories.SUserPasswordRepository;
import com.repositories.SUserRepository;
import com.util.MailEngine;
import com.util.UpdatePasswordError;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service("suserService")
public class SUserService {
	protected Logger log = LoggerFactory.getLogger(SUserService.class);

    @Autowired
    private SUserRepository suserRepository;//code10

    @Autowired
    private SUserPasswordRepository sUserPasswordRepository;

    @Autowired
	private BCryptPasswordEncoder passwordEncoder;

     @Autowired
      SUserService userService;

	 @Autowired
	    protected MailEngine mailEngine;

	 @Autowired
		UpdatePasswordError updatePasswordError;

	 @Value("${appUrl}")
		private String appUrl;

		private static String applicationSecret="asd67@we";




    public List<User> findAll() {
        return suserRepository.findAll();
    }

    public User create(User user) {
        return suserRepository.save(user);
    }

    public User findUserById(int id) {
        return suserRepository.findById(id).get();
    }

    public User login(String email, String password) {
        return suserRepository.findByEmailAndPasswordAndStatus(email, password, Status.ACTIVE);
    }

    public User update(User user) {
        return suserRepository.save(user);
    }

    public void deleteUser(int id) {
        suserRepository.deleteById(id);
    }

    public User findUserByEmail(String email) {
        return suserRepository.findUserByEmailContainingIgnoreCaseAndStatus(email, Status.ACTIVE);
    }


	public User UpdateUserPassword(String username) {
		return suserRepository.findUserByUsernameAndStatus(username, Status.ACTIVE);
	}
	
	@Value("${server.port}")
	private String serverPort;


	/*
	 * public void sendEmail(com.domain.User user) { Locale locale =
	 * LocaleContextHolder.getLocale(); com.domain.User existingUser =
	 * userService.findUserByEmail(user.getEmail());//fetch by email
	 * 
	 * 
	 * String activationCode = createResetPasswordToken(existingUser);
	 * 
	 * String link = appUrl+ "/user/resetPassword?activationCode="+activationCode;
	 * mailEngine.sendEmail("jiten.vats@gmail.com", "Reset Password",link, null,
	 * "_forgot-password.html", locale);
	 * 
	 * existingUser.setActivationCode(activationCode);
	 * suserRepository.save(existingUser);
	 * 
	 * }
	 */
	
	
	// Send Mail to user with user Email, with reset password activation link
 	public void sendEmail(com.domain.User user) {
 		com.domain.User existingUser = userService.findUserByEmail(user.getEmail());//fetch by email
 		String activationCode = createResetPasswordToken(existingUser);
 		String link = appUrl + serverPort+"/user/resetPassword?activationCode="+activationCode;
 		Locale locale = LocaleContextHolder.getLocale();
    	mailEngine.sendEmail(user.getEmail(), "Reset Password", link, null, "_forgotPassword.html", locale);
 		 existingUser.setActivationCode(activationCode);
	     suserRepository.save(existingUser);

 	}

	
	public String createResetPasswordToken(com.domain.User user) {
	    String activationToken = passwordEncoder.encode(user.getEmail()+applicationSecret);
	    return activationToken;
	}

	public User findUserByActivationCode(String activationCode)
    {
    	return suserRepository.findUserByActivationCodeAndStatus(activationCode, Status.ACTIVE);

    }

}