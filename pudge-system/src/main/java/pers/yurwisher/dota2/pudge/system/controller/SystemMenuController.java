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
import pers.yurwisher.dota2.pudge.system.entity.SystemMenu;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemMenuFo;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuAndButtonTreeNode;
import pers.yurwisher.dota2.pudge.system.pojo.tree.MenuTreeNode;
import pers.yurwisher.dota2.pudge.system.service.ISystemMenuService;
import pers.yurwisher.dota2.pudge.wrapper.R;

import java.util.List;

/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单
 * @since V1.0.0
 */
@RestController
@RequestMapping("/menu")
public class SystemMenuController extends BaseController{
    private final ISystemMenuService menuService;

    public SystemMenuController(ISystemMenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    @PreAuthorize("@el.check('menu:create')")
    public R<String> create(@RequestBody @Validated SystemMenuFo fo){
        menuService.create(fo);
        return R.ok();
    }

    @PostMapping("{id}")
    @PreAuthorize("@el.check('node:update')")
    public R<String> update(@PathVariable(name = "id")Long id, @RequestBody @Validated SystemMenuFo fo){
        menuService.update(id,fo);
        return R.ok();
    }

    @GetMapping("{id}")
    public R<SystemMenu> get(@PathVariable(name = "id")Long id){
        return R.ok(menuService.getById(id));
    }

    @PostMapping("delete")
    @PreAuthorize("@el.check('node:delete')")
    public R<String> delete(@RequestBody List<MenuAndButtonTreeNode> nodes){
        menuService.delete(nodes);
        return R.ok();
    }

    /**
     * 用户的菜单树
     */
    @GetMapping("tree")
    public R<List<MenuTreeNode>> tree(){
        return R.ok(menuService.tree());
    }

    /**
     * 完整菜单树,用于分配菜单及按钮
     */
    @GetMapping("wholeTree")
    @PreAuthorize("@el.check('menu:wholeTree')")
    public R<List<MenuAndButtonTreeNode>> wholeTree(){
        return R.ok(menuService.wholeTree());
    }

}
