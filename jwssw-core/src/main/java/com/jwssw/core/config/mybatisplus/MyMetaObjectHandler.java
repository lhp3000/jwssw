package com.jwssw.core.config.mybatisplus;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.jwssw.core.base.entity.MyModel;
import com.jwssw.core.util.Utils.SecurityUtil;
import com.jwssw.core.util.Utils.UuidUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 * insert或者update时公共字段自动填充
 * <pre>
 *     1.公共字段只有为空时才赋值
 * </pre>
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/25 18:38
 * @since JDK 11
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 公共字段 ID
     */
    private static final String ID = "id";
    /**
     * 公共字段 CREATE_BY
     */
    private static final String CREATE_BY = "createBy";
    /**
     * 公共字段 CREATE_TIME
     */
    private static final String CREATE_TIME = "createTime";
    /**
     * 公共字段 LAST_UPDATE_BY
     */
    private static final String LAST_UPDATE_BY = "lastUpdateBy";
    /**
     * 公共字段 LAST_UPDATE_TIME
     */
    private static final String LAST_UPDATE_TIME = "lastUpdateTime";
    /**
     * 公共字段 DEL_FLAG
     */
    private static final String DEL_FLAG = "delFlag";

    /**
     * 数据插入时自动填充
     *
     * @param metaObject 元素对象
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        // 表的逻辑主键
        if (ObjectUtil.isEmpty(getFieldValByName(ID, metaObject))) {
            // todo 可以考虑使用redis或者mysql的id工厂表来填充id值
            this.setFieldValByName(ID, UuidUtil.getIdStr(), metaObject);
        }

        // 判断当前用户是否为空
        if (ObjectUtil.isNotEmpty(SecurityUtil.getUserName())) {
            // 创建人和修改人
            String userName = SecurityUtil.getUserName();
            // 创建人字段只有为空时才赋值
            if (ObjectUtil.isEmpty(getFieldValByName(CREATE_BY, metaObject))) {
                this.setFieldValByName(CREATE_BY, userName, metaObject);
            }
            // 修改人字段只有为空时才赋值
            if (ObjectUtil.isEmpty(getFieldValByName(LAST_UPDATE_BY,
                    metaObject))) {
                this.setFieldValByName(LAST_UPDATE_BY, userName, metaObject);
            }
        }

        // 创建时间和修改时间
        String now = DateUtil.now();
        // 创建日期字段只有为空时才赋值
        if (ObjectUtil.isEmpty(getFieldValByName(CREATE_TIME, metaObject))) {
            this.setFieldValByName(CREATE_TIME, now, metaObject);
        }
        // 更新日期字段只有为空时才赋值
        if (ObjectUtil.isEmpty(getFieldValByName(LAST_UPDATE_TIME,
                metaObject))) {
            this.setFieldValByName(LAST_UPDATE_TIME, now, metaObject);
        }

        // 删除标志
        if (ObjectUtil.isEmpty(getFieldValByName(DEL_FLAG, metaObject))) {
            this.setFieldValByName(DEL_FLAG, MyModel.DEL_FLAG_0, metaObject);
        }
    }

    /**
     * 数据修改时自动填充
     *
     * @param metaObject 元素对象
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName(LAST_UPDATE_BY, SecurityUtil.getUserName(),
                metaObject);
        this.setFieldValByName(LAST_UPDATE_TIME, DateUtil.now(), metaObject);
    }

    public static void main(String[] args) {
        String dateStr = "2020-03-03";
        System.out.println(DateUtil.today());
        System.out.println(dateStr.compareTo(DateUtil.today()));
    }
}
