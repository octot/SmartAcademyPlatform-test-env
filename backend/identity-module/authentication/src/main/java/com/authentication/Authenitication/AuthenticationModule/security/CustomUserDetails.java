package com.authentication.Authenitication.AuthenticationModule.security;

import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


//Cannot use userdetails because it contains  limited fields

@Getter
@Setter
public class CustomUserDetails implements UserDetails {

    private final AppUser user;

    public CustomUserDetails(AppUser user) {
        this.user = user;
    }

    public String getEmail() {
        return user.getEmail();
    }


    public Integer getTokenVersion() {
        return user.getTokenVersion();
    }

    public Long getId(){
        return user.getId();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return UserDetails.super.isAccountNonExpired();
    }

    @Override
    public boolean isAccountNonLocked() {
        return UserDetails.super.isAccountNonLocked();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return UserDetails.super.isCredentialsNonExpired();
    }

    @Override
    public boolean isEnabled() {
        return UserDetails.super.isEnabled();
    }
}
