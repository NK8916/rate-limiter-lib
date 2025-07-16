package com.example.ratelimiter;

public class RateLimiterFactory {
    public static RateLimiter create(RateLimiterConfig config){
        switch (config.getAlgorithm()){
            case TOKEN_BUCKET:
                return new TokenBucketAlgorithm();
            case LEAKY_BUCKET:
                return new LeakyBucketAlgorithm();
            default:
                throw new IllegalArgumentException("Unsupported algorithm: " + config.getAlgorithm());
        }
    }
}
