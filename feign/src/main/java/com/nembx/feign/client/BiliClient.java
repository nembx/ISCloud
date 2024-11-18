package com.nembx.feign.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author Lian
 */
@FeignClient(name = "bili",url = "https://api.bilibili.com/pgc/operation/api")
public interface BiliClient {
    @GetMapping("/slideshow?position_id=104")
    Object enter();
}
