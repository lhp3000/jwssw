package com.jwssw.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwssw.core.base.service.MyService;
import com.jwssw.core.http.PageRequest;
import com.jwssw.rbac.entity.RoleMenu;
import com.jwssw.rbac.vo.RoleMenuVO;

/**
 * 角色菜单 服务接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 14:26:12
 * @since JDK 11
 */
 public interface RoleMenuService extends MyService<RoleMenu> {
     /**
      * 分页查询
      *
      * @param req 分页请求对象
      * @return 查询结果
      */
     IPage<RoleMenu> selectPage(PageRequest req);

     /**
      * 保存数据源信息
      *
      * @param vo RoleMenu信息
      * @return 是否成功
      */
     public boolean save(RoleMenuVO vo);
 }