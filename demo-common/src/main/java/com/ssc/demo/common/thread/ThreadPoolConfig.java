package com.ssc.demo.common.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 类说明 （必填）
 * @author Administrator
 * @date 2022/07/29 10:24 
 */
@Configuration
public class ThreadPoolConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor(){
        return ThreadPool.instance().executor;
    }
}
