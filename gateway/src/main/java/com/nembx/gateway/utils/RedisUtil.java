package com.nembx.gateway.utils;

import jakarta.annotation.Resource;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Lian
 */
@Component
public class RedisUtil {
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    public void createRedis(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    public String getKey(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    public Boolean deleteRedis(String key){
        return stringRedisTemplate.delete(key);
    }

    public Boolean hasKey(String key){
        return stringRedisTemplate.hasKey(key);
    }

    public Boolean expire(String key,long time){
        return stringRedisTemplate.expire(key,time,TimeUnit.DAYS);
    }

    public Long getExpire(String key){
        return stringRedisTemplate.getExpire(key);
    }

    public void setTokenKey(String username,String accessToken,String refreshToken){
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken",accessToken);
        tokenMap.put("refreshToken",refreshToken);
        stringRedisTemplate.opsForHash().putAll(username,tokenMap);
    }

    public Map<Object, Object> getTokenKey(String username){
        return stringRedisTemplate.opsForHash().entries(username);
    }
}
