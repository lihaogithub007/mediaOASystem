package com.yuyu.soft.base.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义 资源url 注解
 *                       
 * @Filename: SecurityMapping.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ java.lang.annotation.ElementType.METHOD })
public @interface SecurityMapping {

    /**
     * 资源名称
     */
    String res_name() default "";

    /**
     * 资源url
     */
    String res_url() default "";

    /**
     * 资源组名称
     */
    String res_group() default "";

}