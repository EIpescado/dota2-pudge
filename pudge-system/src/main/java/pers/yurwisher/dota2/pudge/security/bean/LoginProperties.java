package pers.yurwisher.dota2.pudge.security.bean;

import cn.hutool.core.util.StrUtil;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.ChineseCaptcha;
import com.wf.captcha.ChineseGifCaptcha;
import com.wf.captcha.GifCaptcha;
import com.wf.captcha.SpecCaptcha;
import com.wf.captcha.base.Captcha;
import lombok.Data;

import java.awt.*;


/**
 * 登录配置
 * @author yq
 */
@Data
public class LoginProperties {

    /**
     * 账号单用户 登录
     */
    private boolean single = false;

    /**
     *密码前端会通过公钥加密, 解密私钥
     */
    private String passwordPrivateKey;

    private LoginCode codeConfig;

    /**
     * 依据配置信息生产验证码
     */
    public Captcha switchCaptcha() {
        LoginCode loginCode = this.codeConfig;
        Captcha captcha;
        synchronized (this) {
            switch (loginCode.getType()) {
                case arithmetic:
                    // 算术类型 https://gitee.com/whvse/EasyCaptcha
                    captcha = new ArithmeticCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    break;
                case chinese:
                    captcha = new ChineseCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    break;
                case chinese_gif:
                    captcha = new ChineseGifCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    break;
                case gif:
                    captcha = new GifCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    break;
                case spec:
                    captcha = new SpecCaptcha(loginCode.getWidth(), loginCode.getHeight());
                    break;
                default:
                    throw new RuntimeException("验证码配置信息错误！正确配置查看 LoginCodeEnum ");
            }
        }
        // 几位数运算，默认是两位
        captcha.setLen(loginCode.getLength());
        if(StrUtil.isNotBlank(loginCode.getFontName())){
            captcha.setFont(new Font(loginCode.getFontName(), Font.PLAIN, loginCode.getFontSize()));
        }
        return captcha;
    }
}
