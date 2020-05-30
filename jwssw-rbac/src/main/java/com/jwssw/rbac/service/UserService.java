package com.jwssw.rbac.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jwssw.core.http.PageRequest;
import com.jwssw.core.base.service.MyService;
import com.jwssw.rbac.entity.User;
import com.jwssw.rbac.vo.DeptVO;
import com.jwssw.rbac.vo.RoleVO;
import com.jwssw.rbac.vo.UserVO;

import java.util.List;

/**
 * 系统用户服务接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020/1/26 13:18
 * @since JDK 11
 */
public interface UserService extends MyService<User> {
    /**
     * 分页查询
     *
     * @param req 分页请求对象
     * @return 查询结果
     */
    Page<UserVO> selectPage(PageRequest req);

    /**
     * 保存用户信息
     *
     * @param entity 用户信息
     * @return 是否成功
     */
    public boolean save(UserVO entity);

    /**
     * 获取系统所有角色信息
     *
     * @return 角色集合
     */
    List<RoleVO> listRole();

    /**
     * 获取系统所有部门角色信息
     *
     * @return 部门集合
     */
    List<DeptVO> listDept();

    /**
     * 重置用户密码
     *
     * @param userId 用户Id
     * @return 是否成功
     */
    boolean resetPassword(String userId);

    /**
     * 解锁或者锁定用户
     *
     * @param vo 用户信息
     * @return 是否成功
     */
    boolean changeStatus(UserVO vo);
}
