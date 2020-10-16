package pers.yurwisher.dota2.pudge.security;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

import java.util.List;

/**
 * @author yq
 * @date 2020/09/21 11:28
 * @description 用户信息
 * @since V1.0.0
 */
@Data
public class CurrentUser {

    private Long id;
    private String username;
    private String password;
    private String nickname;
    private String avatar;
    private String phone;
    private Boolean enabled;

    /**角色*/
    private List<String> roles;

    /**数据访问域*/
    @JSONField(serialize = false)
    private List<Long> dataScopes;
    /**用户权限*/
    private List<String> permissions;

    public boolean isAdmin(){
        return CollectionUtil.isNotEmpty(roles) && roles.contains("admin");
    }
}
