package com.cloud.gateway.filter;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;

/**
 * @Title: cloud-service
 * @Description :
 * @Authod: smokong
 * @Date: 2019/4/30 15:57
 * @Version: 1.0
 */
@Configuration
@Aspect
public class ControllerAopConfig {

	@Around("execution(* com.cloud.gateway.controller..*.*(..))")
	public Object testAop(ProceedingJoinPoint pro) throws Throwable {
		//获取response
		HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
		//核心设置
		response.setHeader("Access-Control-Allow-Origin", "*");
		//response.setHeader("Access-Control-Allow-Headers", "*");

		//执行调用的方法
		Object proceed = pro.proceed();
		return proceed;
	}

}
