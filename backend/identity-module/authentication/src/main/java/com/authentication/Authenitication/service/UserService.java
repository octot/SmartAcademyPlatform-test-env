package com.authentication.Authenitication.service;


import com.authentication.Authenitication.entity.AppUser;
import com.authentication.Authenitication.enums.UserStatus;
import com.authentication.Authenitication.exception.AppException;
import com.authentication.Authenitication.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
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

}
