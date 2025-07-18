package io.github.NK8916.ratelimiter.api;

import io.github.NK8916.ratelimiter.core.RateLimiterFactory;
import io.github.NK8916.ratelimiter.core.RateLimiterImpl;
import io.github.NK8916.ratelimiter.core.RedisClient;

public class RateLimiterBuilder {
    private RedisClient redisClient;
    private RateLimitRuleProvider ruleProvider;

    public RateLimiterBuilder redisClient(RedisClient redisClient){
        this.redisClient=redisClient;
        return this;
    }

    public RateLimiterBuilder ruleProvider(RateLimitRuleProvider ruleProvider){
        this.ruleProvider=ruleProvider;
        return this;
    }

    public RateLimiter build(){
        RateLimiterFactory factory=new RateLimiterFactory(redisClient);
        return new RateLimiterImpl(factory,ruleProvider);
    }
}
