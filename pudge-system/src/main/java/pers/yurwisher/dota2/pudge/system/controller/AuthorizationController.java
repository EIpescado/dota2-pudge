package pers.yurwisher.dota2.pudge.system.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.annotation.rest.AnonymousGetMapping;
import pers.yurwisher.dota2.pudge.annotation.rest.AnonymousPostMapping;
import pers.yurwisher.dota2.pudge.base.BaseController;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.security.form.UserLoginForm;
import pers.yurwisher.dota2.pudge.system.service.AuthorizationService;
import pers.yurwisher.dota2.pudge.wrapper.R;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author yq
 * @date 2020/09/18 09:22
 * @description 授权
 * @since V1.0.0
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthorizationController extends BaseController {

    private final AuthorizationService authorizationService;

    @AnonymousPostMapping(value = "/login")
    public R<Map<String, Object>> login(@RequestBody @Validated UserLoginForm form, HttpServletRequest request) {
        return authorizationService.login(form, request);
    }

    @GetMapping(value = "/info")
    public R<CurrentUser> info() {
        return authorizationService.info();
    }

    @AnonymousGetMapping(value = "/code")
    public R<Map<String, Object>>  getCode() {
        return authorizationService.getCode();
    }

    @AnonymousPostMapping(value = "/logout")
    public R<String> logout(HttpServletRequest request) {
        return authorizationService.logout(request);
    }

}
