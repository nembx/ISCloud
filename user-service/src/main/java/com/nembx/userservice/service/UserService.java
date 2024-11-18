package com.nembx.userservice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nembx.userservice.domian.User;

import java.util.Map;


/**
 * @author Lian
 */

public interface UserService extends IService<User> {
    String produceCode();
    String sendEmail(String email);
    boolean createToken(User user);
    Map<Object,Object> findToken(User user);
    Map<Object,Object> refreshToken(String username);
}
