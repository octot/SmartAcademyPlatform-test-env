package com.authentication.Authenitication.user.controller;


import com.authentication.Authenitication.Tutor.dto.TutorProfileResponse;
import com.authentication.Authenitication.Tutor.dto.TutorSetupRequest;
import com.authentication.Authenitication.Tutor.dto.TutorUpdateRequest;
import com.authentication.Authenitication.user.dto.StudentProfileResponse;
import com.authentication.Authenitication.user.dto.StudentSetupRequest;
import com.authentication.Authenitication.user.dto.StudentUpdateRequest;
import com.authentication.Authenitication.user.service.ProfileService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/profile")
public class ProfileController {

    private final ProfileService profileService;

    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping("/setup/tutor")
    public ResponseEntity<?> setupProfile(@Valid @RequestBody TutorSetupRequest request) {
        profileService.setupForTutor(request);
        return ResponseEntity.ok("Tutor Profile setup completed");
    }

    @PostMapping("/setup/student")
    public ResponseEntity<?> setupProfile(@Valid @RequestBody StudentSetupRequest request) {
        profileService.setupForStudent(request);
        return ResponseEntity.ok("Student Profile setup completed");
    }

    @GetMapping("/tutor")
    public ResponseEntity<TutorProfileResponse> getTutorProfile() {
        return ResponseEntity.ok(profileService.getTutorProfile());
    }

    @PutMapping("/tutor")
    public ResponseEntity<TutorProfileResponse> updateTutorProfile(@Valid @RequestBody TutorUpdateRequest request) {
        TutorProfileResponse response=profileService.updateTutorProfile(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/student")
    public ResponseEntity<StudentProfileResponse> getStudentProfile() {
        return ResponseEntity.ok(profileService.getStudentProfile());
    }

    @PutMapping("/student")
    public ResponseEntity<StudentProfileResponse> updateStudentProfile(
            @Valid @RequestBody StudentUpdateRequest request) {
        StudentProfileResponse response = profileService.updateStudentProfile(request);
        return ResponseEntity.ok(response);

    }

}
