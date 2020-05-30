package com.jwssw.rbac.vo;

import com.jwssw.core.base.vo.MyValueObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 系统菜单VO对象
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 13:13
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class MenuVO extends MyValueObject {
    private String name;
    private String parentId;
    private String parentName;
    private String url;
    private String perms;
    private String type;
    private String icon;
    private BigDecimal orderNum;
    private BigDecimal level;
    private List<MenuVO> Children;
}
