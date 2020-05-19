package com.elite.webdata.redisson.config;


import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Redission 配置类
 */

@Configuration
public class RedissonConfig {

    @Bean(destroyMethod = "shutdown")
    public RedissonClient redissonClient(){
        Config config = new Config();
        //多节点
        //config.useClusterServers().addNodeAddress("127.0.0.1:6379");
        //单节点
        config.useSingleServer().setAddress("redis://127.0.0.1:6379").setPassword("qhm1997").setDatabase(1);
        return Redisson.create(config);
    }
}
