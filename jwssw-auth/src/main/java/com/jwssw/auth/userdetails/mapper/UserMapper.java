package com.jwssw.auth.userdetails.mapper;

import com.jwssw.auth.userdetails.entity.User;
import com.jwssw.core.base.mapper.MyMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统用户Mapper映射接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/26 10:32
 * @since JDK 11
 */
public interface UserMapper extends MyMapper<User> {
    /**
     * 根据用户ID获取用户角色ID集合
     *
     * @param userId 用户ID
     * @return 用户角色ID集合
     */
    @Select("select role_id from sys_user_role where user_id=#{userId}")
    List<String> listRoleIdByUserId(String userId);
}
