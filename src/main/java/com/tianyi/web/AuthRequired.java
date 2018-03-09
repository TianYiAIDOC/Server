package com.tianyi.web;

import java.lang.annotation.*;

/**
 * Created by lingqing.wan on 2015/12/29.
 */
@Inherited
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthRequired {
}
