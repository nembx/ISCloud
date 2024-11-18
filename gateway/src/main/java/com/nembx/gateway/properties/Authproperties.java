package com.nembx.gateway.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Lian
 */
@Data
@Component
@ConfigurationProperties(prefix = "nembx.auth")
public class Authproperties {
    private List<String> skip;

}
