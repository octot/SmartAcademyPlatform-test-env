package com.authentication.Authenitication.user.dto;

import com.authentication.Authenitication.Student.enums.StudentClass;
import com.authentication.Authenitication.Student.enums.Syllabus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StudentProfileResponse {
    private StudentClass studentClass;
    private Syllabus syllabus;
}
