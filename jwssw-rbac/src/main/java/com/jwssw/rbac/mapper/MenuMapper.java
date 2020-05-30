package com.jwssw.rbac.mapper;

import com.jwssw.core.base.mapper.MyMapper;
import com.jwssw.rbac.entity.Menu;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 系统菜单Mapper映射接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 13:08
 * @since JDK 11
 */
public interface MenuMapper extends MyMapper<Menu> {

    /**
     * 根据角色Id集合获取对应的菜单集合
     *
     * @param roleIds 角色Id集合
     * @return 菜单集合
     */
    @Select({"<script>",
            "select a.id,a.name,a.parent_id,a.url,a.perms,a.type,a.icon,a.order_num,a.create_by,a.create_time,",
            "a.last_update_by,a.last_update_time,a.del_flag from sys_menu a inner join sys_role_menu b",
            " on b.menu_id = a.id and b.del_flag=a.del_flag where a.del_flag='0' and b.role_id in ",
            "<foreach collection='ids' item='item' index='index' open='(' separator=',' close=')'>",
            "#{item}",
            "</foreach>",
            "</script>"})
    List<Menu> listByRoleIds(@Param("ids") List<String> roleIds);
}
