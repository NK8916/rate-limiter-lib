package io.github.NK8916.ratelimiter.core;

import io.github.NK8916.ratelimiter.api.RateLimitRule;

import java.util.Set;

public interface RedisClient {
    long incr(String key);
    void expire(String key,int seconds);
    void delete(String key);
    boolean runTokenBucketLua(String key, RateLimitRule rule);
    boolean runSlidingWindowLua(String key,RateLimitRule rule);
    Set<String> keys(String pattern);
    String ping();
    void close();
}
