package com.authentication.Authenitication.Tutor.dto;

import com.authentication.Authenitication.Student.enums.StudentClass;
import com.authentication.Authenitication.Student.enums.Syllabus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class TutorUpdateRequest {

    private Boolean hasVehicle;
    private String vehicleType;

    private String qualification;
    private Integer experienceYears;

    private List<String> subjects;
    private List<StudentClass> classesHandled;
    private List<Syllabus> syllabusHandled;

    private List<String> preferredLocations;
    private String remarks;

    private PaymentDetails payment;
}
