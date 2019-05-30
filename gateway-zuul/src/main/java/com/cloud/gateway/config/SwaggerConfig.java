package com.cloud.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.UiConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * swagger文档
 * 
 * @author hlxd
 *
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {

	@Bean
	public Docket docket() {
		return new Docket(DocumentationType.SWAGGER_2)
				.groupName("网关swagger接口文档")
				.apiInfo(new ApiInfoBuilder().title("网关swagger接口文档")
						.contact(new Contact("hlxd", "", "hlxd@163.com")).version("1.0").build())
				.select().paths(PathSelectors.any()).build();
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("分布式购物系统")
				.description("购物系统接口文档说明")
				.termsOfServiceUrl("http://localhost:8080")
				.contact(new Contact("vker", "", "6492178@gmail.com"))
				.version("1.0")
				.build();
	}

	@Bean
	UiConfiguration uiConfig() {
		return new UiConfiguration(null, "list", "alpha", "schema",
				UiConfiguration.Constants.DEFAULT_SUBMIT_METHODS, false, true, 60000L);
	}
}
