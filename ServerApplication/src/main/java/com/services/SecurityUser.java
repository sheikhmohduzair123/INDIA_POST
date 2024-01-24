package com.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.domain.Role;
import com.domain.User;

public class SecurityUser extends User implements UserDetails {

	private static final long serialVersionUID = 1L;

	private static final Logger log = LoggerFactory.getLogger(SecurityUser.class);

	/**
	 * Sending the object to check whether the object is valid
	 * 
	 * @param suser
	 */

	public SecurityUser(User suser) {
		if (suser != null) {
			this.setId(suser.getId());
			this.setName(suser.getName());
			this.setEmail(suser.getEmail());
			this.setPassword(suser.getPassword());
			this.setDob(suser.getDob());
			this.setRole(suser.getRole());
		}
	}

	/**
	 * Checking the Authorities
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		
		Role userRoles = this.getRole();
		log.info("Getting Roles: " + userRoles);
		if (userRoles != null) {
			SimpleGrantedAuthority authority = new SimpleGrantedAuthority(userRoles.getName());
			authorities.add(authority);
		}
		log.info("Checking Authorities: " + authorities);
		return authorities;
	}

	@Override
	public String getPassword() {
		return super.getPassword();
	}

	@Override
	public String getUsername() {
		return super.getEmail();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

}
