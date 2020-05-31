package com.jwssw.core.oauth2;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * @author <a href="mailTo:luhaopeng2005@126.com">Luhaopeng</a>
 * @version 1.0
 * @date 2020/2/4 15:55
 * @since JDK 1.8
 */
@EqualsAndHashCode(callSuper = true)
@Setter
@Getter
@Accessors(chain = true)
public class MyUserDetail extends User {
    /**
     * 部门ID
     */
    private String deptId;

    /**
     * 部门名称
     */
    private String deptName;

    /**
     * 构造函数
     *
     * @param username    用户名
     * @param password    密码
     * @param authorities 权限集合
     */
    public MyUserDetail(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
