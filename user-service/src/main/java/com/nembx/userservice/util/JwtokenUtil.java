package com.nembx.userservice.util;

import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import cn.hutool.jwt.JWTValidator;
import io.swagger.annotations.ApiOperation;


import java.util.Date;

/**
 * @author Lian
 */
public class JwtokenUtil {
    private static final String JWT_SECRET = "nembxisapoorman@";

    private static final Date EXPIRE_DATE = DateUtil.offset(DateUtil.date(), DateField.DAY_OF_WEEK, 1);

    @ApiOperation("创建accessToken")
    public static String createAccessToken(String username) {
        return JWT.create()
                .setPayload("username", username)
                .setIssuer("nembx")
                .setIssuedAt(DateUtil.date())
                .setExpiresAt(DateUtil.date(DateUtil.offset(DateUtil.date(), DateField.HOUR, 10)))
                .setKey(JWT_SECRET.getBytes())
                .sign();
    }

    @ApiOperation("创建refreshToken")
    public static String createRefreshToken(String username) {
        return JWT.create()
                .setPayload("username", username)
                .setIssuer("nembx")
                .setIssuedAt(DateUtil.date())
                .setExpiresAt(DateUtil.date(DateUtil.offset(DateUtil.date(), DateField.DAY_OF_WEEK, 7)))
                .setKey(JWT_SECRET.getBytes())
                .sign();
    }

    @ApiOperation("验证token")
    public static boolean verifyToken(String token) {
        try {
            JWTValidator.of(token).validateDate(DateUtil.date());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
