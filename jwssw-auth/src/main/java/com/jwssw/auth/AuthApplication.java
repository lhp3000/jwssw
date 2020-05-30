package com.jwssw.auth;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * OAuth2 鉴权认证服务
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/16 14:01
 * @since JDK 11
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.jwssw"})
public class AuthApplication {
    public static void main(String[] args) {
        log.info("OAuth2的鉴权认证服务（" + AuthApplication.class.getName() + "）启动开始");
        SpringApplication.run(AuthApplication.class, args);
        log.info("OAuth2的鉴权认证服务（" + AuthApplication.class.getName() + "）启动结束");
    }
}
