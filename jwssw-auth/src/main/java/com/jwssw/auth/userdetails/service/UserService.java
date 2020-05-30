package com.jwssw.auth.userdetails.service;

import com.jwssw.auth.userdetails.entity.User;
import com.jwssw.auth.userdetails.vo.UserVO;
import com.jwssw.core.base.service.MyService;

/**
 * 系统用户服务接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/26 13:18
 * @since JDK 11
 */
public interface UserService extends MyService<User> {

    /**
     * 根据用户名查询用户信息
     *
     * @param name 用户名
     * @return 用户信息（包括：用户权限等）
     */
    UserVO loadUserByUsername(String name);
}
