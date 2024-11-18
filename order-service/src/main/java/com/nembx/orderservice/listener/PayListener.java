package com.nembx.orderservice.listener;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.nembx.orderservice.domain.Order;
import com.nembx.orderservice.service.OrderService;
import com.rabbitmq.client.Channel;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * @author Lian
 */

@Component
@RequiredArgsConstructor
@Slf4j
public class PayListener {

    private final OrderService orderService;
//    private final RedisUtil redisUtil;

    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange("pay-exchange"),
            value = @Queue("pay-queue"),
            key = "pay"
    ))
    public void listenPay(String orderNumber, Channel channel) {
        boolean update = orderService.update(new UpdateWrapper<Order>()
                .eq("order_state", "未支付")
                .eq("order_number", orderNumber)
                .set("order_state", "已支付"));
        try {
            if (!update) {
                channel.basicNack(channel.getNextPublishSeqNo(), false, false);
                throw new RuntimeException("支付失败，订单号：" + orderNumber);
            }else {
                log.info("支付成功，订单号：{}", orderNumber);
                channel.basicAck(channel.getNextPublishSeqNo(), false);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    @RabbitListener(bindings = @QueueBinding(
            exchange = @Exchange(value = "delayed.topic",type = "x-delayed-message"),
            value = @Queue(value = "delay.queue",durable = "true"),
            key = {"cancel.#"}
    ))
    public void cancelListener(String orderNumber) {
        if ("未支付".equals(orderService.getOne(new QueryWrapper<Order>()
                        .eq("order_number", orderNumber))
                .getOrderState())){
            orderService.remove(new QueryWrapper<Order>()
                    .eq("order_number", orderNumber));
//        redisUtil.deleteRedis("order:" + orderNumber);
            log.info("支付失败，订单号：{},自动取消订单", orderNumber);
            throw new RuntimeException("支付失败，订单号：" + orderNumber + "，自动取消订单");
        }
    }
}
