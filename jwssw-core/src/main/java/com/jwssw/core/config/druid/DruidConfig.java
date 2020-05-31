package com.jwssw.core.config.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * 阿里Druid的配置类
 *
 * @author Rewlco
 * @version 1.0
 * @date 2020/1/25 13:30
 * @since JDK 11
 */
@Configuration
@EnableConfigurationProperties({DruidProperties.class})
public class DruidConfig {
    /**
     * 加载druid属性
     */
    @Autowired
    DruidProperties prop;

    /**
     * 初始化数据源
     *
     * @return DataSource数据源
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSource druidDataSource() {
        // 使用druid自带方法初始化数据源
        DruidDataSource dataSource = DruidDataSourceBuilder.create().build();
        /**
         * 给数据源属性赋值
         */
        dataSource.setDriverClassName(prop.getDriverClassName());
        dataSource.setUrl(prop.getUrl());
        dataSource.setUsername(prop.getUsername());
        dataSource.setPassword(prop.getPassword());
        dataSource.setInitialSize(prop.getInitialSize());
        dataSource.setMinIdle(prop.getMinIdle());
        dataSource.setMaxActive(prop.getMaxActive());
        dataSource.setMaxWait(prop.getMaxWait());
        dataSource.setTimeBetweenEvictionRunsMillis(prop.getTimeBetweenEvictionRunsMillis());
        dataSource.setMinEvictableIdleTimeMillis(prop.getMinEvictableIdleTimeMillis());
        dataSource.setValidationQuery(prop.getValidationQuery());
        dataSource.setTestWhileIdle(prop.isTestWhileIdle());
        dataSource.setTestOnBorrow(prop.isTestOnBorrow());
        dataSource.setTestOnReturn(prop.isTestOnReturn());
        dataSource.setPoolPreparedStatements(prop.isPoolPreparedStatements());
        dataSource.setMaxPoolPreparedStatementPerConnectionSize(prop.getMaxPollPreparedStatementPerConnectionSize());
        try {
            dataSource.setFilters(prop.getFilters());
        } catch (SQLException e) {
        }

        // 返回数据源
        return dataSource;
    }


    /**
     * 配置事物管理器
     *
     * @return DataSourceTransactionManager
     */
    @Bean(name = "transactionManager")
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(druidDataSource());
    }

    /**
     * druid的servlet配置类
     *
     * @return ServletRegistrationBean
     */
    @Bean
    @ConditionalOnMissingBean
    public ServletRegistrationBean<Servlet> druidServlet() {
        ServletRegistrationBean<Servlet> bean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
        // IP白名单，IP地址多个之间采用“,”分割
        bean.addInitParameter("allow", "127.0.0.1");
        // IP黑名单，IP地址多个之间采用“,”分割
        bean.addInitParameter("deny", "");
        // 登录查看账号密码，用于登录druid监控后台
        bean.addInitParameter("loginUsername", "admin");
        bean.addInitParameter("loginPassword", "admin");

        // 是否能够重置数据
        bean.addInitParameter("resetEnable", "true");

        // 返回
        return bean;
    }

    /**
     * 配置服务过滤器
     *
     * @return 返回过滤器配置对象
     */
    @Bean
    @ConditionalOnMissingBean
    public FilterRegistrationBean statFilter() {
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        // 添加过滤规则
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤格式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*,");
        return filterRegistrationBean;
    }
}
