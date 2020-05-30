package com.jwssw.auth.userdetails.mapper;

import com.jwssw.auth.userdetails.entity.Menu;
import com.jwssw.core.base.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统菜单mapper映射接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/26 13:59
 * @since JDK 11
 */
public interface MenuMapper extends MyMapper<Menu> {
    /**
     * 根据角色ID集合查询对应的菜单权限集合
     * <pre>
     *     todo 目前只查询按钮权限（a.type = '2'）
     * </pre>
     *
     * @param roleIds 角色集合
     * @return 菜单权限集合
     */
    @Select({"<script>",
            "select a.perms from sys_menu a, sys_role_menu b where a.id = b.menu_id and a.type = '2'",
            " and b.role_id in ",
            "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    List<String> listPermsByRoleIds(@Param("ids") List<String> roleIds);
}
