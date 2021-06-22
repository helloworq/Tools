package com.zlutil.tools.Share;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Img2UrlSpeedUp {
    String value() default "";
}