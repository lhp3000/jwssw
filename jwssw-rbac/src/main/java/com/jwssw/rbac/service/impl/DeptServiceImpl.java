package com.jwssw.rbac.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.jwssw.core.base.entity.MyModel;
import com.jwssw.core.base.service.impl.MyServiceImpl;
import com.jwssw.core.base.vo.MyValueObject;
import com.jwssw.core.util.Utils;
import com.jwssw.rbac.entity.Dept;
import com.jwssw.rbac.mapper.DeptMapper;
import com.jwssw.rbac.service.DeptService;
import com.jwssw.rbac.vo.DeptVO;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 机构管理 服务接口实现
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-17 15:46:14
 * @since JDK 11
 */
@Service
public class DeptServiceImpl extends MyServiceImpl<DeptMapper, Dept> implements DeptService {

    /**
     * 查询部门数据信息
     *
     * @return 部门树
     */
    @Override
    public List<DeptVO> findTree() {
        // 查询部门所有信息
        List<Dept> deptList = baseMapper.findAll();
        // 顶级部门信息
        List<DeptVO> superDeptList = new ArrayList<>();
        for (Dept dept : deptList) {
            if (StrUtil.isEmpty(dept.getParentId()) || "-1".equals(dept.getParentId())) {
                superDeptList.add(dept.toVO(DeptVO.class));
            }
        }
        // 根据orderNum字段排序
        superDeptList.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
        // 组装部门信息
        findChildren(superDeptList, deptList);

        // 返回部门信息
        return superDeptList;
    }

    /**
     * 保存Dept信息
     *
     * @param vo Dept信息
     * @return 是否成功
     */
    @Override
    public boolean save(DeptVO vo) {
        // 如果id为空表示新增
        if (StrUtil.isEmpty(vo.getId())) {
            return baseMapper.insert(vo.toPO(Dept.class)) > 0;
        }

        // 使用id进行修改
        return baseMapper.updateById(vo.toPO(Dept.class)) > 0;
    }

    /**
     * 批量（递归）删除
     *
     * @param deptList 部门列表
     * @return 结果
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean delete(List<DeptVO> deptList) {
        // 查询子机构
        deptList.stream().forEach(dept -> {
            List<Dept> childOrg = baseMapper.selectChildOrg(dept.getId());
            if (CollectionUtil.isNotEmpty(childOrg)) {
                delete(Utils.BeanUtil.listPOToVO(childOrg, DeptVO.class));
            }
        });

        // 删除机构信息
        int number = baseMapper.deleteBatchIds(deptList.stream().map(MyValueObject::getId).collect(Collectors.toList()));
        return number > 0;
    }

    /**
     * 查询部门下一级节点信息
     *
     * @param superList 部门父节点集合
     * @param deptList  部门集合
     */
    private void findChildren(List<DeptVO> superList, List<Dept> deptList) {
        // 循环所有顶级部门信息
        for (DeptVO deptVO : superList) {
            // 组装子部门信息
            List<DeptVO> children = new ArrayList<>();
            for (Dept dept : deptList) {
                if (deptVO.getId().equals(dept.getParentId())) {
                    DeptVO vo = dept.toVO(DeptVO.class);
                    vo.setParentName(deptVO.getName());
                    children.add(vo);
                }
            }
            // 根据orderNum字段排序
            children.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));
            // 设置子部门信息并递归下一级
            deptVO.setChildren(children);
            findChildren(children, deptList);
        }
    }
}
