package pers.yurwisher.dota2.pudge.system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemUserQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemUserTo;
import pers.yurwisher.dota2.pudge.system.service.ISystemUserService;
import pers.yurwisher.dota2.pudge.wrapper.PageR;
import pers.yurwisher.dota2.pudge.wrapper.R;

/**
 * @author yq
 * @date 2020/10/08 18:35
 * @description 用户
 * @since V1.0.0
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class SystemUserController {

    private final ISystemUserService systemUserService;

    @GetMapping
    public R<PageR<SystemUserTo>> list(@ModelAttribute SystemUserQo qo){
        return R.ok(systemUserService.list(qo));
    }
}
