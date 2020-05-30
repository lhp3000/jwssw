package com.jwssw.rbac.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwssw.core.base.service.impl.MyServiceImpl;
import com.jwssw.core.http.PageRequest;
import com.jwssw.rbac.entity.Dict;
import com.jwssw.rbac.mapper.DictMapper;
import com.jwssw.rbac.service.DictService;
import com.jwssw.rbac.vo.DictVO;
import org.springframework.stereotype.Service;

/**
 * 字典表 服务接口实现
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 17:52:57
 * @since JDK 11
 */
@Service
public class DictServiceImpl extends MyServiceImpl<DictMapper, Dict> implements DictService {
    /**
     * 分页查询
     *
     * @param req 分页请求对象
     * @return 查询结果
     */
    @Override
    public IPage<Dict> selectPage(PageRequest req) {
        IPage<Dict> page = new Page<>(req.getCurrent(), req.getSize());
        QueryWrapper<Dict> qw = new QueryWrapper<>();
        Dict entity = req.toBean(Dict.class);
        // 名称条件
        if (ObjectUtil.isNotEmpty(entity.getLabel())) {
            qw.like(Dict.LABEL, entity.getLabel());
        }
        // 类型条件
        if (ObjectUtil.isNotEmpty(entity.getType())) {
            qw.like(Dict.TYPE, entity.getType());
        }
        // 描述条件
        if (ObjectUtil.isNotEmpty(entity.getDescription())) {
            qw.like(Dict.DESCRIPTION, entity.getDescription());
        }

        // 使用类型和值排序
        qw.orderByAsc(Dict.TYPE, Dict.SORT);
        return baseMapper.selectPage(page, qw);
    }

    /**
     * 保存Dict信息
     *
     * @param vo Dict信息
     * @return 是否成功
     */
    @Override
    public boolean save(DictVO vo) {
        // 如果id为空表示新增
        if (StrUtil.isEmpty(vo.getId())) {
            return baseMapper.insert(vo.toPO(Dict.class)) > 0;
        }

        // 使用id进行修改
        return baseMapper.updateById(vo.toPO(Dict.class)) > 0;
    }
}
