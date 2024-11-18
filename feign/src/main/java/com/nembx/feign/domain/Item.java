package com.nembx.feign.domain;

import lombok.Data;

/**
 * @author Lian
 */
@Data
public class Item {
    private String itemName;
    private int itemPrice;
    private String itemImage;
    private String itemCity;
    private String itemState;
    private String itemAddress;
}
