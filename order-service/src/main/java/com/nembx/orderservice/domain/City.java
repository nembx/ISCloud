package com.nembx.orderservice.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author Lian
 */
@Data
@TableName("city")
public class City {
    private String cityName;
    private int orderCount;
}
