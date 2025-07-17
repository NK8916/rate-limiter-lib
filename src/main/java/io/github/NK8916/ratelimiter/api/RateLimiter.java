package io.github.NK8916.ratelimiter.api;

import io.github.NK8916.ratelimiter.core.RateLimiterFactory;
import io.github.NK8916.ratelimiter.strategy.RateLimiterStrategy;

public class RateLimiter {
    private final RateLimiterFactory factory;
    private final RateLimitRuleProvider ruleProvider;

    public RateLimiter(RateLimiterFactory factory,RateLimitRuleProvider ruleProvider){
        this.factory=factory;
        this.ruleProvider=ruleProvider;
    }

    public boolean allow(String key,String logicalRuleKey){
        RateLimitRule rule=ruleProvider.getRule(logicalRuleKey);
        RateLimiterStrategy strategy=factory.getStrategy(rule.getAlgorithm());
        return strategy.isAllowed(key,rule);
    }
}
