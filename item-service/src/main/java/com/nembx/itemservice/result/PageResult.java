package com.nembx.itemservice.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageResult<T> {
    private int code;
    private String msg;
    private long pageTotal;
    private long pageNum;
    private long pageCurrent;
    private T result;

    public PageResult(int code,String msg){
        this(code,msg,0,0,0,null);
    }
}
