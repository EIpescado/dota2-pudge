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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.annotation.Log;
import pers.yurwisher.dota2.pudge.system.pojo.fo.ChangeAccountInfoFo;
import pers.yurwisher.dota2.pudge.system.pojo.fo.ChangeMailFo;
import pers.yurwisher.dota2.pudge.system.pojo.fo.ResetPasswordFo;
import pers.yurwisher.dota2.pudge.system.pojo.fo.SystemUserFo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemLogQo;
import pers.yurwisher.dota2.pudge.system.pojo.qo.SystemUserQo;
import pers.yurwisher.dota2.pudge.system.pojo.to.SystemUserTo;
import pers.yurwisher.dota2.pudge.system.pojo.to.UserSystemLogTo;
import pers.yurwisher.dota2.pudge.system.pojo.vo.SystemUserVo;
import pers.yurwisher.dota2.pudge.system.service.ISystemLogService;
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
    private final ISystemLogService systemLogService;

    @Log("用户列表")
    @GetMapping
    public R<PageR<SystemUserTo>> list(@ModelAttribute SystemUserQo qo) {
        return R.ok(systemUserService.list(qo));
    }

    @PostMapping
    @PreAuthorize("@el.check('user:create')")
    public R<String> create(@RequestBody @Validated SystemUserFo fo) {
        systemUserService.create(fo);
        return R.ok();
    }

    @PostMapping("/{id}")
    @PreAuthorize("@el.check('user:update')")
    public R<String> update(@RequestBody @Validated SystemUserFo fo, @PathVariable Long id) {
        systemUserService.update(id, fo);
        return R.ok();
    }

    @GetMapping("/{id}")
    public R<SystemUserVo> get(@PathVariable Long id) {
        return R.ok(systemUserService.get(id));
    }

    @PostMapping("/resetPassword/{id}")
    public R<String> resetPassword(@PathVariable Long id) {
        systemUserService.resetPassword(id);
        return R.ok();
    }

    @PostMapping("/switchEnabled/{id}")
    @PreAuthorize("@el.check('user:switchEnabled')")
    public R<String> switchEnabled(@PathVariable Long id) {
        systemUserService.switchEnabled(id);
        return R.ok();
    }

    @PostMapping("changePassword")
    public R<String> changePassword(@RequestBody @Validated ResetPasswordFo resetPasswordFo) {
        systemUserService.changePassword(resetPasswordFo);
        return R.ok();
    }

    @GetMapping(value = "/sendChangeMailCode")
    public R<String> sendChangeMailCode(@RequestParam String mail) {
        systemUserService.sendChangeMailCode(mail);
        return R.ok();
    }

    @PostMapping("changeMail")
    public R<String> changeMail(@RequestBody @Validated ChangeMailFo changeMailFo) {
        systemUserService.changeMail(changeMailFo);
        return R.ok();
    }

    @PostMapping("changeAccountInfo")
    public R<String> changeAccountInfo(@RequestBody @Validated ChangeAccountInfoFo changeAccountInfoFo) {
        systemUserService.changeAccountInfo(changeAccountInfoFo);
        return R.ok();
    }

    @GetMapping("log")
    public R<PageR<UserSystemLogTo>> log(@ModelAttribute SystemLogQo qo) {
        return R.ok(systemLogService.userLogList(qo));
    }
}
