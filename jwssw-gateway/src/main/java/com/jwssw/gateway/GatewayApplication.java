package com.jwssw.gateway;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关服务启动类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/21 20:23
 * @since JDK 11
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication
public class GatewayApplication {
    public static void main(String[] args) {
        log.info("网关服务（" + GatewayApplication.class.getName() + "）启动开始");
        SpringApplication.run(GatewayApplication.class, args);
        log.info("网关服务（" + GatewayApplication.class.getName() + "）启动结束");
    }
}
