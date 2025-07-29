package io.github.NK8916.ratelimiter.api;

import java.util.Objects;

public class RateLimitRule{
    private final RateLimitAlgorithm algorithm;
    private final Integer limit;
    private final Integer windowSeconds;
    private final Double refillRate;
    private final Integer bucketSize;

    private RateLimitRule(Builder builder){
        this.algorithm=builder.algorithm;
        this.limit=builder.limit;
        this.windowSeconds=builder.windowSeconds;
        this.refillRate=builder.refillRate;
        this.bucketSize=builder.bucketSize;
    }

    public static Builder builder(){
        return new Builder();
    }

    public static RateLimitRule unlimited() {
        return new RateLimitRule.Builder()
                .algorithm(RateLimitAlgorithm.NO_LIMIT)
                .limit(Integer.MAX_VALUE)
                .windowSeconds(Integer.MAX_VALUE)
                .refillRate(Double.MAX_VALUE)
                .bucketSize(Integer.MAX_VALUE)
                .build();
    }

    public static class Builder{
        private RateLimitAlgorithm algorithm;
        private Integer limit;
        private Integer windowSeconds;
        private Double refillRate;
        private Integer bucketSize;

        public Builder algorithm(RateLimitAlgorithm algorithm){
            this.algorithm=algorithm;
            return this;
        }

        public Builder limit(Integer limit){
            this.limit=limit;
            return this;
        }

        public Builder windowSeconds(Integer seconds) {
            this.windowSeconds = seconds;
            return this;
        }

        public Builder refillRate(Double rate){
            this.refillRate=rate;
            return this;
        }

        public Builder bucketSize(Integer size){
            this.bucketSize=size;
            return this;
        }

        public RateLimitRule build(){
            switch (this.algorithm) {
                case TOKEN_BUCKET:
                    Objects.requireNonNull(refillRate, "refillRate is required for Token Bucket");
                    Objects.requireNonNull(bucketSize, "bucketSize is required for Token Bucket");
                    break;
                case FIXED_WINDOW:
                case SLIDING_WINDOW_LOG:
                    Objects.requireNonNull(limit, "limit is required");
                    Objects.requireNonNull(windowSeconds, "windowSeconds is required");
                    break;
            }
            return new RateLimitRule(this);
        }

    }


    public RateLimitAlgorithm getAlgorithm() {
        return algorithm;
    }

    public Integer getLimit() {
        return limit;
    }

    public Integer getWindowSeconds() {
        return windowSeconds;
    }

    public Double getRefillRate() {
        return refillRate;
    }

    public Integer getBucketSize() {
        return bucketSize;
    }
}
