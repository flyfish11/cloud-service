package com.cloud.appmanage;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.result.view.freemarker.FreeMarkerConfigurer;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2Doc
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@EnableSwagger2
public class AppmanageCenterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppmanageCenterApplication.class, args);
    }
    @Bean
    public FreeMarkerConfigurer freemarkerConfig()  {
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        configurer.setDefaultEncoding("UTF-8");
        return configurer;
    }

}
