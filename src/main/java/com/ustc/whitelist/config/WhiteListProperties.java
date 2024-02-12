package com.ustc.whitelist.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 注解旨在将应用程序配置中以ustc.whitelist为前缀的属性绑定和映射到该类的实例
 * 保存白名单属性
 * 使得在应用程序中更容易管理和操作与白名单相关的配置
 */
@ConfigurationProperties("ustc.whitelist")
public class WhiteListProperties {
    private String users;// 存储用户的白名单

    // 获取白名单
    public String getUsers(){
        return users;
    }

    public void setUsers(String users){
        this.users = users;
    }
}
