package com.nembx.common.Aspect;

import java.lang.annotation.*;

/**
 * @author Lian
 */

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RunTimeRecord {
    String value() default "";
}
