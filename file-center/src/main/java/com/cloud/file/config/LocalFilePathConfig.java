package com.cloud.file.config;


import org.springframework.beans.factory.annotation.Value;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

/**
 * 使系统加载jar包外的文件
 *
 * @author hlxd
 *
 */
@Configuration
public class LocalFilePathConfig implements WebMvcConfigurer {

	/**
	 * 上传文件存储在本地的根路径
	 */
	@Value("${file.local.path}")
	private String localFilePath;

	/**
	 * url前缀
	 */
	@Value("${file.local.prefix}")
	public String localFilePrefix;

	@Bean
	public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
		return new WebMvcConfigurerAdapter() {

			 // 外部文件访问<br>

			@Override
			public void addResourceHandlers(ResourceHandlerRegistry registry) {
			//注册资源处理器
				registry.addResourceHandler(localFilePrefix + "/**")
						.addResourceLocations(ResourceUtils.FILE_URL_PREFIX + localFilePath + File.separator);
			}

		};
	}
}
