package com.jwssw.rbac.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwssw.core.base.service.impl.MyServiceImpl;
import com.jwssw.core.constant.MyConst;
import com.jwssw.core.http.PageRequest;
import com.jwssw.core.util.Utils.BeanUtil;
import com.jwssw.rbac.entity.Dept;
import com.jwssw.rbac.entity.Role;
import com.jwssw.rbac.entity.User;
import com.jwssw.rbac.entity.UserRole;
import com.jwssw.rbac.mapper.UserMapper;
import com.jwssw.rbac.service.DeptService;
import com.jwssw.rbac.service.RoleService;
import com.jwssw.rbac.service.UserRoleService;
import com.jwssw.rbac.service.UserService;
import com.jwssw.rbac.vo.DeptVO;
import com.jwssw.rbac.vo.RoleVO;
import com.jwssw.rbac.vo.UserRoleVO;
import com.jwssw.rbac.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统用户的服务接口实现类
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/26 13:20
 * @since JDK 11
 */
@Service
public class UserServiceImpl extends MyServiceImpl<UserMapper, User> implements UserService {
    /**
     * 系统角色服务接口
     */
    @Autowired
    private RoleService roleService;
    /**
     * 系统部门服务接口
     */
    @Autowired
    private DeptService deptService;
    /**
     * 用户角色服务接口
     */
    @Autowired
    private UserRoleService userRoleService;

    /**
     * 分页查询
     *
     * @param req 分页请求对象
     * @return 查询结果
     */
    @Override
    public Page<UserVO> selectPage(PageRequest req) {
        // 定义并解析查询参数
        IPage<User> page = new Page<>(req.getCurrent(), req.getSize());
        User user = req.toBean(User.class);

        // 组装查询条件
        QueryWrapper<User> qw = new QueryWrapper<>();
        if (StrUtil.isNotEmpty(user.getName())) {
            qw.like(User.NAME, user.getName());
            qw.or();
            qw.like(User.NICK_NAME, user.getName());
        }
        // todo 默认只查询部门的员工信息，需要使用oauth的jwt和spring security
        qw.orderByAsc(User.NAME);
        baseMapper.selectPage(page, qw);
        // 查询并返回
        return selectPageAttach(page);
    }

    /**
     * 保存用户信息
     *
     * @param vo 用户信息
     * @return 是否成功
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean save(UserVO vo) {
        User user = vo.toPO(User.class);
        // 根据用户ID判断是新增用户还是修改用户
        if (StrUtil.isEmpty(user.getId())) {
            // 新增用户
            baseMapper.insert(user);
        } else {
            // 不对用户的密码做变更，只能通过密码初始化或者新增用户时对密码字段进行变更
            user.setPassword(null);
            // 更新用户信息
            baseMapper.updateById(user);
        }

        // 维护用户的角色信息，删除已有的角色，再新增新的角色
        userRoleService.deleteByUserId(user.getId());
        for (UserRoleVO userRoleVO : vo.getListUserRole()) {
            userRoleVO.setUserId(user.getId());
            userRoleService.save(userRoleVO.toPO(UserRole.class));
        }

        // 直接返回争取
        return true;
    }

    /**
     * 获取系统所有角色信息
     *
     * @return 角色集合
     */
    @Override
    public List<RoleVO> listRole() {
        return BeanUtil.listPOToVO(roleService.list(), RoleVO.class);
    }


    /**
     * 获取系统所有部门角色信息
     *
     * @return 部门集合
     */
    @Override
    public List<DeptVO> listDept() {
        List<Dept> listDept = deptService.list();
        List<DeptVO> listSupper = listDept.stream().filter(menu -> (StrUtil.isEmpty(menu.getParentId())
                || MyConst.ZERO_STR.equals(menu.getParentId()))).map(menu -> BeanUtil.toBean(menu, DeptVO.class))
                .collect(Collectors.toList());
        // 根据orderNum字段排序
        listSupper.sort((o1, o2) -> o1.getOrderNum().compareTo(o2.getOrderNum()));

        // 组装子部门
        findChildren(listSupper, listDept);

        // 返回部门信息集合
        return listSupper;
    }

    /**
     * 重置用户密码
     *
     * @param userId 用户Id
     * @return 是否成功
     */
    @Override
    public boolean resetPassword(String userId) {
        // 获取用户信息
        User user = getById(userId);
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String pwd = passwordEncoder.encode(MyConst.DEFAULT_PASSWORD);
        return baseMapper.resetPassword(userId, pwd);
    }

    /**
     * 解锁或者锁定用户
     *
     * @param vo 用户信息
     * @return 是否成功
     */
    @Override
    public boolean changeStatus(UserVO vo) {
        if (MyConst.ZERO_STR.equals(vo.getStatus())) {
            vo.setStatus(MyConst.ONE_STR);
        } else {
            vo.setStatus(MyConst.ZERO_STR);
        }
        return updateById(vo.toPO(User.class));
    }

    /**
     * 表格字段转换
     *
     * @return 转换结果
     */
    private Page<UserVO> selectPageAttach(IPage<User> page) {
        Page<UserVO> retPage = new Page<>();
        retPage.setTotal(page.getTotal());
        retPage.setCurrent(page.getCurrent());
        retPage.setSize(page.getSize());
        retPage.setPages(page.getPages());
        retPage.setRecords(BeanUtil.listPOToVO(page.getRecords(), UserVO.class));

        // 替换用户角色名称
        List<String> userIds = retPage.getRecords().stream().map(UserVO::getId).collect(Collectors.toList());
        List<Map<String, String>> listRole = roleService.listByUserIds(userIds);
        retPage.getRecords().forEach(vo -> {
            List<UserRoleVO> listUserRoleVO = new ArrayList<>();
            StringBuilder sb = new StringBuilder();
            // 循环角色信息
            listRole.forEach(role -> {
                if (role.get(UserRole.USER_ID).equals(vo.getId())) {
                    // 拼接角色名称
                    sb.append(sb.length() > 0 ? ", " : StrUtil.EMPTY).append(role.get(Role.REMARK));

                    // 用户角色信息
                    UserRoleVO userRoleVO = new UserRoleVO();
                    userRoleVO.setRoleId(role.get(Role.ID));
                    listUserRoleVO.add(userRoleVO);
                }
            });
            vo.setListUserRole(listUserRoleVO);
            if (sb.length() > 0) {
                vo.setRoleNames(sb.toString());
            }
        });

        // 部门名称
        List<String> deptIds = retPage.getRecords().stream().map(UserVO::getDeptId).collect(Collectors.toList());
        QueryWrapper<Dept> qw = new QueryWrapper<>();
        qw.in(Dept.ID, deptIds);
        List<Dept> listDept = deptService.list(qw);
        retPage.getRecords().forEach(vo -> {
            StringBuilder sb = new StringBuilder();
            listDept.forEach(dept -> {
                if (dept.getId().equals(vo.getDeptId())) {
                    sb.append(sb.length() > 0 ? ", " : StrUtil.EMPTY).append(dept.getName());
                }
            });
            if (sb.length() > 0) {
                vo.setDeptName(sb.toString());
            }
        });

        return retPage;
    }


    /**
     * 查询部门下一级节点信息
     *
     * @param superList 部门父节点集合
     * @param deptList  部门集合
     */
    private void findChildren(List<DeptVO> superList, List<Dept> deptList) {
        // 循环所有顶级部门信息
        for (DeptVO deptVO : superList) {
            // 组装子部门信息
            List<DeptVO> children = new ArrayList<>();
            for (Dept dept : deptList) {
                if (StrUtil.isNotEmpty(deptVO.getId()) && deptVO.getId().equals(dept.getParentId())) {
                    DeptVO vo = BeanUtil.toBean(dept, DeptVO.class);
                    vo.setParentName(deptVO.getName());
                    children.add(vo);
                }
            }
            // 设置子部门信息并递归下一级
            deptVO.setChildren(children);
            findChildren(children, deptList);
        }
    }
}
