package pers.yurwisher.dota2.pudge.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.base.BaseController;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemDictTypeFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemDictTypeQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemDictTypeTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemDictTypeVo;
import pers.yurwisher.dota2.pudge.system.service.ISystemDictTypeService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.R;

/**
 * @author yq
 * @date 2020-11-04 11:13:36
 * @description 字典类型
 * @since V1.0.0
 */
@RestController
@RequestMapping("/dictType")
public class SystemDictTypeController extends BaseController {
    private ISystemDictTypeService systemDictTypeService;

    public SystemDictTypeController(ISystemDictTypeService systemDictTypeService) {
        this.systemDictTypeService = systemDictTypeService;
    }

    @PostMapping
    public R<String> create(@RequestBody SystemDictTypeFo fo) {
        systemDictTypeService.create(fo);
        return R.ok();
    }

    @PostMapping("{id}")
    public R<String> update(@PathVariable(name = "id") Long id, @RequestBody SystemDictTypeFo fo) {
        systemDictTypeService.update(id, fo);
        return R.ok();
    }


    @PostMapping("/delete/{id}")
    public R<String> delete(@PathVariable(name = "id") Long id) {
        systemDictTypeService.delete(id);
        return R.ok();
    }

    @GetMapping
    public R<PageR<SystemDictTypeTo>> list(@ModelAttribute SystemDictTypeQo qo) {
        return R.ok(systemDictTypeService.list(qo));
    }

    @GetMapping("{id}")
    public R<SystemDictTypeVo> get(@PathVariable(name = "id") Long id) {
        return R.ok(systemDictTypeService.get(id));
    }

}
