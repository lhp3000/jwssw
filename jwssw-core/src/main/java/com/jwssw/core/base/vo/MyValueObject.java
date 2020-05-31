package com.jwssw.core.base.vo;

import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ReflectUtil;
import com.jwssw.core.base.entity.MyModel;
import com.jwssw.core.util.Utils.BeanUtil;
import lombok.Data;

import java.io.Serializable;

/**
 * 所有vo对象的父类
 *
 * @author rewloc
 * @version 1.0
 * @date 2020/1/27 17:06
 * @since JDK 11
 */
@Data
public class MyValueObject implements Serializable {
    /**
     * 逻辑主键
     */
    private String id;

    /**
     * 创建人
     */
    private String createBy;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 最后修改人
     */
    private String lastUpdateBy;

    /**
     * 最后修改时间
     */
    private String lastUpdateTime;

    /**
     * 删除标志
     */
    private String delFlag;


    /**
     * 将当前VO转化成对应的PO对象，同时忽略空直
     *
     * @param <T>    PO类型
     * @param tClass PO对象class
     * @return 对应的PO对象
     */
    public <T extends MyModel<?>> T toPO(Class<T> tClass) {
        return toPO(tClass, true);
    }

    /**
     * 将当前VO转化成对应的PO对象
     *
     * @param <T>             PO类型
     * @param tClass          PO对象class
     * @param ignoreNullValue 是否忽略空直
     * @return 对应的PO对象
     */
    public <T extends MyModel<?>> T toPO(Class<T> tClass, boolean ignoreNullValue) {
        return toPO(ReflectUtil.newInstance(tClass), ignoreNullValue);
    }

    /**
     * 使用当前的VO给PO对象进行属性赋值
     *
     * @param <T>    PO类型
     * @param target PO对象
     * @return PO对象
     */
    public <T extends MyModel<?>> T toPO(T target) {
        return toPO(target, true);
    }

    /**
     * 使用当前的VO给PO对象进行属性赋值
     *
     * @param <T>             PO类型
     * @param target          PO对象
     * @param ignoreNullValue 是否忽略空直
     * @return PO对象
     */
    public <T extends MyModel<?>> T toPO(T target, boolean ignoreNullValue) {
        BeanUtil.copyProperties(this, target, CopyOptions.create().setIgnoreNullValue(ignoreNullValue));
        return target;
    }
}
