package com.jwssw.core.base.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 所有mapper接口的父类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/25 18:44
 * @since JDK 11
 */
public interface MyMapper<T> extends BaseMapper<T> {

    /**
     * 删除单挑数据
     *
     * @param entity 实体类
     * @return 删除条数
     */
    int deleteEntity(T entity);
}
