package com.jwssw.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwssw.core.base.entity.MyModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
/**
 * 机构管理 实体
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-17 15:46:14
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("sys_dept")
public class Dept extends MyModel<Dept> {
    /**
     * 表字段静态值
     */
    public static final String NAME = "name";
    public static final String PARENT_ID = "parent_id";
    public static final String ORDER_NUM = "order_num";
    public static final String REMARK = "remark";
    /**
     * 表业务字段
     */
    private String name;
    private String parentId;
    private BigDecimal orderNum;
    private String remark;
}
