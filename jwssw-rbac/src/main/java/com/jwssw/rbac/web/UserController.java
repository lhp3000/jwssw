package com.jwssw.rbac.web;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwssw.core.base.web.MyController;
import com.jwssw.core.constant.MyConst;
import com.jwssw.core.http.HttpResult;
import com.jwssw.core.http.PageRequest;
import com.jwssw.core.util.Utils;
import com.jwssw.rbac.entity.User;
import com.jwssw.rbac.service.UserService;
import com.jwssw.rbac.vo.DeptVO;
import com.jwssw.rbac.vo.RoleVO;
import com.jwssw.rbac.vo.UserVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkState;

/**
 * 系统用户控制类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/27 19:40
 * @since JDK 11
 */
@Log4j2
@RestController
@RequestMapping("user")
public class UserController extends MyController<UserService> {

    /**
     * 分页查询
     *
     * @param req 分页请求对象
     * @return 查询结果
     */
    @PreAuthorize("hasAuthority('sys:user:view')")
    @PostMapping("selectPage")
    public HttpResult<Page<UserVO>> selectPage(@RequestBody PageRequest req) {
        return HttpResult.success(service.selectPage(req));
    }

    /**
     * 获取系统所有角色信息
     *
     * @return 角色集合
     */
    @PreAuthorize("hasAuthority('sys:user:view')")
    @PostMapping("listRole")
    public HttpResult<List<RoleVO>> listRole() {
        return HttpResult.success(service.listRole());
    }

    /**
     * 获取系统所有部门角色信息
     *
     * @return 部门集合
     */
    @PreAuthorize("hasAuthority('sys:user:view')")
    @PostMapping("listDept")
    public HttpResult<List<DeptVO>> listDept() {
        return HttpResult.success(service.listDept());
    }

    /**
     * 保存用户信息
     *
     * @param vo 用户信息
     * @return 保存结果
     */
    @PreAuthorize("hasAuthority('sys:user:add') AND hasAuthority('sys:user:edit')")
    @PostMapping("save")
    public HttpResult<Boolean> save(@RequestBody UserVO vo) {
        // 判断用户信息是否有效
        checkNotNull(vo, "无效的用户信息");

        if (StrUtil.isEmpty(vo.getId())) {
            // 新增用户
            QueryWrapper<User> qw = new QueryWrapper<>();
            qw.eq(User.NAME, vo.getName());
            checkState(ObjectUtil.isEmpty(service.getOne(qw)), "用户名已存在!");
        } else {
            // 根据用户ID查询用户信息
            User user = service.getById(vo.getId());
            // 如果用户信息不为空，判断修改的用户是否为管理员用户，如果是不允许修改
            checkArgument(!MyConst.ADMIN.equalsIgnoreCase(user.getName()), "超级管理员不允许修改!");
        }

        // 设置默认密码和盐值
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode(MyConst.DEFAULT_PASSWORD);
        if (StrUtil.isNotEmpty(vo.getPassword())) {
            password = passwordEncoder.encode(vo.getPassword());
        }
        vo.setPassword(password);
        vo.setSalt(RandomUtil.randomString(MyConst.BASE_STR, 21));

        // 保存用户信息
        return HttpResult.success(service.save(vo));
    }

    /**
     * 批量删除用户信息
     *
     * @param listVO 批量用户信息
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:delete')")
    @PostMapping("delete")
    public HttpResult<Boolean> delete(@RequestBody List<UserVO> listVO) {
        checkNotNull(listVO, "无效的用户信息");
        // 判断删除的用户中是否包含管理员，如果包含就不允许删除
        Optional<UserVO> optional = listVO.stream().filter(user -> MyConst.ADMIN.equals(user.getId())).findFirst();
        checkArgument(!optional.isPresent(), "超级管理员不允许删除!");
        return HttpResult.success(service.deleteEntityByIds(Utils.BeanUtil.listVOToPO(listVO, User.class)));
    }

    /**
     * 初始化用户的密码
     *
     * @param id 用户ID
     * @return
     */
    @PreAuthorize("hasAuthority('sys:user:initpwd')")
    @PostMapping("resetPassword")
    public HttpResult<Boolean> resetPassword(@RequestParam String id) {
        checkNotNull(id, "无效的用户信息");
        return HttpResult.success(service.resetPassword(id));
    }

    /**
     * 锁定或解锁用户
     *
     * @param vo 用户信息
     * @return 锁定或解锁结果
     */
    @PreAuthorize("hasAuthority('sys:user:unlock') AND hasAuthority('sys:user:lock')")
    @PostMapping(value = "changeStatus")
    public HttpResult changeStatus(@RequestBody UserVO vo) {
        checkNotNull(vo, "无效的用户信息");
        return HttpResult.success(service.changeStatus(vo));
    }
}
