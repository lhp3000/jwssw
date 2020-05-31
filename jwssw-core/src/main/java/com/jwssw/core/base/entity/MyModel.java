package com.jwssw.core.base.entity;

import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.util.ReflectUtil;
import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.jwssw.core.util.Utils;
import com.jwssw.core.base.vo.MyValueObject;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 基础模型父类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/25 18:34
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class MyModel<E extends Model<?>> extends Model<E> {

    /**
     * 公共自动常量
     */
    public static final String ID = "id";
    public static final String CREATE_BY = "create_by";
    public static final String CREATE_TIME = "create_time";
    public static final String LAST_UPDATE_BY = "last_update_by";
    public static final String LAST_UPDATE_TIME = "last_update_time";
    public static final String DEL_FLAG = "del_flag";
    /**
     * 逻辑删除值_0
     */
    public static final String DEL_FLAG_0 = "0";
    /**
     * 逻辑未删除值_1
     */
    public static final String DEL_FLAG_1 = "1";

    /**
     * 逻辑主键
     */
    @TableField(fill = FieldFill.INSERT)
    private String id;

    /**
     * 创建人
     */
    @TableField(fill = FieldFill.INSERT)
    private String createBy;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private String createTime;

    /**
     * 最后修改人
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastUpdateBy;

    /**
     * 最后修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String lastUpdateTime;

    /**
     * 删除标志
     */
    @TableField(fill = FieldFill.INSERT, select = false)
    @TableLogic
    private String delFlag;

    /**
     * 将当前的PO转化成对应的VO对象
     *
     * @param <T>    VO类
     * @param tClass VO类
     * @return VO对象
     */
    public <T extends MyValueObject> T toVO(Class<T> tClass) {
        return toVO(tClass, true);
    }

    /**
     * 将当前的PO转化成对应的VO对象
     *
     * @param <T>             VO类
     * @param tClass          VO类
     * @param ignoreNullValue 是否忽略空直
     * @return VO对象
     */
    public <T extends MyValueObject> T toVO(Class<T> tClass, boolean ignoreNullValue) {
        return toVO(tClass, ignoreNullValue, new String[]{});
    }

    /**
     * 将当前的PO转化成对应的VO对象
     *
     * @param <T>              VO类型
     * @param tClass           VO类
     * @param ignoreProperties 忽略属性列表
     * @return VO对象
     */
    public <T extends MyValueObject> T toVO(Class<T> tClass, String... ignoreProperties) {
        return toVO(tClass, true, ignoreProperties);
    }

    /**
     * 将当前的PO转化成对应的VO对象
     *
     * @param <T>              VO类型
     * @param tClass           VO类
     * @param ignoreNullValue  是否忽略空直
     * @param ignoreProperties 忽略属性列表
     * @return VO对象
     */
    public <T extends MyValueObject> T toVO(Class<T> tClass, boolean ignoreNullValue, String... ignoreProperties) {
        T target = ReflectUtil.newInstance(tClass);
        Utils.BeanUtil.copyProperties(this, target, CopyOptions.create().setIgnoreNullValue(ignoreNullValue)
                .setIgnoreProperties(ignoreProperties));
        return target;
    }
}
