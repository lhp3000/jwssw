package com.jwssw.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwssw.core.base.service.MyService;
import com.jwssw.core.http.PageRequest;
import com.jwssw.rbac.entity.Dept;
import com.jwssw.rbac.vo.DeptVO;

import java.util.List;

/**
 * 机构管理 服务接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-17 15:46:14
 * @since JDK 11
 */
public interface DeptService extends MyService<Dept> {
    /**
     * 查询机构树
     *
     * @return
     */
    List<DeptVO> findTree();

    /**
     * 批量删除部门信息
     *
     * @param deptList 部门列表
     * @return 结果
     */
    boolean delete(List<DeptVO> deptList);

    /**
     * 保存数据源信息
     *
     * @param vo Dept信息
     * @return 是否成功
     */
    public boolean save(DeptVO vo);
}