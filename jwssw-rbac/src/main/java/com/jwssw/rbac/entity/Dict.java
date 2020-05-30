package com.jwssw.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwssw.core.base.entity.MyModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
/**
 * 字典表 实体
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 17:52:57
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("sys_dict")
public class Dict extends MyModel<Dict> {
    /**
     * 表字段静态值
     */
    public static final String VALUE = "value";
    public static final String LABEL = "label";
    public static final String TYPE = "type";
    public static final String DESCRIPTION = "description";
    public static final String SORT = "sort";
    public static final String REMARKS = "remarks";
    /**
     * 表业务字段
     */
    private String value;
    private String label;
    private String type;
    private String description;
    private BigDecimal sort;
    private String remarks;
}
