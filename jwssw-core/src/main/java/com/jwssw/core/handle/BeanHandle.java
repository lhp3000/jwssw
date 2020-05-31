package com.jwssw.core.handle;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwssw.core.base.entity.MyModel;
import com.jwssw.core.base.vo.MyValueObject;
import com.jwssw.core.util.Utils;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.stream.Collectors;

/**
 * java bean相关处理工具类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 14:47
 * @since JDK 11
 */
public class BeanHandle extends BeanUtil {

    /**
     * po集合bean转换成vo集合bean
     *
     * @param <T>    bean类型
     * @param list   集合源数据
     * @param tClass 转换成bean类型
     * @return 转换后的bean集合
     */
    public static <T extends MyValueObject> List<T> listPOToVO(@NonNull final List<? extends MyModel<?>> list, Class<T> tClass) {
        return list.stream().map(obj -> toBean(obj, tClass)).collect(Collectors.toList());
    }

    /**
     * vo集合bean转换成po集合bean
     *
     * @param <T>    bean类型
     * @param list   集合源数据
     * @param tClass 转换成bean类型
     * @return 转换后的bean集合
     */
    public static <T extends MyModel<?>> List<T> listVOToPO(@NonNull final List<? extends MyValueObject> list, Class<T> tClass) {
        return list.stream().map(obj -> toBean(obj, tClass)).collect(Collectors.toList());
    }

    /**
     * 实体分页转换成VO对象分页
     *
     * @param <T>    vo 类型
     * @param page   分页元数据
     * @param tClass 转换成VO类型
     * @return 转换后的分页对象
     */
    public static <T extends MyValueObject> IPage<T> pagePOToVO(IPage<? extends MyModel<?>> page, Class<T> tClass) {
        //
        IPage<T> retPage = new Page<>();
//        retPage.setTotal(page.getTotal());
//        retPage.setCurrent(page.getCurrent());
//        retPage.setSize(page.getSize());
//        retPage.setPages(page.getPages());
        Utils.BeanUtil.copyProperties(page, retPage, "records", "optimizeCountSql", "isSearchCount", "hitCount");
        // 实体类list转换成vo对象的list
        retPage.setRecords(listPOToVO(page.getRecords(), tClass));
        return retPage;
    }
}
