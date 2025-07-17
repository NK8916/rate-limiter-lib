package io.github.NK8916.ratelimiter.api;

public class RateLimitRule{
    private RateLimitAlgorithm algorithm;
    private int limit;
    private int windowCount;
    private double refillRate;
    private int bucketSize;

    public RateLimitAlgorithm getAlgorithm() {
        return algorithm;
    }

    public int getLimit() {
        return limit;
    }

    public int getWindowCount() {
        return windowCount;
    }

    public double getRefillRate() {
        return refillRate;
    }

    public int getBucketSize() {
        return bucketSize;
    }
}
