package com.jwssw.core.config.druid;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * druid数据源属性类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/25 13:23
 * @since JDK 11
 */
@Data
@ConfigurationProperties(prefix = "spring.datasource.druid")
public class DruidProperties {
    private String driverClassName;
    private String url;
    private String username;
    private String password;
    /**
     * 初始化大小
     */
    private int initialSize = 5;
    /**
     * 最小连接
     */
    private int minIdle = 2;
    /**
     * 最大连接数
     */
    private int maxActive = 100;
    /**
     * 获取连接等待超时时间
     */
    private long maxWait = 60000;
    private long timeBetweenEvictionRunsMillis = 60000;
    private long minEvictableIdleTimeMillis = 300000;
    private String validationQuery = "select 'x'";
    private boolean testWhileIdle = true;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;
    private boolean poolPreparedStatements = true;
    private int maxPollPreparedStatementPerConnectionSize = 20;
    /**
     * 配置监控统计拦截的filters，去掉后监控界面SQL无法进行统计，wall用于防火墙
     */
    private String filters = "stat,wall,log4j,config";
}
