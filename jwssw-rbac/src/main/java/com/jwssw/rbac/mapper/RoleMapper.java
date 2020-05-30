package com.jwssw.rbac.mapper;

import com.jwssw.core.base.mapper.MyMapper;
import com.jwssw.rbac.entity.Role;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;
import java.util.Map;

/**
 * 系统角色Mapper接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/2/2 12:21
 * @since JDK 11
 */
public interface RoleMapper extends MyMapper<Role> {
    /**
     * 根据用户Id集合获取对应的权限
     *
     * @param userIds 用户Id集合
     * @return 角色集合
     */
    @Select({"<script>", "select a.id, a.remark, b.user_id from sys_role a inner join sys_user_role b",
            "  on a.id = b.role_id where a.del_flag = '0' and b.user_id in ",
            "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    List<Map<String, String>> listByUserIds(@Param("ids") List<String> userIds);
}
