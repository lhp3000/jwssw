package com.jwssw.rbac.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwssw.core.base.web.MyController;
import com.jwssw.core.http.HttpResult;
import com.jwssw.core.http.PageRequest;
import com.jwssw.core.util.Utils;
import com.jwssw.core.util.Utils.BeanUtil;
import com.jwssw.rbac.entity.Dept;
import com.jwssw.rbac.service.DeptService;
import com.jwssw.rbac.vo.DeptVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 机构管理 控制
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-17 15:46:14
 * @since JDK 11
 */
@RestController
@RequestMapping("dept")
public class DeptController extends MyController<DeptService> {
    /**
     * 查询部门树
     *
     * @return 请求响应
     */
    @PreAuthorize("hasAuthority('sys:dept:view')")
    @GetMapping("findTree")
    public HttpResult findTree() {
        return HttpResult.success(service.findTree());
    }

    /**
     * 保存机构管理信息
     *
     * @param vo 数据源信息
     * @return 保存结果
     */
    @PreAuthorize("hasAuthority('sys:dept:add') AND hasAuthority('sys:dept:edit')")
    @PostMapping("save")
    public HttpResult<Boolean> save(@RequestBody DeptVO vo) {
        checkNotNull(vo, "无效的机构管理");
        return HttpResult.success(service.save(vo));
    }

    /**
     * 批量删除机构管理信息
     *
     * @param listVO 批量用户信息
     * @return
     */
    @PreAuthorize("hasAuthority('sys:dept:delete')")
    @PostMapping("delete")
    public HttpResult<Boolean> delete(@RequestBody List<DeptVO> listVO) {
        checkNotNull(listVO, "无效的机构管理");
        return HttpResult.success(service.delete(listVO));
    }
}
