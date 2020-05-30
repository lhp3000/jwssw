package com.jwssw.rbac.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Preconditions;
import com.jwssw.core.base.web.MyController;
import com.jwssw.core.constant.MyConst;
import com.jwssw.core.http.HttpResult;
import com.jwssw.core.http.PageRequest;
import com.jwssw.core.util.Utils;
import com.jwssw.rbac.entity.Role;
import com.jwssw.rbac.entity.RoleMenu;
import com.jwssw.rbac.service.RoleService;
import com.jwssw.rbac.vo.RoleMenuVO;
import com.jwssw.rbac.vo.RoleVO;
import com.jwssw.rbac.vo.UserVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 系统角色控制类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/2/2 12:28
 * @since JDK 11
 */
@RestController
@RequestMapping("role")
public class RoleController extends MyController<RoleService> {
    /**
     * 分页查询
     *
     * @param req 分页请求对象
     * @return 查询结果I
     */
    @PreAuthorize("hasAuthority('sys:role:view')")
    @PostMapping("selectPage")
    public HttpResult<IPage<Role>> selectPage(@RequestBody PageRequest req) {
        return HttpResult.success(service.selectPage(req));
    }

    /**
     * 保存角色管理信息
     *
     * @param vo 数据源信息
     * @return 保存结果
     */
    @PreAuthorize("hasAuthority('sys:role:add') AND hasAuthority('sys:role:edit')")
    @PostMapping("save")
    public HttpResult<Boolean> save(@RequestBody RoleVO vo) {
        checkNotNull(vo, "无效的角色管理");
        return HttpResult.success(service.save(vo));
    }

    /**
     * 批量删除角色管理信息
     *
     * @param listVO 批量用户信息
     * @return
     */
    @PreAuthorize("hasAuthority('sys:role:delete')")
    @PostMapping("delete")
    public HttpResult<Boolean> delete(@RequestBody List<RoleVO> listVO) {
        checkNotNull(listVO, "无效的角色管理");
        return HttpResult.success(service.deleteEntityByIds(Utils.BeanUtil.listVOToPO(listVO, Role.class)));
    }

    /**
     * 根据角色ID查询角色菜单信息
     *
     * @return com.jwssw.core.http.HttpResult
     * @author luhaopeng
     * @date 2019/11/17 12:00
     * @parme [roleId 角色ID]
     **/
    @PreAuthorize("hasAuthority('sys:role:view')")
    @GetMapping("findRoleMenus")
    public HttpResult findRoleMenus(@RequestParam String roleId) {
        return HttpResult.success(service.findRoleMenus(roleId));
    }

    /**
     * 保存角色菜单信息
     *
     * @return com.jwssw.core.http.HttpResult
     * @author luhaopeng
     * @date 2019/11/17 12:01
     * @parme [records 角色菜单信息]
     **/
    @PreAuthorize("hasAuthority('sys:role:add') AND hasAuthority('sys:role:edit')")
    @PostMapping(value = "/saveRoleMenus")
    public HttpResult saveRoleMenus(@RequestBody List<RoleMenuVO> records) {
        checkNotNull(records, "角色菜单无效");
        // 判断是否为admin管理角色，如果是提示不允许修改
        for (RoleMenuVO record : records) {
            Role sysRole = service.getById(record.getRoleId());
            if (MyConst.ADMIN.equalsIgnoreCase(sysRole.getName())) {
                // 如果是超级管理员，不允许修改
                return HttpResult.error("超级管理员拥有所有菜单权限，不允许修改！");
            }
        }
        return HttpResult.success(service.saveRoleMenus(records));
    }
}
