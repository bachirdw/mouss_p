package com.gestion.location.security;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;

@Component
public class LoginAttemptService {
    private final Map<String, Integer> attempts = new ConcurrentHashMap<>();

    public void loginSucceeded(String key) {
        attempts.remove(key);
    }

    public void loginFailed(String key) {
        attempts.put(key, attempts.getOrDefault(key, 0) + 1);
    }

    public boolean isBlocked(String key) {
        return attempts.getOrDefault(key, 0) >= 5;
    }
}
