package pers.yurwisher.dota2.pudge.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import pers.yurwisher.dota2.pudge.annotation.Log;
import pers.yurwisher.dota2.pudge.base.BaseController;
import pers.yurwisher.dota2.pudge.system.pojo.SystemFileUploadBack;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemDictFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemDictQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemDictTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemDictVo;
import pers.yurwisher.dota2.pudge.system.service.ISystemDictService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.R;
import pers.yurwisher.dota2.pudge.wrapper.Selector;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author yq
 * @date 2020-11-04 11:18:42
 * @description 字典
 * @since V1.0.0
 */
@RestController
@RequestMapping("/dict")
public class SystemDictController extends BaseController {
    private ISystemDictService systemDictService;

    public SystemDictController(ISystemDictService systemDictService) {
        this.systemDictService = systemDictService;
    }

    @PostMapping
    @Log("创建字典")
    public R<String> create(@RequestBody SystemDictFo fo) {
        systemDictService.create(fo);
        return R.ok();
    }

    @PostMapping("{id}")
    @Log("修改字典")
    public R<String> update(@PathVariable(name = "id") Long id, @RequestBody SystemDictFo fo) {
        systemDictService.update(id, fo);
        return R.ok();
    }

    @GetMapping("{id}")
    public R<SystemDictVo> get(@PathVariable(name = "id") Long id) {
        return R.ok(systemDictService.get(id));
    }

    @PostMapping("/delete/{id}")
    public R<String> delete(@PathVariable(name = "id") Long id) {
        systemDictService.delete(id);
        return R.ok();
    }

    @GetMapping
    public R<PageR<SystemDictTo>> list(@ModelAttribute SystemDictQo qo) {
        return R.ok(systemDictService.list(qo));
    }

    @GetMapping("select")
    public R<List<Selector<String>>> select(@RequestParam String dictType) {
        return R.ok(systemDictService.select(dictType));
    }

    @PostMapping("import")
    @Log("导入字典")
    public R<String> importDict(@RequestParam("file") MultipartFile file) {
        systemDictService.importDict(file);
        return R.ok();
    }

}
