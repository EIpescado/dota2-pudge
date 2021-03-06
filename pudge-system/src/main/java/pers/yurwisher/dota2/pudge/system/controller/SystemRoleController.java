package pers.yurwisher.dota2.pudge.system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
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
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuAndButtonTreeNode;
import pers.yurwisher.dota2.pudge.system.service.ISystemRoleService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.R;
import pers.yurwisher.dota2.pudge.wrapper.Selector;

import java.util.List;

/**
 * @author yq
 * @date 2020-09-21 14:45:55
 * @description 角色
 * @since V1.0.0
 */
@RestController
@RequestMapping("/role")
public class SystemRoleController extends BaseController{
    private final ISystemRoleService systemRoleService;

    public SystemRoleController(ISystemRoleService systemRoleService) {
        this.systemRoleService = systemRoleService;
    }

    @PostMapping
    @PreAuthorize("@el.check('role:create')")
    public R<String> create(@RequestBody SystemRoleFo fo){
        systemRoleService.create(fo);
        return R.ok();
    }

    @PostMapping("{id}")
    @PreAuthorize("@el.check('role:update')")
    public R<String> update(@PathVariable(name = "id")Long id, @RequestBody SystemRoleFo fo){
        systemRoleService.update(id,fo);
        return R.ok();
    }

    @GetMapping("{id}")
    public R<SystemRole> get(@PathVariable(name = "id")Long id){
        return R.ok(systemRoleService.getById(id));
    }

    @PostMapping("/delete/{id}")
    public R<String> delete(@PathVariable(name = "id")Long id){
        systemRoleService.delete(id);
        return R.ok();
    }

    @GetMapping
    @PreAuthorize("@el.check('role:list')")
    public R<PageR<SystemRoleTo>> list(@ModelAttribute SystemRoleQo qo){
        return R.ok(systemRoleService.list(qo));
    }

    @GetMapping("select")
    public R<List<Selector<Long>>> select(){
        return R.ok(systemRoleService.select());
    }

    @PreAuthorize("@el.check('menu:bind')")
    @PostMapping("/{roleId}/bindMenuAndButton")
    public R<String> bindMenuAndButton(@RequestBody List<MenuAndButtonTreeNode> nodes, @PathVariable Long roleId){
        systemRoleService.bindMenuAndButton(roleId,nodes);
        return R.ok();
    }

    @GetMapping("/{roleId}/singleRoleMenuAndButton")
    public R<List<String>> singleRoleMenuAndButton(@PathVariable Long roleId){
        return R.ok(systemRoleService.singleRoleMenuAndButton(roleId));
    }

}
