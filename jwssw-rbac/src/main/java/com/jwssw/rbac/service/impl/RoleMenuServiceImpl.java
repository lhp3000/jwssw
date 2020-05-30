package com.jwssw.rbac.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwssw.core.base.service.impl.MyServiceImpl;
import com.jwssw.core.http.PageRequest;
import com.jwssw.rbac.entity.RoleMenu;
import com.jwssw.rbac.mapper.RoleMenuMapper;
import com.jwssw.rbac.service.RoleMenuService;
import com.jwssw.rbac.vo.RoleMenuVO;
import org.springframework.stereotype.Service;

/**
 * 角色菜单 服务接口实现
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 14:26:12
 * @since JDK 11
 */
@Service
public class RoleMenuServiceImpl extends MyServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    /**
     * 分页查询
     *
     * @param req 分页请求对象
     * @return 查询结果
     */
    @Override
    public IPage<RoleMenu> selectPage(PageRequest req) {
        IPage<RoleMenu> page = new Page<>(req.getCurrent(), req.getSize());
        QueryWrapper<RoleMenu> qw = new QueryWrapper<>();
        // RoleMenu entity = req.toBean(RoleMenu.class);
        // if (StrUtil.isNotEmpty(entity.getName())) {
        //     qw.like(RoleMenu.NAME, entity.getName());
        // }
        // qw.orderByAsc(RoleMenu.NAME);
        return baseMapper.selectPage(page, qw);
    }

    /**
     * 保存RoleMenu信息
     *
     * @param vo RoleMenu信息
     * @return 是否成功
     */
    @Override
    public boolean save(RoleMenuVO vo) {
        // 如果id为空表示新增
        if (StrUtil.isEmpty(vo.getId())) {
            return baseMapper.insert(vo.toPO(RoleMenu.class)) > 0;
        }

        // 使用id进行修改
        return baseMapper.updateById(vo.toPO(RoleMenu.class)) > 0;
    }
}
