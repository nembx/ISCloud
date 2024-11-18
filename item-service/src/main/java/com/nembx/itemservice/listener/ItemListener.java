package com.nembx.itemservice.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.nembx.itemservice.domain.Item;
import com.nembx.itemservice.service.ItemService;
import jakarta.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author Lian
 */

//@Component
@RequiredArgsConstructor
@Slf4j
public class ItemListener {

//    @Resource
//    private ItemService itemService;
//    @RabbitListener(bindings = @QueueBinding(
//            exchange = @Exchange("item-exchange"),
//            value = @Queue("item-queue"),
//            key = "decrease"
//    ))
//    public void decrease(String orderName){
//        System.out.println("收到的信息是" + orderName);
//        int count = itemService.getOne(new QueryWrapper<Item>().eq("item_name", orderName)).getItemAmount();
//        if (count == 0){
//            log.error("库存不足");
//            throw new RuntimeException("库存不足");
//        }else {
//            itemService.update(new UpdateWrapper<Item>()
//                    .eq("item_name", orderName)
//                    .set("item_amount", count - 1));
//        }
//    }
}
