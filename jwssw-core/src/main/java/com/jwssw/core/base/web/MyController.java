package com.jwssw.core.base.web;

import com.jwssw.core.base.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 所有控制器的父类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/27 17:08
 * @since JDK 11
 */
public class MyController<S extends MyService> {

    /**
     * 基础映射对象
     */
    @Autowired
    protected S service;
}
