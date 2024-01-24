package com.services.impl;

import java.util.Base64;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.domain.Client;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.repositories.ClientRepository;
import com.services.MeterBalanceSyncService;
import com.util.AuthTokenUtils;

@Service
public class LoginAttemptService {

    @Autowired
    MeterBalanceSyncService meterBalanceSyncService;

    @Autowired
    ClientRepository clientRepository;

    private final int MAX_ATTEMPT = 3;
    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS)
                .build(new CacheLoader<String, Integer>() {
                    public Integer load(String key) {
                        return 0;
                    }
                });
    }

    public void loginSucceeded(String key) {

        try {
            if (attemptsCache.get(key) < MAX_ATTEMPT) {
                attemptsCache.invalidate(key);
            }

        } catch (ExecutionException e) {
            Log.error("Error occurred in login attempt success:: ", e);
        }

    }

    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
            Log.error("Error occurred in login attempt failure:: ", e);
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
            Log.error("Error occurred while checking if user(ip) is blocked:: ", e);
            return false;
        }
    }

    public void syncBalance() {
        if (AuthTokenUtils.oauthToken == null)
            AuthTokenUtils.getAccessToken();

        Log.info("Syncing balance ");
        List<Client> listOfClient = clientRepository.findAll();
        if (listOfClient.size() >= 1) {
            Client client = listOfClient.get(0);
            String encodedNgp = client.getNgpId();
            byte[] base64DecodedBytes = Base64.getDecoder().decode(encodedNgp);
            String decodedNgp = new String(base64DecodedBytes);
            
            meterBalanceSyncService.syncBalance(decodedNgp);
            Log.info("Syncing balance successful");
            
        }

    }
}