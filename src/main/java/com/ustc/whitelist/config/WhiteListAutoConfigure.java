package com.ustc.whitelist.config;


import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration 注解表明这是一个配置类
 * ConfigurationOnClass 表示当类路径中存在WhiteListProperties类 才会应用这个自自动配置
 * EnableConfigurationProperties  用于启动对WhiteListProperties类型的配置属性的支持
 * 实现自动配置类 用于根据应用的配置和条件来创建名为whiteListConfig 的Bean 该Bean用于提供白名单配置信息
 */

// 可以算作是一个组件注解，在 SpringBoot 启动时可以进行加载创建出 Bean 文
@Configuration
@ConditionalOnClass(WhiteListProperties.class)
@EnableConfigurationProperties(WhiteListProperties.class)
public class WhiteListAutoConfigure {

    /**
     * 第二个注解 表示 当ioc容器中不存在名为whiteListConfig 的bean 创建这个bean资源
     * 整个方法会在配置信息和Bean注册完成后，开始被实例化加载到 Spring 中
     * @param properties
     * @return
     */
    @Bean("whiteListConfig")
    @ConditionalOnMissingBean
    public String whiteListConfig(WhiteListProperties properties){
        return properties.getUsers();
    }
}
