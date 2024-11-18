package com.nembx.orderservice.domain;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.nembx.feign.domain.Item;
import lombok.Data;
import org.springframework.data.annotation.Transient;


/**
 * @author Lian
 */
@Data
@TableName("show_order")
public class Order {
    private String orderDate;
    private String orderName;
    @TableId
    private String orderNumber;
    private String orderPurchaser;
    private String orderCity;
    private int orderPrice;
    private String orderState;
    private String orderAddress;
}
