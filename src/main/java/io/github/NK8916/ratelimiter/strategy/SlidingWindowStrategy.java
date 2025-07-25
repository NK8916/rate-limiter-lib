package io.github.NK8916.ratelimiter.strategy;

import io.github.NK8916.ratelimiter.api.RateLimitRule;
import io.github.NK8916.ratelimiter.core.RedisClient;

public class SlidingWindowStrategy implements RateLimiterStrategy{
    private final RedisClient redis;
    public SlidingWindowStrategy(RedisClient redis){
        this.redis=redis;
    }

    @Override
    public boolean isAllowed(String key, RateLimitRule rule) {
        return this.redis.runSlidingWindowLua(key,rule);
    }
}
