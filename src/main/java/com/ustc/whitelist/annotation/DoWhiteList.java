package com.ustc.whitelist.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义注解 用于标记方法 将方法添加为白名单功能 并在编译之后的字节码中保留相关信息
 * Retention 指定该注解在编译期间保留，将在编译之后存在于编译后的字节码文件中，但是在运行时不可以使用
 * Target 指定该方法只能用于方法上
 * key 用于白名单的键值
 * returnJson 用于指定返回的Json格式
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface DoWhiteList {

    /**
     * key 的作用是配置当前接口入参需要提取的属性
     * @return
     */
    String key() default "";

    /**
     * returnJson 的作用是在我们拦截到用户请求后需要给一个返回信息
     * @return
     */
    String returnJson() default "";
}
