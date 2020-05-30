package com.jwssw.rbac.mapper;

import com.jwssw.core.base.mapper.MyMapper;
import com.jwssw.rbac.entity.Dept;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 机构管理 Mapper接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-17 15:46:14
 * @since JDK 11
 */
public interface DeptMapper extends MyMapper<Dept> {
    /**
     * 查询部门所有信息
     *
     * @return 部门信息列表
     */
    @Select({"select id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, ",
            " del_flag, remark from sys_dept where del_flag = '0'"})
    List<Dept> findAll();

    /**
     * 根据父ID查询子机构ID
     *
     * @param fatherId 父ID
     * @return 子机构ID集合
     */
    @Select({"select id, name, parent_id, order_num, create_by, create_time, last_update_by, last_update_time, ",
            "del_flag, remark from sys_dept where del_flag = '0' and parent_id = #{fatherId}"})
    List<Dept> selectChildOrg(String fatherId);

}