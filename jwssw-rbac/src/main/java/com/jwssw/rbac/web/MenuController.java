package com.jwssw.rbac.web;

import cn.hutool.core.util.StrUtil;
import com.jwssw.core.base.web.MyController;
import com.jwssw.core.constant.MyConst;
import com.jwssw.core.http.HttpResult;
import com.jwssw.core.util.Utils;
import com.jwssw.rbac.entity.Menu;
import com.jwssw.rbac.service.MenuService;
import com.jwssw.rbac.vo.MenuVO;
import com.jwssw.rbac.vo.RouterVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 系统菜单控制类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/31 13:00
 * @since JDK 11
 */
@Log4j2
@RestController
@RequestMapping("menu")
public class MenuController extends MyController<MenuService> {
    /**
     * 查询菜单树
     *
     * @return 菜单集合
     */
    @PreAuthorize("hasAuthority('sys:menu:view')")
    @GetMapping("findMenuTree")
    public HttpResult<List<MenuVO>> findMenuTree() {
        return HttpResult.success(service.findMenuTree());
    }

    /**
     * 根据用户名
     *
     * @param userName
     * @return
     */
    @PreAuthorize("hasAuthority('sys:menu:view') OR hasAuthority('rbac:login:check')")
    @GetMapping("findNavTree")
    public HttpResult<List<RouterVO>> findNavTree(@RequestParam String userName) {
        checkArgument(StrUtil.isNotEmpty(userName), "无效的用户名，请重新操作");
        return HttpResult.success(service.findNavTree(userName, MyConst.ONE));
    }

    /**
     * 根据用户名称获取用户权限列表
     *
     * @param userName 用户名
     * @return 权限列表
     */
    @PreAuthorize("hasAuthority('rbac:login:check')")
    @GetMapping("findPermissions")
    public HttpResult<List<String>> findPermissions(@RequestParam String userName) {
        checkArgument(StrUtil.isNotEmpty(userName), "无效的用户名，请重新操作");
        return HttpResult.success(service.findPermissions(userName));
    }

    /**
     * 保存菜单信息
     *
     * @param vo 菜单信息
     * @return 请求返回
     */
    @PreAuthorize("hasAuthority('sys:menu:add') AND hasAuthority('sys:menu:edit')")
    @PostMapping("save")
    // @Operation(moduleName = "系统管理", featuresName = "菜单管理", value = "保存菜单信息")
    public HttpResult save(@RequestBody MenuVO vo) {
        checkNotNull(vo, "无效的权限菜单");
        return HttpResult.success(service.save(vo));
    }

    /**
     * 删除菜单信息
     *
     * @param listVO 菜单信息集合
     * @return 请求返回
     */
    @PreAuthorize("hasAuthority('sys:menu:delete')")
    @PostMapping("delete")
    // @Operation(moduleName = "系统管理", featuresName = "菜单管理", value = "保存菜单信息")
    public HttpResult delete(@RequestBody List<MenuVO> listVO) {
        checkNotNull(listVO, "无效的权限菜单");
        return HttpResult.success(service.deleteEntityByIds(Utils.BeanUtil.listVOToPO(listVO, Menu.class)));
    }
}
