package pers.yurwisher.dota2.pudge.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.base.BaseController;
import pers.yurwisher.dota2.pudge.system.entity.SystemRole;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemRoleFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemRoleQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemRoleTo;
import pers.yurwisher.dota2.pudge.system.service.ISystemRoleService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.R;

/**
 * @author yq
 * @date 2020-09-21 14:45:55
 * @description 角色
 * @since V1.0.0
 */
@RestController
@RequestMapping("/systemRole")
public class SystemRoleController extends BaseController{
    private ISystemRoleService systemRoleService;

    public SystemRoleController(ISystemRoleService systemRoleService) {
        this.systemRoleService = systemRoleService;
    }

    @PostMapping
    public R create(@RequestBody SystemRoleFo fo){
        systemRoleService.create(fo);
        return R.ok();
    }

    @PostMapping("{id}")
    public R update(@PathVariable(name = "id")Long id, @RequestBody SystemRoleFo fo){
        systemRoleService.update(id,fo);
        return R.ok();
    }

    @GetMapping("{id}")
    public R<SystemRole> get(@PathVariable(name = "id")Long id){
        return R.ok(systemRoleService.getById(id));
    }

    @PostMapping("/delete/{id}")
    public R delete(@PathVariable(name = "id")Long id){
        systemRoleService.delete(id);
        return R.ok();
    }

    @GetMapping
    public R<PageR<SystemRoleTo>> list(@ModelAttribute SystemRoleQo qo){
        return R.ok(systemRoleService.list(qo));
    }


}
