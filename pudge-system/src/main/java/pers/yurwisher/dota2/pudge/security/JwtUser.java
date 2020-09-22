package pers.yurwisher.dota2.pudge.security;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import pers.yurwisher.dota2.pudge.enums.SystemCustomTipEnum;
import pers.yurwisher.dota2.pudge.system.exception.SystemCustomException;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author yq
 * @date 2020/09/21 11:27
 * @description JwtUser
 * @since V1.0.0
 */
@Getter
public class JwtUser implements UserDetails {

    private static final long serialVersionUID = -7443286142453207263L;

    private CurrentUser user;
    /**权限*/
    private final List<GrantedAuthority> authorities;

    public JwtUser(CurrentUser user) {
        List<String> permissions = user.getPermissions();
        List<GrantedAuthority> authorities;
        if(CollectionUtil.isNotEmpty(permissions)){
            authorities =  permissions.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
        }else{
            authorities = CollectionUtil.newArrayList();
        }
        this.authorities = authorities;
        this.user = user;
    }

    @Override
    @JSONField(serialize = false)
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    @JSONField(serialize = false)
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JSONField(serialize = false)
    public boolean isEnabled() {
        return user.getEnabled();
    }

    /**
     * 当前用户
     * @return CurrentUser
     */
    public static CurrentUser current(){
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null){
            throw new SystemCustomException(SystemCustomTipEnum.LOGIN_EXPIRED);
        }
        Object principal = authentication.getPrincipal();
        if (principal == null) {
            throw new SystemCustomException(SystemCustomTipEnum.LOGIN_EXPIRED);
        } else {
            //匿名用户
            if (principal instanceof String) {
                throw new SystemCustomException(SystemCustomTipEnum.LOGIN_EXPIRED);
            }
            return ((JwtUser) principal).user;
        }
    }

    /**
     * 当前用户ID
     * @return 用户ID
     */
    public static Long currentUserId(){
        return current().getId();
    }

    /**
     * 当前用户账号
     * @return 用户账号
     */
    public static String currentUsername(){
       return current().getUsername();
    }
}
