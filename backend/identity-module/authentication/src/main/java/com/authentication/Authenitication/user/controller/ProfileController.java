package com.authentication.Authenitication.user.controller;


import com.authentication.Authenitication.Tutor.dto.TutorSetupRequest;
import com.authentication.Authenitication.user.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/setup/tutor")
    public ResponseEntity<?> setupProfile(@Valid @RequestBody TutorSetupRequest request) {
        profileService.setup(request);
        return ResponseEntity.ok("Tutor Profile setup completed");
    }

}
