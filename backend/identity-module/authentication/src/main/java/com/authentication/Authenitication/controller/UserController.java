package com.authentication.Authenitication.controller;


import com.authentication.Authenitication.dto.AboutUserResponse;
import com.authentication.Authenitication.security.CustomUserDetails;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping("/about")
    public ResponseEntity<?> aboutUser(Authentication auth) {
        CustomUserDetails user =
                (CustomUserDetails) auth.getPrincipal();
        AboutUserResponse response = new AboutUserResponse(
                user.getUsername(),
                user.getEmail(),
                auth.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .toList()
        );
        return ResponseEntity.ok(response);

    }


/*
    @PutMapping("/change-email")
    public ResponseEntity<?> changeEmail(...) { }

    @PutMapping("/change-username")
    public ResponseEntity<?> changeUsername(...) { }

    @PutMapping("/change-password")
    public ResponseEntity<?> changePassword(...) { }
 */
}
