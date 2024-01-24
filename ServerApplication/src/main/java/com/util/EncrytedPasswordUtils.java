package com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class EncrytedPasswordUtils {
	static final Logger log = LoggerFactory.getLogger(EncrytedPasswordUtils.class.getPackage().getName());

	/**
	 * 
	 * @param password
	 * @return Encrypted Password
	 */
	public static String encrytePassword(String password) {
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder.encode(password);
	}

	public static void main(String[] args) {
		String password = "123";
		String encrytedPassword = encrytePassword(password);

	}
}
