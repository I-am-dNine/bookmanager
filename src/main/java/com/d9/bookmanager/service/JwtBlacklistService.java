package com.d9.bookmanager.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class JwtBlacklistService {

    private final RedisTemplate<String, String> redisTemplate;
    private static final String PREFIX = "blacklisted:";

    public JwtBlacklistService(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void blacklistToken(String token, long ttlSeconds) {
        redisTemplate.opsForValue().set(PREFIX + token, "true", ttlSeconds, TimeUnit.SECONDS);
    }

    public boolean isBlacklisted(String token) {
        return Boolean.TRUE.toString().equals(redisTemplate.opsForValue().get(PREFIX + token));
    }
}