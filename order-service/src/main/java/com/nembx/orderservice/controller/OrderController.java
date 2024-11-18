package com.nembx.orderservice.controller;

import cn.hutool.core.util.IdUtil;
import cn.hutool.json.JSONUtil;
import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.payment.page.models.AlipayTradePagePayResponse;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.nembx.feign.client.ItemClient;
import com.nembx.orderservice.domain.City;
import com.nembx.orderservice.domain.Order;
import com.nembx.orderservice.result.CommonResult;
import com.nembx.orderservice.result.ResponseCodeEnum;
import com.nembx.orderservice.service.CityService;
import com.nembx.orderservice.service.OrderService;
import com.nembx.orderservice.utils.RedisUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author Lian
 */
@Slf4j
@RestController
@RequestMapping("/order")
@Tag(name = "订单接口", description = "订单接口文档")
public class OrderController {
    @Resource
    private OrderService orderService;

    @Resource
    private CityService cityService;

    @Resource
    private RabbitTemplate rabbitTemplate;

    @Resource
    private RedisUtil redisUtil;

    @Resource
    private ItemClient itemClient;

    @Resource
    private RedissonClient redissonClient;

    RLock lock;
    @PostConstruct
    private void init(){
         lock = redissonClient.getLock("lock");
    }

    @Transactional(rollbackFor = Exception.class)
    @Operation(summary = "添加订单接口")
    @PostMapping("/addOrder")
    public CommonResult<String> addOrder(@RequestBody Order order){
        if (redisUtil.hasHash("order:"+order.getOrderPurchaser(), order.getOrderNumber())){
            log.info("订单已存在");
            return new CommonResult<>(ResponseCodeEnum.FAIL);
        }
        lock.lock();
        itemClient.decreaseAmount(order.getOrderName());
        try {
            order.setOrderNumber(IdUtil.randomUUID().replace("-",""));
            orderService.save(order);
            redisUtil.setHash("order:" + order.getOrderPurchaser(), order.getOrderNumber(), JSONUtil.toJsonStr(order));
            if(null == cityService.getOne(new QueryWrapper<City>().eq("city_name", order.getOrderCity()))){
                City city = new City();
                city.setCityName(order.getOrderCity());
                cityService.save(city);
            }
            City city = new City();
            city.setOrderCount(cityService.getOne(new QueryWrapper<City>().eq("city_name", order.getOrderCity())).getOrderCount()+1);
            cityService.update(city,new QueryWrapper<City>().eq("city_name", order.getOrderCity()));
            rabbitTemplate.convertAndSend("delayed.topic","cancel.order",order.getOrderNumber(),message -> {
                message.getMessageProperties().setDelay(60000);
                return message;
            });
            rabbitTemplate.setReturnsCallback(returnedMessage -> {
                log.info("消息主体: " + returnedMessage.getMessage());
                log.info("应答码: " + returnedMessage.getReplyCode());
                log.info("描述: " + returnedMessage.getReplyText());
                log.info("消息使用的交换器名称: " + returnedMessage.getExchange());
                log.info("消息使用的路由键: " + returnedMessage.getRoutingKey());
                if (returnedMessage.getReplyCode() == 312){
                    throw new RuntimeException("消息发送失败");
                }
            });
        }catch (NullPointerException e){
            e.getMessage();
        }finally {
            lock.unlock();
        }
        return new CommonResult<>(ResponseCodeEnum.SUCCESS);
    }

    @Transactional(rollbackFor = Exception.class)
    @Operation(summary = "更新订单接口")
    @PutMapping("/updateOrder")
    public CommonResult<Order> updateOrder(@RequestBody Order order){
        lock.lock();
        try {
            QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
            queryWrapper
                    .eq("order_date", order.getOrderDate())
                    .eq("order_name", order.getOrderName())
                    .eq("order_state", order.getOrderState())
                    .eq("order_city", order.getOrderCity())
                    .eq("order_address", order.getOrderAddress());
            orderService.update(order, queryWrapper);
            redisUtil.setKey("order:"+order.getOrderNumber(), JSONUtil.toJsonStr(order));
            redisUtil.expireSeconds("order:"+order.getOrderNumber(), 20);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return new CommonResult<>(200, "更新成功", order);
    }


    @Transactional(rollbackFor = Exception.class)
    @Operation(summary = "删除订单接口")
    @PostMapping("/deleteOrder")
    public CommonResult<String> deleteOrder(@RequestBody Order order){
        lock.lock();
        try {
            orderService.remove(new QueryWrapper<Order>().eq("order_number", order.getOrderNumber()));
            redisUtil.deleteHash("order:"+order.getOrderPurchaser(), order.getOrderNumber());
            String cityName = orderService.getOne(new QueryWrapper<Order>().last("limit 1").eq("order_name", order.getOrderName())).getOrderCity();
            City city = new City();
            city.setOrderCount(cityService.getOne(new QueryWrapper<City>().eq("city_name", cityName)).getOrderCount()-1);
            cityService.update(city, new QueryWrapper<City>().eq("city_name", cityName));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
        return new CommonResult<>(200, "删除成功");
    }

    @Operation(summary = "查询订单接口")
    @GetMapping("/findOrder")
    public CommonResult<Order> findOrder(@RequestBody Order order){
        if (redisUtil.hasHash("order:"+order.getOrderPurchaser(), order.getOrderNumber())){
            String json = JSONUtil.toJsonStr(redisUtil.getHash("order:" + order.getOrderPurchaser(), order.getOrderNumber()));
            Order od = JSONUtil.toBean(json, Order.class);
            log.info("从缓存中获取数据");
            return new CommonResult<>(ResponseCodeEnum.SUCCESS,od);
        }
        QueryWrapper<Order> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("order_name", order.getOrderName());
        Order od = orderService.getOne(queryWrapper);
        if(null == od){
            return new CommonResult<>(404, "没有找到相关订单");
        }
        return new CommonResult<>(200, "查询成功", od);
    }

    @Operation(summary = "查询所有订单接口")
    @GetMapping ("/findAllOrder")
    public CommonResult<List<Order>> findAllOrder(){
        if(redisUtil.hasKey("order")){
            List<Order> list = JSONUtil.parseArray(redisUtil.getKey("order")).toList(Order.class);
            log.info("从缓存中获取数据");
            return new CommonResult<>(200, "查询成功", list);
        }
        redisUtil.setKey("order", JSONUtil.toJsonStr(orderService.listMaps()));
        redisUtil.expireSeconds("order", 20);
        log.info("从数据库中获取数据");
        List<Order> list = JSONUtil.parseArray(redisUtil.getKey("order")).toList(Order.class);
        return new CommonResult<>(200, "查询成功",list);
    }

    @Operation(summary = "查询某用户的订单接口")
    @GetMapping("/findOrderByPurchaser/{purchaser}")
    public CommonResult<List<Order>> findOrderByPurchaser(@PathVariable String purchaser){
        if (redisUtil.hasKey("order:" + purchaser)){
            Map<Object, Object> hashKeys = redisUtil.getHashKeys("order:" + purchaser);
            List<Order> list = hashKeys.keySet().stream().map(v -> JSONUtil.parseObj(v).toBean(Order.class)).toList();
            log.info("从缓存中获取数据");
            return new CommonResult<>(ResponseCodeEnum.SUCCESS, list);
        }
        List<Map<String, Object>> orderList = orderService.listMaps(new QueryWrapper<Order>().eq("order_purchaser", purchaser));
//        redisUtil.setKey("order:" + purchaser, JSONUtil.toJsonStr(orderList));
//        redisUtil.expireSeconds("order:" + purchaser, 200);
        log.info("从数据库中获取数据");
        orderList.forEach(
                v -> redisUtil.setHash("order:" + purchaser, String.valueOf(v.get("order_number")), JSONUtil.toJsonStr(v))
        );
        Map<Object, Object> hashKeys = redisUtil.getHashKeys("order:" + purchaser);
        List<Order> list = hashKeys.keySet().stream().map(v -> JSONUtil.parseObj(v).toBean(Order.class)).toList();
        return new CommonResult<>(ResponseCodeEnum.SUCCESS, list);
    }

    @Operation(summary = "查询订单总数接口")
    @GetMapping("/findOrderCount")
    public CommonResult<Long> findOrderCount(){
        return new CommonResult<>(ResponseCodeEnum.SUCCESS, orderService.count());
    }

    @Operation(summary = "查询某用户某订单总数接口")
    @GetMapping ("/findOrderCountByName/{order_purchaser}")
    public CommonResult<Long> findOrderCountByName(@PathVariable String order_purchaser){
        return new CommonResult<>(200, "查询成功",
                orderService.count(new QueryWrapper<Order>().eq("order_purchaser", order_purchaser)));
    }

    @Operation(summary = "查询某城市的订单总数")
    @GetMapping ("/findOrderByCityCount/{order_city}")
    public CommonResult<Long> findOrderByCity(@PathVariable String order_city){
        return new CommonResult<>(ResponseCodeEnum.SUCCESS, orderService.count(new QueryWrapper<Order>().eq("order_city", order_city)));
    }

    @Operation(summary = "查询非广州，上海，深圳，香港的订单总数")
    @GetMapping("/findOrderOther")
    public CommonResult<Long> findOrderOther(){
        return new CommonResult<>(ResponseCodeEnum.SUCCESS.getCode(), "查询成功", orderService.count(new QueryWrapper<Order>()
                .ne("order_city", "广州")
                .ne("order_city", "上海")
                .ne("order_city","深圳")
                .ne("order_city","香港")));
    }

    @Operation(summary = "查询所有城市订单总数")
    @GetMapping("/findAllCount")
    public CommonResult<List<Map<String,Object>>> findAllCount(){
        return new CommonResult<>(ResponseCodeEnum.SUCCESS, cityService.listMaps());
    }

    @Operation(summary = "查询所有订单日期")
    @GetMapping("/findDate")
    public CommonResult<List<Map<String,Object>>> findDate(){
        return new CommonResult<>(ResponseCodeEnum.SUCCESS.getCode(), "查询成功",
                orderService.listObjs(new QueryWrapper<Order>().select("order_date")));
    }

//    @Operation(summary = "更新订单状态")
//    @PutMapping("/updateOrderStatus")
//    public CommonResult<Order> updateOrderStatus(@Param("orderNumber") String orderNumber){
//        if (orderService.getOne(new QueryWrapper<Order>().eq("order_number", orderNumber)).getOrderState() == "已支付"){
//            return new CommonResult<>(200, "订单已支付，请勿多次更新");
//        }
//        rabbitTemplate.convertAndSend("pay-exchange", "pay", orderNumber);
//        return new CommonResult<>(200, "更新成功");
//    }

    @Transactional(rollbackFor = Exception.class)
    @Operation(summary = "支付接口")
    @PostMapping("/pay")
    public String pay(String orderName,String orderNumber,String orderPrice){
        if (Objects.equals(orderService.getOne(new QueryWrapper<Order>()
                        .eq("order_number", orderNumber))
                .getOrderState(), "已支付")){
            throw new RuntimeException("订单已支付，请勿多次支付");
        }
        AlipayTradePagePayResponse response = null;
        try {
            response = Factory.Payment.Page().pay(orderName, orderNumber, orderPrice,"http://localhost:8080/order/notifyUrl");
        }catch (Exception e){
            log.error("支付失败");
            throw new RuntimeException("支付失败");
        }
        return response.getBody();
    }

    @Transactional(rollbackFor = Exception.class)
    @Operation(summary = "支付回调接口")
    @GetMapping("/notifyUrl")
    public String notifyUrl(HttpServletRequest request) throws Exception {
        String out_trade_no = request.getParameter("out_trade_no");
        String total_amount = request.getParameter("total_amount");
        String trade_no = request.getParameter("trade_no");
        Map<String,String> params = new HashMap<>();
        Map<String, String[]> parameterMap = request.getParameterMap();
        for (String entry : parameterMap.keySet()) {
            params.put(entry,request.getParameter(entry));
        }
        if (Factory.Payment.Common().verifyNotify(params)){
            log.info("支付成功");
            log.info("订单号：{}", out_trade_no);
            log.info("支付金额：{}", total_amount);
            log.info("交易号：{}", trade_no);
            rabbitTemplate.convertAndSend("pay-exchange", "pay", out_trade_no);
            return "支付成功";
        }
        return "支付失败";
    }
}
