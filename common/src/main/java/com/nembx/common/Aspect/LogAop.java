package com.nembx.common.Aspect;

import java.lang.annotation.*;

/**
 * @author Lian
 */

@Retention(RetentionPolicy.RUNTIME)
@Documented
@Target(ElementType.METHOD)
public @interface LogAop {
    String type() default  "";
}
