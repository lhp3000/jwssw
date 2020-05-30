package com.jwssw.auth;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author <a href="mailTo:luhaopeng2005@126.com">Luhaopeng</a>
 * @version 1.0
 * @date 2020/1/25 14:03
 * @since JDK 1.8
 */
public class BCryptEncodedTest {

    public static void main(String[] args) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        System.out.println(passwordEncoder.encode("rbac-secret-8888"));
    }
}
