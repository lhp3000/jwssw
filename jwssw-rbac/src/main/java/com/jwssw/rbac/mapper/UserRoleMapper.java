package com.jwssw.rbac.mapper;

import com.jwssw.core.base.mapper.MyMapper;
import com.jwssw.rbac.entity.UserRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;

/**
 * 系统用户角色Mapper映射接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 14:08
 * @since JDK 11
 */
public interface UserRoleMapper extends MyMapper<UserRole> {
    /**
     * 根据用户ID删除用户角色信息
     *
     * @param userId 用户ID
     * @return 删除条数
     */
    @Delete("delete from sys_user_role where user_id = #{userId}")
    int deleteByUserId(@Param("userId") String userId);
}
