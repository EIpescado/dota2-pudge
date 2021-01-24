package pers.yurwisher.dota2.pudge.system.pojo.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author yq
 * @date 2020/10/08 20:11
 * @description 用户To
 * @since V1.0.0
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class SystemUserTo extends EntityFileCountTo {

    private static final long serialVersionUID = -115023961674668038L;
    private Long id;

    private String username;

    private String nickname;

    private String phone;

    private String mail;

    private String dateCreated;

    private Integer state;
}
