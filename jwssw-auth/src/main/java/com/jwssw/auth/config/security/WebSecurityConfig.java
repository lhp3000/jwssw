package com.jwssw.auth.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * spring security 安全配置类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/16 14:07
 * @since JDK 11
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 创建一个密码加密Bean
     *
     * @return 密码加密对象
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * 处理身份验证请求
     *
     * @return 身份验证请求管理类
     * @throws Exception
     */
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        // 直接使用父类的AuthenticationManager类
        return super.authenticationManagerBean();
    }

    /**
     * 为特定的http请求配置基于Web的安全性
     * <pre>
     *  覆盖父类中的http请求的安全配置，如果不覆盖默认默认对所有的请求进行安全拦截并校验
     * </pre>
     *
     * @param http Http安全对象
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 忽略"/oauth/**"路径下的请求，其他请求均需要验证
        http.authorizeRequests().antMatchers("/oauth/**").permitAll()
                .antMatchers("/oauth/login/**").permitAll()
                .and().requestMatchers().anyRequest();
    }

}
