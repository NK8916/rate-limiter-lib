package io.github.NK8916.ratelimiter.core;

import io.github.NK8916.ratelimiter.api.RateLimitRule;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class JedisRedisClient implements RedisClient{
    private final JedisPool jedisPool;
    private final String tokenBucketLua;
    private final String slidingWindowLua;

    public JedisRedisClient(String host,int port) throws IOException {
        this.jedisPool=new JedisPool(host,port);
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("token_bucket.lua")) {
            if (is == null) throw new FileNotFoundException("token_bucket.lua not found in classpath");
            this.tokenBucketLua = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("sliding_window.lua")) {
            if (is == null) throw new FileNotFoundException("sliding_window.lua not found in classpath");
            this.slidingWindowLua = new String(is.readAllBytes(), StandardCharsets.UTF_8);
        }
    }

    @Override
    public long incr(String key){
        try(Jedis jedis=jedisPool.getResource()){
            return jedis.incr(getRedisKey(key));
        }
    }

    @Override
    public void expire(String key,int seconds){
        try(Jedis jedis=jedisPool.getResource()){
            jedis.expire(getRedisKey(key),seconds);
        }
    }

    @Override
    public void delete(String key){
        try(Jedis jedis=jedisPool.getResource()){
            jedis.del(getRedisKey(key));
        }
    }

    @Override
    public String ping(){
        try(Jedis jedis=jedisPool.getResource()){
            return jedis.ping("ping");
        }
    }

    @Override
    public boolean runTokenBucketLua(String key, RateLimitRule rule){
        try(Jedis jedis=jedisPool.getResource()){
            long now=System.currentTimeMillis()/1000;
            Object result=jedis.eval(tokenBucketLua, Collections.singletonList(getRedisKey(key)), List.of(String.valueOf(now),String.valueOf(rule.getRefillRate()),String.valueOf(rule.getBucketSize()),"1"));
            return "1".equals(result.toString());
        }
    }


    @Override
    public boolean runSlidingWindowLua(String key, RateLimitRule rule){
        try(Jedis jedis=jedisPool.getResource()){
            long now=System.currentTimeMillis()/1000;
            Object result=jedis.eval(slidingWindowLua, Collections.singletonList(getRedisKey(key)), List.of(String.valueOf(now),String.valueOf(rule.getWindowSeconds()),String.valueOf(rule.getLimit()),"1"));
            return "1".equals(result.toString());
        }
    }

    @Override
    public void close(){
        jedisPool.close();
    }

    @Override
    public Set<String> keys(String pattern) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.keys(pattern);
        }
    }

    protected String getRedisKey(String key){
        return "rate:limiter:"+key;
    }

}
