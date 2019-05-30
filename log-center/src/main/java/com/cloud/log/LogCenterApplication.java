package com.cloud.log;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 日志中心
 * 
 * @author hlxd  d asfdafjiagou@163.com
 *
 */
@EnableSwagger2Doc
@EnableDiscoveryClient
@SpringBootApplication
public class LogCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(LogCenterApplication.class, args);
	}

}