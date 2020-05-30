package com.jwssw.rbac.vo;

import com.jwssw.core.base.vo.MyValueObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
/**
 * 字典表 VO对象
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 17:52:57
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class DictVO extends MyValueObject {
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
