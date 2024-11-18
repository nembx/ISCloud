package com.nembx.orderservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nembx.orderservice.domain.Order;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Lian
 */
@Mapper
public interface OrderMapper extends BaseMapper<Order> {
}
