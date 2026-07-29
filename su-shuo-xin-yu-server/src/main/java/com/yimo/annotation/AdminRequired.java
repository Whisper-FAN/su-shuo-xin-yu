package com.yimo.annotation;

import java.lang.annotation.*;

/**
 * 管理员权限注解
 *
 * @author yimo-team
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AdminRequired {
}
