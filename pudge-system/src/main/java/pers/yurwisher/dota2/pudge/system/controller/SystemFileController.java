package pers.yurwisher.dota2.pudge.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.base.BaseController;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemFileQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemFileTo;
import pers.yurwisher.dota2.pudge.system.service.ISystemFileService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.R;

/**
 * @author yq
 * @date 2020-12-31 17:12:48
 * @description 系统文件
 * @since V1.0.0
 */
@RestController
@RequestMapping("/file")
public class SystemFileController extends BaseController {
    private ISystemFileService systemFileService;

    public SystemFileController(ISystemFileService systemFileService) {
        this.systemFileService = systemFileService;
    }

    @GetMapping
    public R<PageR<SystemFileTo>> list(@ModelAttribute SystemFileQo qo) {
        return R.ok(systemFileService.list(qo));
    }



}
