package com.cloud.log.autoconfigure;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.utils.AppUserUtil;
import com.cloud.model.log.Log;
import com.cloud.model.log.LogAnnotation;
import com.cloud.model.log.constants.LogQueue;
import com.cloud.model.user.LoginAppUser;

/**
 * aop实现日志
 *
 * @author hlxd
 */
@Aspect
public class LogAop {

    private static final Logger logger = LoggerFactory.getLogger(LogAop.class);

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Around(value = "@annotation(com.cloud.model.log.LogAnnotation)")
    public Object logSave(ProceedingJoinPoint joinPoint) throws Throwable {
        Log log = new Log();
        log.setCreateTime(new Date());
        LoginAppUser loginAppUser = AppUserUtil.getLoginAppUser();
        if (loginAppUser != null) {
            log.setUsername(loginAppUser.getUsername());
        }

        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        LogAnnotation logAnnotation = methodSignature.getMethod().getDeclaredAnnotation(LogAnnotation.class);
        log.setModule(logAnnotation.module());

        if (logAnnotation.recordParam()) {
            String[] paramNames = methodSignature.getParameterNames();// 参数名
            if (paramNames != null && paramNames.length > 0) {
                Object[] args = joinPoint.getArgs();// 参数值

                Map<String, Object> params = new HashMap<>();
                for (int i = 0; i < paramNames.length; i++) {
                    params.put(paramNames[i], args[i]);
                }

                try {
                    log.setParams(JSONObject.toJSONString(params));
                } catch (Exception e) {
                    logger.error("记录参数失败：{}", e.getMessage());
                }
            }
        }

        try {
            Object object = joinPoint.proceed();// 原方法
            log.setFlag(Boolean.TRUE);

            return object;
        } catch (Exception e) {
            log.setFlag(Boolean.FALSE);
            log.setRemark(e.getMessage());
            throw e;
        } finally {
            // 异步将Log对象发送到队列
            CompletableFuture.runAsync(() -> {
                try {
                    amqpTemplate.convertAndSend(LogQueue.LOG_QUEUE, log);
                    logger.info("发送日志到队列：{}", log);
                } catch (Exception e2) {
                    e2.printStackTrace();
                }
            });

        }

    }
}
