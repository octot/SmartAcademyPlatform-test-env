package com.authentication.Authenitication.AuthenticationModule.service;

import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.security.CustomUserDetails;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class SecurityUserDetailsService implements UserDetailsService {

    private final UserService userService;

    public SecurityUserDetailsService( UserService userService) {
        this.userService = userService;
    }

    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = userService.findByUsername(username);
        return new CustomUserDetails(user);
    }

}
