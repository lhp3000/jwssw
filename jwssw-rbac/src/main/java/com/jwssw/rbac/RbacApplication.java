package com.jwssw.rbac;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 基于角色权限控制的启动类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/16 14:35
 * @since JDK 11
 */
@Slf4j
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.jwssw"})
public class RbacApplication {

    public static void main(String[] args) {
        log.info("基于角色权限控制的服务（" + RbacApplication.class.getName() + "）启动开始");
        SpringApplication.run(RbacApplication.class, args);
        log.info("基于角色权限控制的服务（" + RbacApplication.class.getName() + "）启动结束");
    }
}
