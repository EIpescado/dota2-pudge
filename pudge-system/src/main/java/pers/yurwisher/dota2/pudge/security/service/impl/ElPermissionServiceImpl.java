package pers.yurwisher.dota2.pudge.security.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import org.springframework.stereotype.Service;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.security.JwtUser;

import java.util.Arrays;
import java.util.List;

/**
 * @author yq
 * @date 2020/09/22 16:37
 * @description 全局自定义权限校验 自动给admin放行
 * @since V1.0.0
 */
@Service(value = "el")
public class ElPermissionServiceImpl {

    public Boolean check(String ...permissions){
        CurrentUser currentUser = JwtUser.current();
        if(currentUser.isAdmin()){
            return true;
        }else{
            //未配置 permission标识
            if(permissions == null || permissions.length == 0){
                return false;
            }else{
                // 获取当前用户的所有权限
                List<String> elPermissions = currentUser.getPermissions();
                // 判断当前用户的所有权限是否包含接口上定义的权限
                return CollectionUtil.isNotEmpty(elPermissions) && Arrays.stream(permissions).anyMatch(elPermissions::contains);
            }
        }
    }
}
