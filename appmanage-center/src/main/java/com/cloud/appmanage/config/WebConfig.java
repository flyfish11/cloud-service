package com.cloud.appmanage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Web相关配置
 *
 * @author yulj
 * @create: 2019/05/15 09:34
 */

@Configuration
public class WebConfig {

    /**
     * RestTemplate BEAN注入
     */
    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }
}
