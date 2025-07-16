package com.example.ratelimiter;

public class RateLimiterConfig {
    private final RateLimiterAlgorithm algorithm_;
    private final long permitsPerSecond_;
    private final long maxPermits_;
    private final long windowSizeMs_;

    public RateLimiterConfig(RateLimiterAlgorithm rateLimiterAlgorithm,long permitsPerSecond,long maxPermits,long windowSizeMs){
        algorithm_=rateLimiterAlgorithm;
        permitsPerSecond_=permitsPerSecond;
        maxPermits_=maxPermits;
        windowSizeMs_=windowSizeMs;
    }

    public RateLimiterAlgorithm getAlgorithm(){
        return algorithm_;
    }

    public long getPermitsPerSecond(){
        return permitsPerSecond_;
    }

    public long getMaxPermits(){
        return maxPermits_;
    }

    public long getWindowSizeMs(){
        return windowSizeMs_;
    }
}
