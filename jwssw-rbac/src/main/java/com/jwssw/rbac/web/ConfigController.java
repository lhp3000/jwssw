package com.jwssw.rbac.web;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jwssw.core.base.web.MyController;
import com.jwssw.core.http.HttpResult;
import com.jwssw.core.http.PageRequest;
import com.jwssw.core.util.Utils;
import com.jwssw.core.util.Utils.BeanUtil;
import com.jwssw.rbac.entity.Config;
import com.jwssw.rbac.service.ConfigService;
import com.jwssw.rbac.vo.ConfigVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * 系统配置表 控制
 *
 * @author Rewloc
 * @version 1.0
 * @date 2020-02-18 19:36:48
 * @since JDK 11
 */
@RestController
@RequestMapping("config")
public class ConfigController extends MyController<ConfigService> {
    /**
     * 分页查询
     *
     * @param req 分页请求对象
     * @return 查询结果I
     */
    @PreAuthorize("hasAuthority('sys:config:view')")
    @PostMapping("selectPage")
    public HttpResult<IPage<Config>> selectPage(@RequestBody PageRequest req) {
        return HttpResult.success(service.selectPage(req));
    }

    /**
     * 保存系统配置表信息
     *
     * @param vo 数据源信息
     * @return 保存结果
     */
    @PreAuthorize("hasAuthority('sys:config:add') AND hasAuthority('sys:config:edit')")
    @PostMapping("save")
    public HttpResult<Boolean> save(@RequestBody ConfigVO vo) {
        checkNotNull(vo, "无效的系统配置表");
        return HttpResult.success(service.save(vo));
    }

    /**
     * 批量删除系统配置表信息
     *
     * @param listVO 批量用户信息
     * @return
     */
    @PreAuthorize("hasAuthority('sys:config:delete')")
    @PostMapping("delete")
    public HttpResult<Boolean> delete(@RequestBody List<ConfigVO> listVO) {
        checkNotNull(listVO, "无效的系统配置表");
        return HttpResult.success(service.deleteEntityByIds(BeanUtil.listVOToPO(listVO, Config.class)));
    }
}
