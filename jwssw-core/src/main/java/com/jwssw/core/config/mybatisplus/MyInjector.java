package com.jwssw.core.config.mybatisplus;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 * 自定义Sql注入器
 * <pre>
 *     目前只实现了删除语句的自定义注入
 * </pre>
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/25 18:29
 * @since JDK 11
 */
public class MyInjector extends DefaultSqlInjector {
    /**
     * 如果只需增加方法，保留MP自带方法
     * <pre>
     *     先super.getMethodList(mapperClass)，再add自定义的方法
     * </pre>
     *
     * @param mapperClass mapper接口
     * @return
     */
    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        // 集成原有的方法
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        // 添加自定义方法
        methodList.add(new MyDelete());
        // 返回方法列表
        return methodList;
    }
}
