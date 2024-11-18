package com.nembx.feign.client;

import com.nembx.feign.domain.Item;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Lian
 */
@FeignClient("item-service")
public interface ItemClient {
    @GetMapping("/item/search/{item_name}")
    Item getByName(@PathVariable String item_name);

    @GetMapping("/item/searchByCity/{item_city}")
    Item getByCity(@PathVariable String item_city);

    @GetMapping("/item/searchItemCount/{itemName}")
    int getItemCount(@PathVariable String itemName);

    @GetMapping("/item/decreaseAmount")
    void decreaseAmount(String itemName);
}
