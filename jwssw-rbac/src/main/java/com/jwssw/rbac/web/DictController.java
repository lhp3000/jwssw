package com.jwssw.rbac.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwssw.core.base.web.MyController;
import com.jwssw.core.http.HttpResult;
import com.jwssw.core.http.PageRequest;
import com.jwssw.core.util.Utils;
import com.jwssw.core.util.Utils.BeanUtil;
import com.jwssw.rbac.entity.Dict;
import com.jwssw.rbac.service.DictService;
import com.jwssw.rbac.vo.DictVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 字典表 控制
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 17:52:57
 * @since JDK 11
 */
@RestController
@RequestMapping("dict")
public class DictController extends MyController<DictService> {
    /**
     * 分页查询
     *
     * @param req 分页请求对象
     * @return 查询结果I
     */
    @PreAuthorize("hasAuthority('sys:dict:view')")
    @PostMapping("selectPage")
    public HttpResult<IPage<Dict>> selectPage(@RequestBody PageRequest req) {
        checkNotNull(req, "无效的查询条件");
        return HttpResult.success(service.selectPage(req));
    }

    /**
     * 保存字典表信息
     *
     * @param vo 数据源信息
     * @return 保存结果
     */
    @PreAuthorize("hasAuthority('sys:dict:add') AND hasAuthority('sys:dict:edit')")
    @PostMapping("save")
    public HttpResult<Boolean> save(@RequestBody DictVO vo) {
        checkNotNull(vo, "无效的字典表");
        return HttpResult.success(service.save(vo));
    }

    /**
     * 批量删除字典表信息
     *
     * @param listVO 批量用户信息
     * @return
     */
    @PreAuthorize("hasAuthority('sys:dict:delete')")
    @PostMapping("delete")
    public HttpResult<Boolean> delete(@RequestBody List<DictVO> listVO) {
        checkNotNull(listVO, "无效的字典表");
        return HttpResult.success(service.deleteEntityByIds(BeanUtil.listVOToPO(listVO, Dict.class)));
    }
}
