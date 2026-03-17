package com.authentication.Authenitication.Student;


import com.authentication.Authenitication.Authorization.service.AuthorizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentService {


    private final AuthorizationService authorizationService ;


    public String  getStudents(Long userId) {
        authorizationService.authorize("student:view");
        return "List of Studetnts";
    }

}
