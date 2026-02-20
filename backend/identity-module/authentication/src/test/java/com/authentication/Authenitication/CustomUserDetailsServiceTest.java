package com.authentication.Authenitication;

import com.authentication.Authenitication.entity.AppUser;
import com.authentication.Authenitication.dto.RegisterRequestDTO;
import com.authentication.Authenitication.entity.RoleEntity;
import com.authentication.Authenitication.exception.AppException;
import com.authentication.Authenitication.repository.UserRepository;
import com.authentication.Authenitication.service.CustomUserDetailsService;
import com.authentication.Authenitication.service.RoleService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Mock
    private RoleService roleService;

    @Mock
    private PasswordEncoder passwordEncoder;


    //user already exist
    @Test
    void register_shouldThrowException_whenUsernameExists() {
        // Arrange
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("john");

        when(userRepository.existsByUsername("john"))
                .thenReturn(true);

        // Act + Assert
        AppException exception = assertThrows(
                AppException.class,
                () -> customUserDetailsService.register(request)
        );

        assertEquals("AUTH_006", exception.getErrorCode());

    }

    @Test
    void register_shouldSaveUser_whenUsernameNotExists() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("john");

        when(userRepository.existsByUsername("john"))
                .thenReturn(false);
        when(roleService.getDefaultUserRole())
                .thenReturn(new RoleEntity(1L, "USER"));

        when(passwordEncoder.encode(any()))
                .thenReturn("encodedPassword");

        customUserDetailsService.register(request);
        verify(userRepository).save(any());

    }

    @Test
    void register_shouldEncodePassword() {
        //Arrange
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("john");
        request.setEmail("john@mail.com");
        request.setPassword("plainPassword");


        when(userRepository.existsByUsername("john"))
                .thenReturn(false);

        when(userRepository.existsByEmail("john@mail.com"))
                .thenReturn(false);

        when(roleService.getDefaultUserRole())
                .thenReturn(new RoleEntity(1L, "USER"));

        when(passwordEncoder.encode("plainPassword"))
                .thenReturn("encodedPassword");

        //Act
        customUserDetailsService.register(request);

        // Assert
        verify(passwordEncoder).encode("plainPassword");

    }

    @Test
    void register_shouldSaveUser() {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("john");
        request.setEmail("john@mail.com");
        request.setPassword("plainPassword");

        when(userRepository.existsByUsername(any()))
                .thenReturn(false);

        when(userRepository.existsByEmail(any()))
                .thenReturn(false);

        when(roleService.getDefaultUserRole())
                .thenReturn(new RoleEntity(1L, "USER"));

        when(passwordEncoder.encode(any()))
                .thenReturn("encodedPass");

        // Act
        customUserDetailsService.register(request);

        // Assert
        verify(userRepository, times(1)).save(any(AppUser.class));

    }

}
