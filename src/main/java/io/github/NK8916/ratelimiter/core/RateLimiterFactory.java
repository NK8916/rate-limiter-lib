package io.github.NK8916.ratelimiter.core;

import io.github.NK8916.ratelimiter.api.RateLimitAlgorithm;
import io.github.NK8916.ratelimiter.strategy.FixedWindowStrategy;
import io.github.NK8916.ratelimiter.strategy.RateLimiterStrategy;
import io.github.NK8916.ratelimiter.strategy.SlidingWindowStrategy;
import io.github.NK8916.ratelimiter.strategy.TokenBucketStrategy;

import java.util.HashMap;
import java.util.Map;

public class RateLimiterFactory {
    private final Map<RateLimitAlgorithm, RateLimiterStrategy> strategies=new HashMap<>();

    public RateLimiterFactory(RedisClient redis){
        strategies.put(RateLimitAlgorithm.TOKEN_BUCKET,new TokenBucketStrategy(redis));
        strategies.put(RateLimitAlgorithm.FIXED_WINDOW,new FixedWindowStrategy(redis));
        strategies.put(RateLimitAlgorithm.SLIDING_WINDOW_LOG,new SlidingWindowStrategy(redis));
    }

    public RateLimiterStrategy getStrategy(RateLimitAlgorithm algorithm){
        return strategies.get(algorithm);
    }
}
