package io.github.NK8916.ratelimiter.api;

import io.github.NK8916.ratelimiter.core.JedisRedisClient;
import io.github.NK8916.ratelimiter.core.RedisClient;
import io.github.NK8916.ratelimiter.support.InMemoryRuleProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

import java.io.IOException;
import java.util.Set;

public abstract class BaseRateLimiterTest {
    protected RedisClient redis;
    protected RateLimiter rateLimiter;
    protected MutableRuleRegistry ruleRegistry;

    protected abstract RateLimitAlgorithm getAlgorithm();

    @BeforeEach
    void setUp() throws IOException {
        redis=new JedisRedisClient("localhost",6379);
        ruleRegistry=new InMemoryRuleProvider();
        String logicalKey="user:post";
        RateLimitRule rule=RateLimitRule.builder().algorithm(getAlgorithm())
                .limit(3)
                .refillRate(3.0)
                .bucketSize(5)
                .windowSeconds(5)
                .build();
        ruleRegistry.registerRule(logicalKey, rule);

        rateLimiter=new RateLimiterBuilder().redisClient(redis).ruleProvider(ruleRegistry).build();
    }

    @AfterEach
    void tearDown(){
        Set<String> keys = redis.keys("rate:limit:*");
        for (String key : keys) {
            redis.delete(key);
        }
        redis.close();
    }

    protected String generateRedisKey(String suffix) {
        return "rate:limit:test:" + suffix;
    }

}