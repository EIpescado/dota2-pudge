package pers.yurwisher.dota2.pudge.system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemUserFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemUserQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemUserTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemUserVo;
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

    @PostMapping
    @PreAuthorize("@el.check('user:create')")
    public R<String> create(@RequestBody @Validated SystemUserFo fo){
        systemUserService.create(fo);
        return R.ok();
    }

    @PostMapping("/{id}")
    @PreAuthorize("@el.check('user:update')")
    public R<String> update(@RequestBody @Validated SystemUserFo fo, @PathVariable Long id){
        systemUserService.update(id,fo);
        return R.ok();
    }

    @GetMapping("/{id}")
    public R<SystemUserVo> get(@PathVariable Long id){
        return R.ok(systemUserService.get(id));
    }
}
