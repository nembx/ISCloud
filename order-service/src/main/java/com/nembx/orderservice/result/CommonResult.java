package com.nembx.orderservice.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Lian
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommonResult<T> {
    private int code;
    private String msg;
    private T result;

    public CommonResult(int code,String msg){
        this(code,msg,null);
    }

    public CommonResult(ResponseCodeEnum responseCodeEnum){
        this(responseCodeEnum.getCode(),responseCodeEnum.getMsg());
    }

    public CommonResult(ResponseCodeEnum responseCodeEnum,T result){
        this(responseCodeEnum.getCode(),responseCodeEnum.getMsg(),result);
    }

}
