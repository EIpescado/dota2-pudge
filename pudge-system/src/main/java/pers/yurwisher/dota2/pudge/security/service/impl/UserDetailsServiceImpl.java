package pers.yurwisher.dota2.pudge.security.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pers.yurwisher.dota2.pudge.security.CurrentUser;
import pers.yurwisher.dota2.pudge.security.JwtUser;
import pers.yurwisher.dota2.pudge.system.service.ISystemUserService;

/**
 * @author yq
 * @date 2020/09/21 11:35
 * @description 用户
 * @since V1.0.0
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);

    private final ISystemUserService systemUserService;

    public UserDetailsServiceImpl(ISystemUserService systemUserService) {
        this.systemUserService = systemUserService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        CurrentUser currentUser = systemUserService.findUserByUsername(username);
        return new JwtUser(currentUser);
    }
}
