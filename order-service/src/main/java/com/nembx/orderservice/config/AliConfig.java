package com.nembx.orderservice.config;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import jakarta.annotation.PostConstruct;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Lian
 */
@Component
@Data
@ConfigurationProperties(prefix = "alipay")
public class AliConfig {
    private String appId;
    private String appPrivateKey;
    private String publicKey;
    private String notifyUrl;

    @PostConstruct
    public void init(){
        Config config = new Config();
        config.protocol = "https";
        config.gatewayHost = "openapi-sandbox.dl.alipaydev.com";
        config.signType = "RSA2";
        config.appId = this.appId;
        config.merchantPrivateKey = this.appPrivateKey;
        config.alipayPublicKey = this.publicKey;
        config.notifyUrl = this.notifyUrl;
        Factory.setOptions(config);
        System.out.println("支付宝初始化成功");
    }
}
