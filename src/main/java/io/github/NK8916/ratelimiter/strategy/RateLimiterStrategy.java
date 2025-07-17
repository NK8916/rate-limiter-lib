package io.github.NK8916.ratelimiter.strategy;

import io.github.NK8916.ratelimiter.api.RateLimitRule;

public interface RateLimiterStrategy {
    boolean isAllowed(String key, RateLimitRule rule);
}
