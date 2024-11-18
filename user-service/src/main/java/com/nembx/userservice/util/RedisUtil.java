package com.nembx.userservice.util;

import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty("创建key")
    public void createRedis(String key,String value){
        stringRedisTemplate.opsForValue().set(key,value);
    }

    @ApiModelProperty("获取key")
    public String getKey(String key){
        return stringRedisTemplate.opsForValue().get(key);
    }

    @ApiModelProperty("删除key")
    public Boolean deleteRedis(String key){
        return stringRedisTemplate.delete(key);
    }

    @ApiModelProperty("判断key是否存在")
    public Boolean hasKey(String key){
        return stringRedisTemplate.hasKey(key);
    }

    @ApiModelProperty("设置过期时间")
    public Boolean expire(String key,long time){
        return stringRedisTemplate.expire(key,time,TimeUnit.HOURS);
    }

    @ApiModelProperty("获取过期时间")
    public Long getExpire(String key){
        return stringRedisTemplate.getExpire(key);
    }

    @ApiModelProperty("获取key的类型")
    public DataType getType(String key){
        return stringRedisTemplate.type(key);
    }

    @ApiModelProperty("设置Token")
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
