package pers.yurwisher.dota2.pudge.system.controller;

import com.alibaba.fastjson.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pers.yurwisher.dota2.pudge.annotation.rest.AnonymousGetMapping;
import pers.yurwisher.dota2.pudge.annotation.rest.AnonymousPostMapping;
import pers.yurwisher.dota2.pudge.base.BaseController;
import pers.yurwisher.dota2.pudge.security.form.UserLoginForm;
import pers.yurwisher.dota2.pudge.system.service.AuthorizationService;
import pers.yurwisher.dota2.pudge.utils.PudgeUtil;
import pers.yurwisher.dota2.pudge.wrapper.R;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yq
 * @date 2020/09/18 09:22
 * @description 授权
 * @since V1.0.0
 */
@RestController
@RequestMapping("/auth")
public class AuthorizationController extends BaseController {

    private AuthorizationService authorizationService;

    public AuthorizationController(AuthorizationService authorizationService) {
        this.authorizationService = authorizationService;
    }

    @AnonymousGetMapping
    public R test(HttpServletRequest request){
        return R.ok(PudgeUtil.getUserClientInfo(request));
    }

    @AnonymousPostMapping(value = "/login")
    public R login(@RequestBody UserLoginForm form, HttpServletRequest request){
        return authorizationService.login(form,request);
    }

    @AnonymousGetMapping(value = "/code")
    public R getCode() {
        return authorizationService.getCode();
    }
}
