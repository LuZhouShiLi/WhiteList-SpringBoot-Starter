package com.ustc.whitelist;

import com.alibaba.fastjson.JSON;
import com.ustc.whitelist.annotation.DoWhiteList;
import org.apache.commons.beanutils.BeanUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.annotation.Retention;
import java.lang.reflect.Method;

/**
 * aspect 定义切面类
 * Component 注解 将该类生成为Bean对象
 */
@Aspect
@Component
public class DoJoinPoint {

    private Logger logger = LoggerFactory.getLogger(DoJoinPoint.class);


    @Resource
    private String whiteListConfig;


    @Pointcut("@annotation(com.ustc.whitelist.annotation.DoWhiteList)")
    public void aopPoint() {
    }

    /**
     * 定义切点
     * 拦截方法 获取方法的自定义注解
     * 最后就是对当前拦截方法校验效果的操作 是拦截还是放行 拦截就是返回自定义注解配置的JSON信息生成对象返回  放行就是调用jp.proceed()方法 让整个代码块继续向下执行
     * 通过getAnnotation 获取目标方法上面的DoWhiteList的注解信息
     * 根据注解中的key属性获取需要进行白名单验证的字段值
     * 根绝whiteListConfig 中配置的白名单内容进行验证，如果存在于白名单中，则放行，否则根据returnJson属性返回指定的对象
     *
     * @param jp
     * @return
     * @throws Throwable
     */
    @Around("aopPoint()")
    public Object doRouter(ProceedingJoinPoint jp) throws Throwable {
        // 获取内容
        Method method = getMethod(jp);
        DoWhiteList whiteList = method.getAnnotation(DoWhiteList.class);// 获取方法的注解

        // 获取字段值
        String keyValue = getFiledValue(whiteList.key(), jp.getArgs());// 获取目标方法参数中对应的字段值
        logger.info("middleware whitelist handler method：{} value：{}", method.getName(), keyValue);
        if (null == keyValue || "".equals(keyValue)) return jp.proceed();

        String[] split = whiteListConfig.split(",");

        // 白名单过滤
        for (String str : split) {
            if (keyValue.equals(str)) {
                return jp.proceed();
            }
        }

        // 拦截
        return returnObject(whiteList, method);
    }

    /**
     * 通过JoinPoint获取目标方法的反射对象
     * @param jp
     * @return
     * @throws NoSuchMethodException
     */
    private Method getMethod(JoinPoint jp) throws NoSuchMethodException {
        Signature sig = jp.getSignature();
        MethodSignature methodSignature = (MethodSignature) sig;
        return jp.getTarget().getClass().getMethod(methodSignature.getName(), methodSignature.getParameterTypes());
    }

    /**
     * 根据方法的返回类型和returnJson属性返回相应的对象
     * @param whiteList
     * @param method
     * @return
     * @throws IllegalAccessException
     * @throws InstantiationException
     */
    private Object returnObject(DoWhiteList whiteList, Method method) throws IllegalAccessException, InstantiationException {
        Class<?> returnType = method.getReturnType();
        String returnJson = whiteList.returnJson();
        if ("".equals(returnJson)) {
            return returnType.newInstance();
        }
        return JSON.parseObject(returnJson, returnType);
    }

    /**
     * 根据给定的字段名获取目标方法的参数中对应字段的值
     * @param filed
     * @param args
     * @return
     */
    private String getFiledValue(String filed, Object[] args) {
        String filedValue = null;
        for (Object arg : args) {
            try {
                if (null == filedValue || "".equals(filedValue)) {
                    filedValue = BeanUtils.getProperty(arg, filed);
                } else {
                    break;
                }
            } catch (Exception e) {
                if (args.length == 1) {
                    return args[0].toString();
                }
            }
        }
        return filedValue;
    }






}
