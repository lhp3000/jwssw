package com.jwssw.rbac.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwssw.core.base.web.MyController;
import com.jwssw.core.http.HttpResult;
import com.jwssw.core.http.PageRequest;
import com.jwssw.core.util.Utils;
import com.jwssw.core.util.Utils.BeanUtil;
import com.jwssw.rbac.entity.RoleMenu;
import com.jwssw.rbac.service.RoleMenuService;
import com.jwssw.rbac.vo.RoleMenuVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 角色菜单 控制
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 14:26:12
 * @since JDK 11
 */
@RestController
@RequestMapping("roleMenu")
public class RoleMenuController extends MyController<RoleMenuService> {
    /**
     * 分页查询
     *
     * @param req 分页请求对象
     * @return 查询结果I
     */
    @PreAuthorize("hasAuthority('sys:roleMenu:view')")
    @PostMapping("selectPage")
    public HttpResult<IPage<RoleMenu>> selectPage(@RequestBody PageRequest req) {
        return HttpResult.success(service.selectPage(req));
    }

    /**
     * 保存角色菜单信息
     *
     * @param vo 数据源信息
     * @return 保存结果
     */
    @PreAuthorize("hasAuthority('sys:roleMenu:add') AND hasAuthority('sys:roleMenu:edit')")
    @PostMapping("save")
    public HttpResult<Boolean> save(@RequestBody RoleMenuVO vo) {
        checkNotNull(vo, "无效的角色菜单");
        return HttpResult.success(service.save(vo));
    }

    /**
     * 批量删除角色菜单信息
     *
     * @param listVO 批量用户信息
     * @return
     */
    @PreAuthorize("hasAuthority('sys:roleMenu:delete')")
    @PostMapping("delete")
    public HttpResult<Boolean> delete(@RequestBody List<RoleMenuVO> listVO) {
        checkNotNull(listVO, "无效的角色菜单");
        return HttpResult.success(service.deleteEntityByIds(BeanUtil.listVOToPO(listVO, RoleMenu.class)));
    }
}
