package com.jwssw.core.base.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jwssw.core.base.mapper.MyMapper;
import com.jwssw.core.base.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 所有service实现类的父类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/25 18:48
 * @since JDK 11
 */
public class MyServiceImpl<M extends MyMapper<T>, T> extends ServiceImpl<M, T> implements MyService<T> {

    /**
     * 基础映射对象
     */
    @Autowired
    protected M baseMapper;

    /**
     * 删除实体类
     *
     * @param entity 实体类对象
     * @return 删除结果
     */
    @Override
    public boolean deleteEntity(T entity) {
        return baseMapper.deleteEntity(entity) > 0;
    }

}
