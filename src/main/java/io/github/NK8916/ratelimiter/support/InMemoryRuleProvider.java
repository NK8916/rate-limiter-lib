package io.github.NK8916.ratelimiter.support;

import io.github.NK8916.ratelimiter.api.MutableRuleRegistry;
import io.github.NK8916.ratelimiter.api.RateLimitRule;

import java.util.HashMap;
import java.util.Map;

public class InMemoryRuleProvider implements MutableRuleRegistry {
    private final Map<String, RateLimitRule> rules = new HashMap<>();
    @Override
    public void registerRule(String logicalKey, RateLimitRule rule){
        rules.put(logicalKey,rule);
    }

    @Override
    public RateLimitRule getRule(String logicalKey){
        return rules.getOrDefault(logicalKey,RateLimitRule.unlimited());
    }
}
