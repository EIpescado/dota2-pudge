package pers.yurwisher.dota2.pudge.config;

import cn.hutool.core.util.StrUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.ChineseGifCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pers.yurwisher.dota2.pudge.security.bean.LoginCode;
import pers.yurwisher.dota2.pudge.security.bean.LoginCodeEnum;
import pers.yurwisher.dota2.pudge.security.bean.LoginProperties;

import java.awt.Font;

/**
 * @author yq
 * @date 2020/09/21 17:53
 * @description 登录配置
 * @since V1.0.0
 */
@Configuration
public class LoginConfig {

    @Bean
    @ConfigurationProperties(prefix = "login")
    public LoginProperties loginProperties(){
        return new LoginProperties();
    }


    @Bean
    public Captcha captcha(){
        LoginProperties loginProperties = loginProperties();
        LoginCode loginCode = loginProperties.getCodeConfig();
        if(loginCode == null){
            loginCode= new LoginCode();
            loginCode.setType(LoginCodeEnum.arithmetic);
        }
        return switchCaptcha(loginCode);
    }


    /**
     * 依据配置信息生产验证码
     *
     * @param loginCode 验证码配置信息
     * @return /
     */
    private Captcha switchCaptcha(LoginCode loginCode) {
        Captcha captcha;
        synchronized (this) {
            switch (loginCode.getType()) {
                case arithmetic:
                    // 算术类型 https://gitee.com/whvse/EasyCaptcha
                    captcha = new ArithmeticCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    // 几位数运算，默认是两位
                    captcha.setLen(loginCode.getLength());
                    break;
                case chinese:
                    captcha = new ChineseCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                case chinese_gif:
                    captcha = new ChineseGifCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                case gif:
                    captcha = new GifCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                case spec:
                    captcha = new SpecCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    captcha.setLen(loginCode.getLength());
                    break;
                default:
                    throw new RuntimeException("验证码配置信息错误！正确配置查看 LoginCodeEnum ");
            }
        }
        if(StrUtil.isNotBlank(loginCode.getFontName())){
            captcha.setFont(new Font(loginCode.getFontName(), Font.PLAIN, loginCode.getFontSize()));
        }
        return captcha;
    }
}
