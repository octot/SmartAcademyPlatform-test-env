package com.authentication.Authenitication.Student.entity;

import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.Student.enums.StudentClass;
import com.authentication.Authenitication.Student.enums.Syllabus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Table(name = "student_profile")
@Getter
@Setter
public class StudentProfile {
    @Id
    private UUID id;

    @OneToOne
    @MapsId
    @JoinColumn(name = "id")
    private AppUser user;

    @Enumerated(EnumType.STRING)
    private StudentClass studentClass;

    @Enumerated(EnumType.STRING)
    private Syllabus syllabus;
}
