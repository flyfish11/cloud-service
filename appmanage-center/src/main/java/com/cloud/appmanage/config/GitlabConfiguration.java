package com.cloud.appmanage.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * gitlab相关配置
 *
 * @author yulj
 * @create: 2019/05/15 09:18
 */
@Component
@ConfigurationProperties(prefix = GitlabConfiguration.PREFIX)
@Getter
@Setter
public class GitlabConfiguration {

    public static final String PREFIX = "gitlab";

    private String ip;

    private String domain;

    private String privateToken;

    private String apiPrefix;
}
