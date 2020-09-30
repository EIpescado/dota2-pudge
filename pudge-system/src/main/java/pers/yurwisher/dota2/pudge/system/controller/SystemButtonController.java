package pers.yurwisher.dota2.pudge.system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.base.BaseController;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemButtonFo;
import pers.yurwisher.dota2.pudge.system.service.ISystemButtonService;
import pers.yurwisher.dota2.pudge.wrapper.R;

/**
 * @author yq
 * @date 2020-09-26 11:35:17
 * @description 按钮
 * @since V1.0.0
 */
@RestController
@RequestMapping("/button")
public class SystemButtonController extends BaseController{
    private ISystemButtonService systemButtonService;

    public SystemButtonController(ISystemButtonService systemButtonService) {
        this.systemButtonService = systemButtonService;
    }

    @PostMapping
    @PreAuthorize("@el.check('menu:create')")
    public R create(@RequestBody @Validated SystemButtonFo fo){
        systemButtonService.create(fo);
        return R.ok();
    }

    @PostMapping("{id}")
    @PreAuthorize("@el.check('menu:update')")
    public R update(@PathVariable(name = "id")Long id, @RequestBody @Validated SystemButtonFo fo){
        systemButtonService.update(id,fo);
        return R.ok();
    }

    @GetMapping("{id}")
    public R get(@PathVariable(name = "id")Long id){
        return R.ok(systemButtonService.getById(id));
    }


}
