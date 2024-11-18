package com.nembx.userservice.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nembx.userservice.domian.UserInfo;
import com.nembx.userservice.mapper.UserInfoMapper;
import com.nembx.userservice.service.UserInfoService;
import org.springframework.stereotype.Service;

/**
 * @author Lian
 */
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo> implements UserInfoService {
}
