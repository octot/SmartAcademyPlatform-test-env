package com.authentication.Authenitication.Student;


import com.authentication.Authenitication.AuthenticationModule.security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")

public class StudentController {
    private final StudentService studentService;


    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public String getStudents(@AuthenticationPrincipal CustomUserDetails user) {
        System.out.println(user);
        Long userId = user.getUser().getId();
        return studentService.getStudents(userId);
    }

}
