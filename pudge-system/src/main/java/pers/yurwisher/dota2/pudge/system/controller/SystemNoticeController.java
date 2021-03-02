package pers.yurwisher.dota2.pudge.system.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.annotation.Log;
import pers.yurwisher.dota2.pudge.base.BaseController;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemNoticeFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemNoticeQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemNoticeTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemNoticeVo;
import pers.yurwisher.dota2.pudge.system.service.ISystemNoticeService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.R;

/**
 * @author yq
 * @date 2020-11-16 13:41:32
 * @description 系统公告
 * @since V1.0.0
 */
@RestController
@RequestMapping("/notice")
public class SystemNoticeController extends BaseController {
    private final ISystemNoticeService systemNoticeService;

    public SystemNoticeController(ISystemNoticeService systemNoticeService) {
        this.systemNoticeService = systemNoticeService;
    }

    @PostMapping
    public R<String> create(@RequestBody @Validated SystemNoticeFo fo) {
        systemNoticeService.create(fo);
        return R.ok();
    }

    @PostMapping("{id}")
    @Log("修改公告")
    public R<String> update(@PathVariable(name = "id") Long id, @RequestBody @Validated SystemNoticeFo fo) {
        systemNoticeService.update(id, fo);
        return R.ok();
    }

    @GetMapping("{id}")
    @Log("公告详情")
    public R<SystemNoticeVo> get(@PathVariable(name = "id") Long id) {
        return R.ok(systemNoticeService.get(id));
    }

    @PostMapping("/delete/{id}")
    public R<String> delete(@PathVariable(name = "id") Long id) {
        systemNoticeService.delete(id);
        return R.ok();
    }

    @GetMapping
    public R<PageR<SystemNoticeTo>> list(@ModelAttribute SystemNoticeQo qo) {
        return R.ok(systemNoticeService.list(qo));
    }


}
