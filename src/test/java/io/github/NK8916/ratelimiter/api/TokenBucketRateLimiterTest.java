package io.github.NK8916.ratelimiter.api;

import org.junit.jupiter.api.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class TokenBucketRateLimiterTest extends BaseRateLimiterTest{

    @Override
    protected RateLimitAlgorithm getAlgorithm() {
        return RateLimitAlgorithm.TOKEN_BUCKET;
    }

    @Test
    void testAllowedRequestWithinLimit(){
        String key=generateRedisKey("tb:1");
        assertTrue(rateLimiter.allow(key,"user:post"));
        assertTrue(rateLimiter.allow(key,"user:post"));
        assertTrue(rateLimiter.allow(key,"user:post"));
    }

    @Test
    void testAllowedRequestExceedingLimit(){
        String redisKey = generateRedisKey("tb:2");
        for(int i=0;i<5;i++){
            rateLimiter.allow(redisKey,"user:post");
        }
        assertFalse(rateLimiter.allow(redisKey,"user:post"));
    }
}
