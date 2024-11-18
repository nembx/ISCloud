package com.nembx.userservice.service.Impl;

import cn.hutool.core.util.StrUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import cn.hutool.json.JSONUtil;
import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nembx.userservice.domian.Token;
import com.nembx.userservice.domian.User;
import com.nembx.userservice.mapper.UserMapper;
import com.nembx.userservice.service.UserService;
import com.nembx.userservice.util.JwtokenUtil;
import com.nembx.userservice.util.RedisUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Random;

/**
 * @author Lian
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    private RedisUtil redisUtil;

    @Override
    public String produceCode() {
        Random random = new Random();
        String tmp_code = "1234567890abcdefghijklmnopqrstuvwxwzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuffer code = new StringBuffer();
        for (int i = 0; i < 6; i++) {
            code.append(tmp_code.charAt(random.nextInt(58)));
        }
        return code.toString();
    }

    @Override
    public String sendEmail(String email) {
        MailAccount account = new MailAccount();
        account.setHost("smtp.163.com");
        account.setPort(465);
        account.setFrom("lianzhongtao93@163.com");
        account.setPass("LEZRKWSAXSLMJOTR");
        account.setUser("你爹");
        account.setAuth(true);
        account.setSslEnable(true);
        return MailUtil.send(account,email,"验证码","hello"+produceCode(),false);
    }

    @Override
    public boolean createToken(User user) {
        try{
            Token token = new Token();
            token.setAccessToken(JwtokenUtil.createAccessToken(user.getUsername()));
            token.setRefreshToken(JwtokenUtil.createRefreshToken(user.getUsername()));
            redisUtil.setTokenKey("token:"+user.getUsername(),
                    token.getAccessToken(),
                    token.getRefreshToken());
            return redisUtil.expire("token:"+ user.getUsername(),30);
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public Map<Object, Object> findToken(User user) {
        return redisUtil.getTokenKey("token:"+ user.getUsername());
    }

    @Override
    public Map<Object, Object> refreshToken(String username) {
        Token token = new Token();
        token.setAccessToken(JwtokenUtil.createAccessToken(username));
        token.setRefreshToken(JwtokenUtil.createRefreshToken(username));
        redisUtil.deleteRedis("token:" + username);
        redisUtil.setTokenKey("token:" + username,
                    token.getAccessToken(),
                    token.getRefreshToken());
        redisUtil.expire("token:" + username,10);
        return redisUtil.getTokenKey("token:"+ username);
    }
}
