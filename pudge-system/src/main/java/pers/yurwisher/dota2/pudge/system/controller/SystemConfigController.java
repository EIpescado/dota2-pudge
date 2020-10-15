package pers.yurwisher.dota2.pudge.system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.base.BaseController;
import pers.yurwisher.dota2.pudge.system.entity.SystemConfig;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemConfigFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemConfigQo;
import pers.yurwisher.dota2.pudge.system.service.ISystemConfigService;
import pers.yurwisher.dota2.pudge.wrapper.R;

/**
 * @author yq
 * @date 2020-10-14 18:59:48
 * @description 系统配置
 * @since V1.0.0
 */
@RestController
@RequestMapping("/config")
public class SystemConfigController extends BaseController{
    private ISystemConfigService systemConfigService;

    public SystemConfigController(ISystemConfigService systemConfigService) {
        this.systemConfigService = systemConfigService;
    }

    @PostMapping
    @PreAuthorize("@el.check('config:create')")
    public R<String> create(@RequestBody @Validated SystemConfigFo fo){
        systemConfigService.create(fo);
        return R.ok();
    }

    @PostMapping("{id}")
    @PreAuthorize("@el.check('config:update')")
    public R<String> update(@PathVariable(name = "id")Long id, @RequestBody @Validated SystemConfigFo fo){
        systemConfigService.update(id,fo);
        return R.ok();
    }

    @GetMapping("{id}")
    public R<SystemConfig> get(@PathVariable(name = "id")Long id){
        return R.ok(systemConfigService.getById(id));
    }

    @GetMapping
    @PreAuthorize("@el.check('config:list')")
    public R list(@ModelAttribute SystemConfigQo qo){
        return R.ok(systemConfigService.list(qo));
    }


}
