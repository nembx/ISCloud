package com.nembx.gateway.filter;

import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTUtil;
import com.nembx.gateway.properties.Authproperties;
import com.nembx.gateway.utils.JwtokenUtil;
import com.nembx.gateway.utils.RedisUtil;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * @author Lian
 */


@Order(-1)
@Component
@Slf4j
@RequiredArgsConstructor
public class MyGlobalFilter implements GlobalFilter {
    @Resource
    private final Authproperties authproperties;

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    private final RedisUtil redisUtil;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        if (isSkip(request.getPath().toString())){
            return chain.filter(exchange);
        }
        log.info("请求路径：{}",request.getPath());
        String token = null;
        List<String> authorization = request.getHeaders().get("Authorization");
        if (authorization != null && !authorization.isEmpty()) {
            token = authorization.get(0);
        }
        if (!JwtokenUtil.verifyToken(token)){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            log.error("token过期或不存在");
            return response.setComplete();
        }
        String jwToken;
        String name;
        try {
            JWT jwt = JWTUtil.parseToken(token);
            jwToken = jwt.getPayload().toString();
            name = jwt.getPayload("username").toString();
            System.out.println(jwToken);
            if(redisUtil.getTokenKey("token:" + name).get("accessToken").toString().equals(token)){
                return chain.filter(exchange);
            }else if(redisUtil.getTokenKey("token:" + name).get("refreshToken").toString().equals(token)){
                return chain.filter(exchange);
            }
            throw new RuntimeException("Token过期或不存在");
        }catch (Exception e){
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.UNAUTHORIZED);
            return response.setComplete();
        }
    }
    private boolean isSkip(String url){
        for (String path : authproperties.getSkip()){
            if (antPathMatcher.match(path,url)){
                return true;
            }
        }
        return false;
    }
}
