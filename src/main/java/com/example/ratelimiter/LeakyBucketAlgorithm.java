package com.example.ratelimiter;

public class LeakyBucketAlgorithm implements RateLimiter {
    @Override
    public boolean allowRequest(){
        return true;
    }
}
