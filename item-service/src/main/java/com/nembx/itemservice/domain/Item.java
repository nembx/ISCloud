package com.nembx.itemservice.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

/**
 * @author Lian
 */
@Data
@TableName("item")
public class Item implements Serializable {
    private int itemId;
    private String itemName;
    private int itemPrice;
    private String itemImage;
    private String itemCity;
    private String itemState;
    private String itemAddress;
    private double itemLongitude;
    private double itemLatitude;
    private int itemAmount;

    @Transient
    private final static long serialVersionUID = 1L;
}
