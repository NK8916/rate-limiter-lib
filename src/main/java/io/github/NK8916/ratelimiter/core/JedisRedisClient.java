package io.github.NK8916.ratelimiter.core;

import io.github.NK8916.ratelimiter.api.RateLimitRule;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Collections;
import java.util.List;

public class JedisRedisClient implements RedisClient{
    private final JedisPool jedisPool;
    private final String tokenBucketLua;
    private final String slidingWindowLua;

    public JedisRedisClient(String host,int port){
        this.jedisPool=new JedisPool(host,port);
        this.tokenBucketLua="";
        this.slidingWindowLua="";
    }

    @Override
    public long incr(String key){
        try(Jedis jedis=jedisPool.getResource()){
            return jedis.incr(key);
        }
    }

    @Override
    public void expire(String key,int seconds){
        try(Jedis jedis=jedisPool.getResource()){
            jedis.expire(key,seconds);
        }
    }

    @Override
    public boolean runTokenBucketLua(String key, RateLimitRule rule){
        try(Jedis jedis=jedisPool.getResource()){
            long now=System.currentTimeMillis()/1000;

            Object result=jedis.eval(tokenBucketLua, Collections.singletonList(key), List.of(String.valueOf(now),String.valueOf(rule.getRefillRate()),String.valueOf(rule.getBucketSize()),"1"));
            return "1".equals(result.toString());
        }
    }


    @Override
    public boolean runSlidingWindowLua(String key, RateLimitRule rule){
        try(Jedis jedis=jedisPool.getResource()){
            long now=System.currentTimeMillis()/1000;

            Object result=jedis.eval(tokenBucketLua, Collections.singletonList(key), List.of(String.valueOf(now),String.valueOf(rule.getRefillRate()),String.valueOf(rule.getBucketSize()),"1"));
            return "1".equals(result.toString());
        }
    }

    @Override
    public void close(){
        jedisPool.close();
    }

}
