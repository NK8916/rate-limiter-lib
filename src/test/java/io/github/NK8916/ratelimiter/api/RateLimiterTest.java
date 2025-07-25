package io.github.NK8916.ratelimiter.api;

import io.github.NK8916.ratelimiter.core.JedisRedisClient;
import io.github.NK8916.ratelimiter.core.RedisClient;
import io.github.NK8916.ratelimiter.support.InMemoryRuleProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Set;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class RateLimiterTest {
    private RedisClient redis;
    private RateLimiter rateLimiter;

    @BeforeEach
    void setUp() throws IOException {
        redis=new JedisRedisClient("localhost",6379);
        MutableRuleRegistry ruleRegistry=new InMemoryRuleProvider();
        ruleRegistry.registerRule("user:post", RateLimitRule.builder().algorithm(RateLimitAlgorithm.SLIDING_WINDOW_LOG).limit(3).windowSeconds(5).build());

        rateLimiter=new RateLimiterBuilder().redisClient(redis).ruleProvider(ruleRegistry).build();
        redis.delete("rate:limit:user:123:post");
    }

    @AfterEach
    void tearDown(){
        Set<String> keys = redis.keys("rate:limit:*");
        for (String key : keys) {
            redis.delete(key);
        }
        redis.close();
    }

    @Test
    void testAllowedRequestWithinLimit(){
        assertTrue(rateLimiter.allow("user:123:post","user:post"));
        assertTrue(rateLimiter.allow("user:123:post","user:post"));
        assertTrue(rateLimiter.allow("user:123:post","user:post"));
    }

    @Test
    void testAllowedRequestExceedingLimit(){
        String redisKey = "user:limit:test:exceeding";
        rateLimiter.allow(redisKey,"user:post");
        rateLimiter.allow(redisKey,"user:post");
        rateLimiter.allow(redisKey,"user:post");
        assertFalse(rateLimiter.allow(redisKey,"user:post"));
    }
}