package com.cloud.workflow;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableSwagger2Doc
@EnableFeignClients
@EnableDiscoveryClient
@SpringBootApplication
public class WorkFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(WorkFlowApplication.class, args);
        System.out.println("ヾ(◍°∇°◍)ﾉﾞ    work-flow启动成功      ヾ(◍°∇°◍)ﾉﾞ" );
    }
}
