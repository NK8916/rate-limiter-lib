package com.example.ratelimiter;

public class TokenBucketAlgorithm implements RateLimiter {
    @Override
    public boolean allowRequest(){
        return true;
    }
}
