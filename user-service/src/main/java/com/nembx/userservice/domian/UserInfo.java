package com.nembx.userservice.domian;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Lian
 */
@Data
@TableName("user")
public class UserInfo {
    private String username;
    private String image;
    private String email;
}
