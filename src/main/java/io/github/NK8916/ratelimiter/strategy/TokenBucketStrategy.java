package io.github.NK8916.ratelimiter.strategy;

import io.github.NK8916.ratelimiter.api.RateLimitRule;
import io.github.NK8916.ratelimiter.core.RedisClient;

public class TokenBucketStrategy implements RateLimiterStrategy {
    private final RedisClient redis;
    public TokenBucketStrategy(RedisClient redis){
        this.redis=redis;
    }

    public boolean isAllowed(String key, RateLimitRule rule) {
        return this.redis.runTokenBucketLua(key,rule);
    }
}
