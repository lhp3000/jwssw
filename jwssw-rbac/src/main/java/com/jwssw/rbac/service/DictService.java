package com.jwssw.rbac.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwssw.core.base.service.MyService;
import com.jwssw.core.http.PageRequest;
import com.jwssw.rbac.entity.Dict;
import com.jwssw.rbac.vo.DictVO;

/**
 * 字典表 服务接口
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 17:52:57
 * @since JDK 11
 */
 public interface DictService extends MyService<Dict> {
     /**
      * 分页查询
      *
      * @param req 分页请求对象
      * @return 查询结果
      */
     IPage<Dict> selectPage(PageRequest req);

     /**
      * 保存数据源信息
      *
      * @param vo Dict信息
      * @return 是否成功
      */
     public boolean save(DictVO vo);
 }