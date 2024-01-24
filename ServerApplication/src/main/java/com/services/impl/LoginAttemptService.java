package com.services.impl;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import org.jfree.util.Log;
import org.springframework.stereotype.Service;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

@Service
public class LoginAttemptService {

    private final int MAX_ATTEMPT = 3;
    private LoadingCache<String, Integer> attemptsCache;

    public LoginAttemptService() {
        super();
        attemptsCache = CacheBuilder.newBuilder().
          expireAfterWrite(1, TimeUnit.DAYS).build(new CacheLoader<String, Integer>() {
            public Integer load(String key) {
                return 0;
            }
        });
    }

    public void loginSucceeded(String key) {
    	try {
			if(attemptsCache.get(key) < MAX_ATTEMPT)
				attemptsCache.invalidate(key);
		} catch (ExecutionException e) {
			Log.error("Error in login attempt success service::",e);
		}
    }

    public void loginFailed(String key) {
        int attempts = 0;
        try {
            attempts = attemptsCache.get(key);
        } catch (ExecutionException e) {
            attempts = 0;
			Log.error("Error in login attempt fail service::",e);
        }
        attempts++;
        attemptsCache.put(key, attempts);
    }

    public boolean isBlocked(String key) {
        try {
            return attemptsCache.get(key) >= MAX_ATTEMPT;
        } catch (ExecutionException e) {
			Log.error("Error checking if current user(ip) is blocked::",e);
        	return false;
        }
    }
}