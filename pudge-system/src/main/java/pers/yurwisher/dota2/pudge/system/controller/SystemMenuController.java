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
import pers.yurwisher.dota2.pudge.security.JwtUser;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemMenuFo;
import pers.yurwisher.dota2.pudge.system.service.ISystemMenuService;
import pers.yurwisher.dota2.pudge.wrapper.R;

/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单
 * @since V1.0.0
 */
@RestController
@RequestMapping("/menu")
public class SystemMenuController extends BaseController{
    private ISystemMenuService menuService;

    public SystemMenuController(ISystemMenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    @PreAuthorize("@el.check('menu:add')")
    public R create(@RequestBody @Validated SystemMenuFo fo){
        menuService.create(fo);
        return R.ok();
    }

    @PostMapping("{id}")
    @PreAuthorize("@el.check('menu:edit')")
    public R update(@PathVariable(name = "id")Long id, @RequestBody @Validated SystemMenuFo fo){
        menuService.update(id,fo);
        return R.ok();
    }

    @GetMapping("{id}")
    public R get(@PathVariable(name = "id")Long id){
        return R.ok(menuService.getById(id));
    }

    /**
     * 用户的菜单树
     */
    @GetMapping("tree")
    public R tree(){
        return R.ok(menuService.tree(JwtUser.currentUserId()));
    }

    /**
     * 完整菜单树,用于分配菜单及按钮
     */
    @GetMapping("wholeTree")
    public R wholeTree(){
        return R.ok(menuService.wholeTree());
    }

}
