package com.authentication.Authenitication.Student.entity;

import jakarta.persistence.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "student_requirement")
public class StudentRequirement {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private StudentProfile student;

    @ElementCollection
    private List<String> subjects;

    private LocalDate tuitionStartDate;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<DayOfWeek> daysRequired;

    private String remarks;
}
