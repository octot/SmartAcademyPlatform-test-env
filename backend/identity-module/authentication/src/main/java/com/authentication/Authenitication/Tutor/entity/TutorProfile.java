package com.authentication.Authenitication.Tutor.entity;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.Student.enums.StudentClass;
import com.authentication.Authenitication.Student.enums.Syllabus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tutor_profile")
@Setter
@Getter
public class TutorProfile {
    @Id
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private AppUser user;

    private String aadhaarNumber;

    private Boolean hasVehicle;
    private String vehicleType;

    private String qualification;

    private Boolean entranceCoaching;

    private Integer experienceYears;

    @ElementCollection
    private List<String> subjects;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<StudentClass> classesHandled;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Syllabus> syllabusHandled;

    @ElementCollection
    private List<String> preferredLocations;

    private Boolean guidelinesAccepted;

    private String remarks;

}
