package com.services.impl;

import java.util.ArrayList;

import com.constants.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.domain.Client;
import com.repositories.ClientRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {
	
	@Autowired
	private ClientRepository userDao;

	@Autowired
	private PasswordEncoder bcryptEncoder;

	@Override
	public UserDetails loadUserByUsername(String clientId) throws UsernameNotFoundException {
		Client user = userDao.findClientByClientIdAndStatus(clientId, Status.ACTIVE);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with username: " + clientId);
		}
		return new org.springframework.security.core.userdetails.User(user.getClientId(), user.getClientToken(),
				new ArrayList<>());
	}
}