package io.github.NK8916.ratelimiter.api;

public interface RateLimitRuleProvider {
    RateLimitRule getRule(String logicalKey);
}
