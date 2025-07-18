package io.github.NK8916.ratelimiter.api;

public interface MutableRuleRegistry extends RateLimitRuleProvider{
    void registerRule(String logicalKey,RateLimitRule rule);
}
