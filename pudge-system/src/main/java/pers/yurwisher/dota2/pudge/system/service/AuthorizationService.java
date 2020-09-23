package pers.yurwisher.dota2.pudge.system.service;

import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.wf.captcha.base.Captcha;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.security.JwtUser;
import pers.yurwisher.dota2.pudge.security.TokenProvider;
import pers.yurwisher.dota2.pudge.security.bean.LoginCodeEnum;
import pers.yurwisher.dota2.pudge.security.bean.LoginProperties;
import pers.yurwisher.dota2.pudge.security.bean.SecurityProperties;
import pers.yurwisher.dota2.pudge.security.form.UserLoginForm;
import pers.yurwisher.dota2.pudge.security.service.IOnlineUserService;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;
import pers.yurwisher.dota2.pudge.wrapper.R;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author yq
 * @date 2020/09/21 15:37
 * @description 授权service
 * @since V1.0.0
 */
@Service
@RequiredArgsConstructor
public class AuthorizationService {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final TokenProvider tokenProvider;
    private final IOnlineUserService onlineUserService;
    private final SecurityProperties properties;
    private final LoginProperties loginProperties;
    private final Captcha captcha;
    private final RedisTemplate<String,Object> redisTemplate;

    public R login(UserLoginForm form, HttpServletRequest request) {
        //校验验证码
        //this.verifyCode(form.getUuid(),form.getCode());
        //校验用户帐号密码 调用对应 UserDetailsService 获取用户信息 存入授权信息
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(form.getUsername(), form.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final JwtUser jwtUser = (JwtUser) authentication.getPrincipal();
        // 生成令牌
        String token = tokenProvider.createToken(jwtUser.getUser());
        // 保存在线信息
        onlineUserService.save(request,token,jwtUser);
        // 返回 token 与 用户信息
        Map<String, Object> authInfo = new HashMap<String, Object>(2) {
            private static final long serialVersionUID = 568330464125730896L;
            {
            put("token", properties.getPrefix() + token);
            //密码不返回给前端
            jwtUser.getUser().setPassword(null);
            put("user", jwtUser.getUser());
        }};
        return R.ok(authInfo);
    }

    /**
     * 校验验证码
     * @param codeId 验证码ID
     * @param codeVal 验证码
     */
    private void verifyCode(String codeId,String codeVal){
        //从redis获取验证码
        String code = (String) redisTemplate.opsForValue().get(codeId);
        if(StrUtil.isBlank(code)){
            //验证码过期
            throw new SystemCustomException(SystemCustomTipEnum.CODE_NOT_EXIST_OR_EXPIRED);
        }
        if(StrUtil.isBlank(codeVal) || !codeVal.equalsIgnoreCase(code)){
            //验证码错误
            throw new SystemCustomException(SystemCustomTipEnum.CODE_ERROR);
        }
        //从redis移除验证码
        redisTemplate.delete(codeId);
    }

    public R getCode() {
        String uuid = properties.getCodeKey() + IdUtil.simpleUUID();
        //当验证码类型为 arithmetic时且长度 >= 2 时，captcha.text()的结果有几率为浮点型
        String captchaValue = captcha.text();
        if (captcha.getCharType() - 1 == LoginCodeEnum.arithmetic.ordinal() && captchaValue.contains(StrUtil.DOT)) {
            captchaValue = captchaValue.split("\\.")[0];
        }
        // 保存
        redisTemplate.opsForValue().set(uuid, captchaValue, loginProperties.getCodeConfig().getExpiration(), TimeUnit.MINUTES);
        // 验证码信息
        Map<String, Object> imgResult = new HashMap<String, Object>(2) {
            private static final long serialVersionUID = 9009002806086752036L;
            {
            put("img", captcha.toBase64());
            put("uuid", uuid);
        }};
        return R.ok(imgResult);
    }
}
