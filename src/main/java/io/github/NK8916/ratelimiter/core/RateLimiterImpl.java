package io.github.NK8916.ratelimiter.core;

import io.github.NK8916.ratelimiter.api.RateLimitRule;
import io.github.NK8916.ratelimiter.api.RateLimitRuleProvider;
import io.github.NK8916.ratelimiter.api.RateLimiter;
import io.github.NK8916.ratelimiter.strategy.RateLimiterStrategy;

public class RateLimiterImpl implements RateLimiter {
    private final RateLimiterFactory factory;
    private final RateLimitRuleProvider ruleProvider;

    public RateLimiterImpl(RateLimiterFactory factory,RateLimitRuleProvider ruleProvider){
        this.factory=factory;
        this.ruleProvider=ruleProvider;
    }

    @Override
    public boolean allow(String key,String logicalRuleKey){
        RateLimitRule rule=ruleProvider.getRule(logicalRuleKey);
        RateLimiterStrategy strategy=factory.getStrategy(rule.getAlgorithm());
        return strategy.isAllowed(key,rule);
    }
}
