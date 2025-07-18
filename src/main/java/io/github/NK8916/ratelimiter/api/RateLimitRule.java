package io.github.NK8916.ratelimiter.api;

public class RateLimitRule{
    private RateLimitAlgorithm algorithm;
    private int limit;
    private int windowSeconds;
    private double refillRate;
    private int bucketSize;

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
        private int limit;
        private int windowSeconds;
        private double refillRate;
        private int bucketSize;

        public Builder algorithm(RateLimitAlgorithm algorithm){
            this.algorithm=algorithm;
            return this;
        }

        public Builder limit(int limit){
            this.limit=limit;
            return this;
        }

        public Builder windowSeconds(int seconds) {
            this.windowSeconds = seconds;
            return this;
        }

        public Builder refillRate(double rate){
            this.refillRate=rate;
            return this;
        }

        public Builder bucketSize(int size){
            this.bucketSize=size;
            return this;
        }

        public RateLimitRule build(){
            return new RateLimitRule(this);
        }

    }

}
