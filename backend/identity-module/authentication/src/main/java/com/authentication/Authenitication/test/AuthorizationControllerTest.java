package com.authentication.Authenitication.test;


import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class AuthorizationControllerTest {


    @PreAuthorize("hasAuthority('USER_VIEW')")
    @GetMapping("/view")
    public String view() {
        return "VIEW OK";
    }

    @PreAuthorize("hasAuthority('USER_APPROVE')")
    @GetMapping("/approve")
    public String approve() {
        return "APPROVE OK";
    }

    @GetMapping("/debug")
    public String debug() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getAuthorities().toString();
    }

}
