package com.nembx.userservice.controller;

import cn.hutool.jwt.JWTUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nembx.common.Aspect.LogAop;
import com.nembx.common.Aspect.RunTimeRecord;
import com.nembx.userservice.domian.User;
import com.nembx.userservice.domian.UserInfo;
import com.nembx.userservice.result.CommonResult;
import com.nembx.userservice.service.UserInfoService;
import com.nembx.userservice.service.UserService;
import com.nembx.userservice.util.JwtokenUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Lian
 */
@RestController
@RequestMapping("/user")
@Tag(name = "用户接口", description = "用户接口文档")
public class UserController {
    @Resource
    private UserService userService;

    @Resource
    private UserInfoService userInfoService;

    @Operation(summary = "登录接口")
    @LogAop(type = "登录")
    @RunTimeRecord(value = "登录")
    @PostMapping("/login")
    public CommonResult<Map<Object, Object>> login(@RequestBody User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", user.getUsername())
                    .eq("password", user.getPassword());
        if (null == userService.getOne(queryWrapper)){
            return new CommonResult<>(404, "账号不存在");
        }
        if(userService.findToken(user).isEmpty() || userService.findToken(user) == null){
            userService.refreshToken(user.getUsername());
        }
        return new CommonResult<>(200, "登录成功", userService.findToken(user));
    }

    @Operation(summary = "注册接口")
    @LogAop(type = "注册")
    @PostMapping("/register")
    public CommonResult<Boolean> register(@RequestBody User user){
        if(login(user).getCode() == 200){
            return new CommonResult<>(409, "账号已存在");
        }
        userService.save(user);
        return new CommonResult<>(201, "创建成功",userService.createToken(user));
    }

    @Operation(summary = "生成验证码接口")
    @LogAop(type = "生成验证码")
    @GetMapping("/produce/code")
    public CommonResult<String> produceCode(){
        return new CommonResult<>(201, "成功",userService.produceCode());
    }

    @Operation(summary = "发送邮箱验证码接口")
    @LogAop(type = "发送邮箱验证码")
    @PostMapping("/send/email/{email}")
    public String sendEmail(@PathVariable String email){
        return userService.sendEmail(email);
    }

    @Operation(summary = "更新用户信息接口")
    @LogAop(type = "更新用户信息")
    @PutMapping ("/update/img")
    public Boolean updateImg(@RequestBody User user){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("image",user.getImage());
        return userService.update(user,queryWrapper);
    }

    @Operation(summary = "获取用户信息接口")
    @LogAop(type = "获取用户信息")
    @GetMapping("/getInfo")
    public CommonResult<UserInfo> getUserInfo(@RequestHeader("Authorization") String token){
        if (!JwtokenUtil.verifyToken(token)){
            return new CommonResult<>(401, "token过期");
        }
        Object username = JWTUtil.parseToken(token).getPayload("username");
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username",username);
        return new CommonResult<>(201, "成功",userInfoService.getOne(queryWrapper));
    }

    @Operation(summary = "刷新Token接口")
    @LogAop(type = "刷新Token")
    @GetMapping("/refreshToken")
    public CommonResult<Map<Object, Object>> getToken(@RequestHeader("Authorization") String token){
        String username = JWTUtil.parseToken(token).getPayload("username").toString();
        return new CommonResult<>(201, "刷新Token成功",userService.refreshToken(username));
    }

}
