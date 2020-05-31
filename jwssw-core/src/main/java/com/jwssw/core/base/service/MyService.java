package com.jwssw.core.base.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * 所有Service的父类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/25 18:46
 * @since JDK 11
 */
public interface MyService<T> extends IService<T> {

    /**
     * 删除实体类对象
     *
     * @param entity 实体类
     * @return 是否删除
     */
    boolean deleteEntity(T entity);

    /**
     * 批量删除实体对象
     *
     * @param entities 实体类集合
     * @return 是否删除
     */
    @Transactional(rollbackFor = Exception.class)
    default boolean deleteEntityByIds(Collection<T> entities) {
        // 循环调用单个实体类删除方法
        entities.stream().forEach(entity -> {
            deleteEntity(entity);
        });

        // 返回true
        return true;
    }
}
