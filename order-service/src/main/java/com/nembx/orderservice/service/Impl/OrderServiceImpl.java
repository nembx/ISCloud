package com.nembx.orderservice.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nembx.orderservice.domain.Order;
import com.nembx.orderservice.mapper.OrderMapper;
import com.nembx.orderservice.service.OrderService;
import org.springframework.stereotype.Service;

/**
 * @author Lian
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
