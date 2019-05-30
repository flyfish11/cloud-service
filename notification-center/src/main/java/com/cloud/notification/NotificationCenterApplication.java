package com.cloud.notification;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 通知中心
 * 
 * @author hlxd
 *
 */
@EnableSwagger2Doc
@EnableDiscoveryClient
@SpringBootApplication
public class NotificationCenterApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationCenterApplication.class, args);
	}

}