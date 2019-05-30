package com.cloud.backend;

import com.spring4all.swagger.EnableSwagger2Doc;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 管理后台
 * @author hlxd
 */
@EnableSwagger2Doc
@EnableDiscoveryClient
@SpringBootApplication
public class ManageBackendApplication {

	public static void main(String[] args) {

		SpringApplication.run(ManageBackendApplication.class, args);

	}

}