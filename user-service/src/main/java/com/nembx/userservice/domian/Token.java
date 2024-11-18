package com.nembx.userservice.domian;

import lombok.Data;

/**
 * @author Lian
 */
@Data
public class Token {
    private String AccessToken;
    private String RefreshToken;
}
