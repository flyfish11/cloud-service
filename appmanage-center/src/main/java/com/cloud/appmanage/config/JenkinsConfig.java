package com.cloud.appmanage.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Title: cloud-service
 * @Description :
 * @Authod: smokong
 * @Date: 2019/5/14 10:26
 * @Version: 1.0
 */
@Configuration
@Data
public class JenkinsConfig {

	@Value("${jenkins.jenUrl}")
	private String jenUrl;

	@Value("${jenkins.username}")
	private String username;

	@Value("${jenkins.passwordorToken}")
	private String passwordorToken;


}
