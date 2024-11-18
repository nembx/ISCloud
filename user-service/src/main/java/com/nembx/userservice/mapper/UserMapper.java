package com.nembx.userservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nembx.userservice.domian.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Lian
 */
@Mapper
public interface UserMapper extends BaseMapper<User> {
}
