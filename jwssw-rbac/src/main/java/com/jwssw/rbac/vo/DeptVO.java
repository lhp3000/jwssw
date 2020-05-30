package com.jwssw.rbac.vo;

import com.jwssw.core.base.vo.MyValueObject;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.util.List;

/**
 * 机构管理 VO对象
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-17 15:46:14
 * @since JDK 11
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Accessors(chain = true)
public class DeptVO extends MyValueObject {
    /**
     * 表业务字段
     */
    private String name;
    private String parentId;
    private BigDecimal orderNum;
    private String remark;
    private String parentName;
    private List<DeptVO> children;
}
