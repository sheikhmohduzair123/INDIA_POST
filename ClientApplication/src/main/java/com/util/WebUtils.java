package com.util;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;

import com.domain.User;


public class WebUtils {

	public static final Logger logger = LoggerFactory.getLogger(WebUtils.class);

	/**
	 * 
	 * @param user
	 * @return All the authorities associated with user
	 */
	public static String toString(User user) {

		StringBuilder sb = new StringBuilder();
		logger.info("Appending the user with username: " + user.getUsername());
		sb.append("UserName:").append(user.getUsername());
		/**
		 * Returning all the authorities associated with user
		 */

		Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
		if (authorities != null && !authorities.isEmpty()) {
			sb.append(" (");
			boolean first = true;
			for (GrantedAuthority a : authorities) {
				if (first) {
					sb.append(a.getAuthority());
					logger.info("Authority: " + authorities);
					first = false;
				} else {
					sb.append(", ").append(a.getAuthority());
				}
			}
			sb.append(")");
		}
		logger.info("Authorities: " + sb.toString());
		return sb.toString();
	}
}
