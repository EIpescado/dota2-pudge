package pers.yurwisher.dota2.pudge.system.pojo.to;

import lombok.Data;

/**
 * @author yq
 * @date 2020/10/08 20:11
 * @description 用户To
 * @since V1.0.0
 */
@Data
public class SystemUserTo {

    private Long id;

    private String username;

    private String nickname;

    private String phone;

    private String mail;

    private String dateCreated;

    private Boolean enabled;
}
