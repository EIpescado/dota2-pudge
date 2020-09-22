package pers.yurwisher.dota2.pudge.system.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.base.BaseController;
import pers.yurwisher.dota2.pudge.system.pojo.fo.MenuFo;
import pers.yurwisher.dota2.pudge.system.service.IMenuService;
import pers.yurwisher.dota2.pudge.wrapper.R;

/**
 * @author yq
 * @date 2020-09-21 15:33:47
 * @description 菜单
 * @since V1.0.0
 */
@RestController
@RequestMapping("/menu")
public class MenuController extends BaseController{
    private IMenuService menuService;

    public MenuController(IMenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public R create(@RequestBody MenuFo fo){
        menuService.create(fo);
        return R.ok();
    }

    @PostMapping("{id}")
    public R update(@PathVariable(name = "id")Long id, @RequestBody MenuFo fo){
        menuService.update(id,fo);
        return R.ok();
    }

    @PreAuthorize("@el.check('menu:edit')")
    @GetMapping("{id}")
    public R get(@PathVariable(name = "id")Long id){
        return R.ok(menuService.getById(id));
    }

    @PostMapping("/delete/{id}")
    public R delete(@PathVariable(name = "id")Long id){
        menuService.delete(id);
        return R.ok();
    }


}
