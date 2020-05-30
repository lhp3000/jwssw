package com.jwssw.rbac.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.jwssw.core.base.entity.MyModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

/**
 * 系统菜单实体类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 13:00
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
@TableName("sys_menu")
public class Menu extends MyModel<Menu> {
    /**
     * 表字段静态值
     */
    public static final String NAME = "name";
    public static final String PARENT_ID = "parent_id";
    public static final String URL = "url";
    public static final String PERMS = "perms";
    public static final String TYPE = "type";
    public static final String ICON = "icon";
    public static final String ORDER_NUM = "order_num";

    /**
     * 表业务字段
     */
    private String name;
    private String parentId;
    private String url;
    private String perms;
    private String type;
    private String icon;
    private BigDecimal orderNum;
}
