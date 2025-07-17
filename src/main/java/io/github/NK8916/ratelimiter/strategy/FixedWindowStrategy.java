package io.github.NK8916.ratelimiter.strategy;

import io.github.NK8916.ratelimiter.api.RateLimitRule;
import io.github.NK8916.ratelimiter.core.RedisClient;

public class FixedWindowStrategy implements RateLimiterStrategy{
    public FixedWindowStrategy(RedisClient redis){}

    @Override
    public boolean isAllowed(String key, RateLimitRule rule) {
        return false;
    }
}
