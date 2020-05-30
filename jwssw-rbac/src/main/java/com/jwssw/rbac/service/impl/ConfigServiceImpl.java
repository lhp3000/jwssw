package com.jwssw.rbac.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwssw.core.base.service.impl.MyServiceImpl;
import com.jwssw.core.http.PageRequest;
import com.jwssw.rbac.entity.Config;
import com.jwssw.rbac.mapper.ConfigMapper;
import com.jwssw.rbac.service.ConfigService;
import com.jwssw.rbac.vo.ConfigVO;
import org.springframework.stereotype.Service;

/**
 * 系统配置表 服务接口实现
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 19:36:48
 * @since JDK 11
 */
@Service
public class ConfigServiceImpl extends MyServiceImpl<ConfigMapper, Config> implements ConfigService {
    /**
     * 分页查询
     *
     * @param req 分页请求对象
     * @return 查询结果
     */
    @Override
    public IPage<Config> selectPage(PageRequest req) {
        IPage<Config> page = new Page<>(req.getCurrent(), req.getSize());
        QueryWrapper<Config> qw = new QueryWrapper<>();
        Config entity = req.toBean(Config.class);
        if (ObjectUtil.isNotEmpty(entity.getLabel())) {
            qw.like(Config.LABEL, entity.getLabel());
        }
        // 类型条件
        if (ObjectUtil.isNotEmpty(entity.getType())) {
            qw.like(Config.TYPE, entity.getType());
        }
        // 描述条件
        if (ObjectUtil.isNotEmpty(entity.getDescription())) {
            qw.like(Config.DESCRIPTION, entity.getDescription());
        }
        return baseMapper.selectPage(page, qw);
    }

    /**
     * 保存Config信息
     *
     * @param vo Config信息
     * @return 是否成功
     */
    @Override
    public boolean save(ConfigVO vo) {
        // 如果id为空表示新增
        if (StrUtil.isEmpty(vo.getId())) {
            return baseMapper.insert(vo.toPO(Config.class)) > 0;
        }

        // 使用id进行修改
        return baseMapper.updateById(vo.toPO(Config.class)) > 0;
    }
}
