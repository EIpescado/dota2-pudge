package pers.yurwisher.dota2.pudge.system.pojo.fo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yq
 * @date 2018/11/16 09:38
 * @description 重置密码
 * @since V1.0.0
 */
@Data
public class ResetPasswordFo implements Serializable {
    private static final long serialVersionUID = 984897616818417570L;

    @NotBlank(message = "旧密码必填")
    private String oldPass;

    @NotBlank(message = "新密码必填")
    private String newPass;

    @NotBlank(message = "确认密码必填")
    private String confirmPass;
}
