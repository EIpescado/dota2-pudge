package pers.yurwisher.dota2.pudge.system.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.base.BaseController;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemLogQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemLogTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemLogVo;
import pers.yurwisher.dota2.pudge.system.service.ISystemLogService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.R;

/**
 * @author yq
 * @date 2020-12-01 15:17:44
 * @description 系统日志
 * @since V1.0.0
 */
@RestController
@RequestMapping("/log")
public class SystemLogController extends BaseController {
    private ISystemLogService systemLogService;

    public SystemLogController(ISystemLogService systemLogService) {
        this.systemLogService = systemLogService;
    }


    @GetMapping("{id}")
    public R<SystemLogVo> get(@PathVariable(name = "id") Long id) {
        return R.ok(systemLogService.get(id));
    }

    @GetMapping
    public R<PageR<SystemLogTo>> list(@ModelAttribute SystemLogQo qo) {
        return R.ok(systemLogService.list(qo));
    }
}
