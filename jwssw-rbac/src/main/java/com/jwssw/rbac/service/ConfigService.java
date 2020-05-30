package com.jwssw.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwssw.core.base.service.MyService;
import com.jwssw.core.http.PageRequest;
import com.jwssw.rbac.entity.Config;
import com.jwssw.rbac.vo.ConfigVO;

/**
 * 系统配置表 服务接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 19:36:48
 * @since JDK 11
 */
 public interface ConfigService extends MyService<Config> {
     /**
      * 分页查询
      *
      * @param req 分页请求对象
      * @return 查询结果
      */
     IPage<Config> selectPage(PageRequest req);

     /**
      * 保存数据源信息
      *
      * @param vo Config信息
      * @return 是否成功
      */
     public boolean save(ConfigVO vo);
 }