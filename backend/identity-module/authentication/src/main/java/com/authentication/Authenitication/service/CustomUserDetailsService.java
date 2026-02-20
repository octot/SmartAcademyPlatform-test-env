package com.authentication.Authenitication.service;

import com.authentication.Authenitication.entity.*;
import com.authentication.Authenitication.enums.UserStatus;
import com.authentication.Authenitication.exception.AppException;
import com.authentication.Authenitication.security.CustomUserDetails;
import com.authentication.Authenitication.util.UsernameValidator;
import com.authentication.Authenitication.dto.ChangePasswordRequest;
import com.authentication.Authenitication.dto.RegisterRequestDTO;
import com.authentication.Authenitication.repository.UserRepository;
import com.authentication.Authenitication.verification.otp.Otp;
import com.authentication.Authenitication.verification.otp.OtpPurpose;
import com.authentication.Authenitication.verification.otp.OtpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Set;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AppUser user = findByUsername(username);
        return new CustomUserDetails(user);
    }

    public AppUser findByUsername(String login) {
        AppUser user;
        if (login.contains("@")) {
            user = userRepository.findByEmail(login).orElseThrow(() -> new AppException("AUTH_011"));
        } else {
            user = userRepository.findByUsername(login)
                    .orElseThrow(() -> new AppException("AUTH_011"));
        }
        if (!user.isEmailVerified()) {
            throw new AppException("AUTH_002");
        }
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new AppException("AUTH_003");
        }
        return user;
    }

    public void existsByUsername(String username) {
        if (userRepository.existsByUsername(username)) {
            throw new AppException("AUTH_006"); // Username already taken
        }
    }

    public void register(RegisterRequestDTO request) {
        checkUserNameAndEmailExist(request);
        createUser(request, Set.of(roleService.getDefaultUserRole()));
    }

    public void registerForAdmin(RegisterRequestDTO request) {
        checkUserNameAndEmailExist(request);
        createUser(request, Set.of(roleService.getAdminUserRole()));
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


    public void checkUserNameAndEmailExist(RegisterRequestDTO request) {
        UsernameValidator.validate(request.getUsername());
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new AppException("AUTH_006");
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new AppException("AUTH_007");
        }
    }

    private void createUser(RegisterRequestDTO request, Set<RoleEntity> userRole) {
        AppUser user = new AppUser();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(userRole);
        user.setEmailVerified(false);
        user.setStatus(UserStatus.PENDING);

        String otpValue = OtpUtil.generateOtp();
        Otp otp = new Otp();
        otp.setUser(user);
        otp.setOtpValue(otpValue);
        otp.setPurpose(OtpPurpose.SIGNUP);
        otp.setExpiryTime(Instant.now().plus(5, ChronoUnit.MINUTES));
        otp.setAttemptCount(0);
        otp.setMaxAttempts(3);
        otp.setUsed(false);



        userRepository.save(user);
    }


}
