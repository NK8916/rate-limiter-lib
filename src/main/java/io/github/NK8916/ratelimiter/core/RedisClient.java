package io.github.NK8916.ratelimiter.core;

import io.github.NK8916.ratelimiter.api.RateLimitRule;

public interface RedisClient {
    long incr(String key);
    void expire(String key,int seconds);
    boolean runTokenBucketLua(String key, RateLimitRule rule);
    boolean runSlidingWindowLua(String key,RateLimitRule rule);
    void close();
}
