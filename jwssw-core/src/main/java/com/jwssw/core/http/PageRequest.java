package com.jwssw.core.http;

import cn.hutool.core.bean.copier.CopyOptions;
import com.jwssw.core.util.Utils.BeanUtil;
import lombok.Data;
import lombok.experimental.Accessors;

import java.util.HashMap;
import java.util.Map;

/**
 * 分页请求对象
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/2/1 15:17
 * @since JDK 11
 */
@Data
@Accessors(chain = true)
public class PageRequest {
    /**
     * 当前页码
     */
    private int current = 1;
    /**
     * 每页数量
     */
    private int size = 10;

    /**
     * 查询参数
     */
    private Map<String, Object> columnFilters = new HashMap<>();

    /**
     * 前台参数转化成对应的java bean对象
     *
     * @param <T>    类型
     * @param tClass java bean类型
     * @return java bean 对象
     */
    public <T> T toBean(Class<T> tClass) {
        return BeanUtil.mapToBean(this.columnFilters, tClass, CopyOptions.create().setIgnoreNullValue(true));
    }
}
