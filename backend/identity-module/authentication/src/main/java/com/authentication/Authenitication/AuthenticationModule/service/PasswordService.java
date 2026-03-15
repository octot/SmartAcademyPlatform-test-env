package com.authentication.Authenitication.AuthenticationModule.service;


import com.authentication.Authenitication.AuthenticationModule.dto.ChangePasswordRequest;
import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.AuthenticationModule.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public PasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public void changePassword(ChangePasswordRequest request, String name) {
        AppUser user = userRepository.findByUsername(name).orElseThrow(() -> new AppException("AUTH_011"));
        if (!passwordEncoder.matches(request.oldPassword(), user.getPassword())) {
            throw new AppException("AUTH_004");
        }
        if (passwordEncoder.matches(request.newPassword(), user.getPassword())) {
            throw new AppException("AUTH_005");
        }
        String encodedNewPassword = passwordEncoder.encode(request.newPassword());
        user.setPassword(encodedNewPassword);
        user.setTokenVersion(user.getTokenVersion() + 1);
        userRepository.save(user);
    }
}
