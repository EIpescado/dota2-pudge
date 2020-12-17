package pers.yurwisher.dota2.pudge.system.pojo.fo;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * @author yq
 * @date 2020/12/17 17:16
 * @description 变更帐号信息Fo
 * @since V1.0.0
 */
@Data
public class ChangeAccountInfoFo implements Serializable {
    private static final long serialVersionUID = 6053361795809637434L;

    @NotBlank(message = "昵称必填")
    private String nickname;
}
