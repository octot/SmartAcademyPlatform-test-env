package com.authentication.Authenitication.Tutor.dto;

import com.authentication.Authenitication.Student.enums.StudentClass;
import com.authentication.Authenitication.Student.enums.Syllabus;
import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TutorSetupRequest {

    @NotBlank
    private String aadhaarNumber;

    @NotNull
    private Boolean hasVehicle;

    private String vehicleType;

    @NotBlank
    @Column(nullable = false)
    private String qualification;

    @NotNull
    @Min(0)
    @Max(50)
    private Integer experienceYears;

    @NotEmpty
    private List<String> subjects;

    @NotEmpty
    private List<StudentClass> classesHandled;

    @NotEmpty
    private List<Syllabus> syllabusHandled;

    @NotBlank
    private  List<String>  preferredLocations;

    private String remarks;

    @NotNull
    @AssertTrue(message = "You must accept guidelines")
    private Boolean guidelinesAccepted;

    @NotNull
    private PaymentDetails payment;
}
