package io.github.NK8916.ratelimiter.api;

public interface RateLimiter {
    boolean allow(String key,String ruleKey);
}
