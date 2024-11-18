package com.nembx.userservice;

import com.nembx.feign.client.BiliClient;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author Lian
 */
@SpringBootApplication(scanBasePackages = {
        "com.nembx.userservice",
        "com.nembx.common"
})
@EnableFeignClients(clients = BiliClient.class)
public class UserServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(UserServiceApplication.class, args);
    }
}
