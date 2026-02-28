package com.authentication.Authenitication;


import com.authentication.Authenitication.dto.RegisterRequestDTO;
import com.authentication.Authenitication.exception.ErrorProperties;
import com.authentication.Authenitication.controller.AuthController;
import com.authentication.Authenitication.security.JwtUtil;
import com.authentication.Authenitication.service.SecurityUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AuthenticationManager authenticationManager;
    @MockBean
    private JwtUtil jwtService;
    @MockBean
    private SecurityUserDetailsService customUserDetailsService;
    @MockBean
    private ErrorProperties errorProperties;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
        void register_shouldReturnOk_whenSuccess() throws Exception {
        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setUsername("john");
        request.setEmail("john@mail.com");
        request.setPassword("pass");

        mockMvc.perform(post("/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(content().string("User registered successfully"));
    }


}
