package com.nembx.userservice.domian;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Lian
 */
@Data
@TableName("user")
public class User {
    private String id;
    private String username;
    private String email;
    private String password;
    private String image;
}
