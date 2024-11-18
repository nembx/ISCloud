package com.nembx.orderservice.utils;

import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.annotation.Resource;
import org.springframework.data.redis.connection.DataType;
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

    @ApiOperation("创建key")
    public void setKey(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    @ApiOperation("获取key")
    public String getKey(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }


    @ApiOperation("创建hash")
    public void setHash(String key,String hashKey,String value){
        stringRedisTemplate.opsForHash().put(key,hashKey,value);
    }

    @ApiOperation("获取hash")
    public Object getHash(String key,String hashKey){
        return stringRedisTemplate.opsForHash().get(key,hashKey);
    }

    @ApiOperation("判断hash是否存在")
    public Boolean hasHash(String key,String hashKey){
        return stringRedisTemplate.opsForHash().hasKey(key,hashKey);
    }

    @ApiOperation("删除hash")
    public Boolean deleteHash(String key,String hashKey){
        return stringRedisTemplate.opsForHash().delete(key,hashKey) > 0;
    }

    @ApiOperation("获取hash的所有key")
    public Map<Object,Object> getHashKeys(String key){
        return stringRedisTemplate.opsForHash().entries(key);
    }

    @ApiOperation("删除key")
    public Boolean deleteRedis(String key){
        return stringRedisTemplate.delete(key);
    }

    @ApiOperation("判断key是否存在")
    public Boolean hasKey(String key){
        return stringRedisTemplate.hasKey(key);
    }

    @ApiOperation("设置过期时间-小时")
    public Boolean expireHours(String key,long time){
        return stringRedisTemplate.expire(key,time,TimeUnit.HOURS);
    }

    @ApiOperation("设置过期时间-分钟")
    public Boolean expireMinutes(String key,long time){
        return stringRedisTemplate.expire(key,time,TimeUnit.MINUTES);
    }

    @ApiOperation("设置过期时间-秒")
    public Boolean expireSeconds(String key,long time){
        return stringRedisTemplate.expire(key,time,TimeUnit.SECONDS);
    }

    @ApiOperation("获取过期时间")
    public Long getExpire(String key){
        return stringRedisTemplate.getExpire(key);
    }

    @ApiOperation("获取key的类型")
    public DataType getType(String key){
        return stringRedisTemplate.type(key);
    }

    @ApiOperation("设置Token")
    public void setTokenKey(String username,String accessToken,String refreshToken){
        Map<String,String> tokenMap = new HashMap<>();
        tokenMap.put("accessToken",accessToken);
        tokenMap.put("refreshToken",refreshToken);
        stringRedisTemplate.opsForHash().putAll(username,tokenMap);
    }

    @ApiOperation("获取HashMap的Token")
    public Map<Object, Object> getTokenKey(String username){
        return stringRedisTemplate.opsForHash().entries(username);
    }
}
