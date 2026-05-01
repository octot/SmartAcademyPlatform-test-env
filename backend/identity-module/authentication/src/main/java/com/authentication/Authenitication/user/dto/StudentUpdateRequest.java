package com.authentication.Authenitication.user.dto;

import com.authentication.Authenitication.Student.enums.StudentClass;
import com.authentication.Authenitication.Student.enums.Syllabus;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class StudentUpdateRequest {

    @NotNull
    private StudentClass studentClass;

    @NotNull
    private Syllabus syllabus;

}
