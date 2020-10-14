package pers.yurwisher.dota2.pudge.system.pojo.vo;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author yq
 * @date 2020/10/13 16:17
 * @description 用户vo
 * @since V1.0.0
 */
@Data
public class SystemUserVo implements Serializable {

    private static final long serialVersionUID = -4568113000008505894L;
    /**
     * 用户名
     */
    private String username;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 角色ID集合
     */
    List<Long> roleIds;

    private LocalDateTime lastUpdated;
}
