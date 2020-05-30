package com.jwssw.rbac.vo;

import lombok.Data;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 前台动态路由所需要的属性VO对象
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/2/1 13:22
 * @since JDK 11
 */
@Data
@Accessors(chain = true)
public class RouterVO {
    private String id;
    private String name;
    private String url;
    private String icon;
    private BigDecimal orderNum;
    private List<RouterVO> Children;
}
