package com.services.impl;

import com.domain.Client;
import com.repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.Collections;


@Service
public class ServerServiceImpl implements UserDetailsService {
    /**
     * Additional logger to use when no user details handler is found for a
     * request.
     */
    protected Logger log = LoggerFactory.getLogger(ServerServiceImpl.class);

    @Autowired
    ClientRepository clientRepository;

    @Override
    public User loadUserByUsername(String clientId) {
        log.info(clientId);
        Client client = clientRepository.findClientByClientId(clientId);
        return new User(client.getClientId(),client.getPassword(), Collections.emptyList());
    }

}
