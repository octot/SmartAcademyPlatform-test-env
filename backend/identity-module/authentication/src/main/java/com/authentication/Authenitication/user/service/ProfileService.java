package com.authentication.Authenitication.user.service;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.AuthenticationModule.repository.UserRepository;
import com.authentication.Authenitication.AuthenticationModule.security.SecurityUtils;
import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.Student.entity.StudentProfile;
import com.authentication.Authenitication.Student.repository.StudentProfileRepository;
import com.authentication.Authenitication.Tutor.dto.PaymentDetails;
import com.authentication.Authenitication.Tutor.dto.TutorSetupRequest;
import com.authentication.Authenitication.Tutor.entity.TutorPaymentDetails;
import com.authentication.Authenitication.Tutor.entity.TutorProfile;
import com.authentication.Authenitication.Tutor.repository.TutorProfileRepository;
import com.authentication.Authenitication.payment.repository.PaymentRepository;
import com.authentication.Authenitication.role.Role;
import com.authentication.Authenitication.user.dto.StudentSetupRequest;
import jakarta.annotation.Nonnull;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ProfileService {
    private final SecurityUtils securityUtils;

    private final StudentProfileRepository studentProfileRepo;
    private final TutorProfileRepository tutorProfileRepo;
    private final PaymentRepository paymentRepository;
    private final UserRepository userRepository;

    public ProfileService(SecurityUtils securityUtils, StudentProfileRepository studentProfileRepo, TutorProfileRepository tutorProfileRepo, PaymentRepository paymentRepository, UserRepository userRepository) {
        this.securityUtils = securityUtils;
        this.studentProfileRepo = studentProfileRepo;
        this.tutorProfileRepo = tutorProfileRepo;
        this.paymentRepository = paymentRepository;
        this.userRepository = userRepository;
    }

    public void setupForTutor(TutorSetupRequest request) {
        AppUser currentUser = securityUtils.getCurrentUser();

        if (tutorProfileRepo.existsByUser(currentUser)) {
            throw new AppException("TUTOR_PROFILE_EXIST");
        }
        AppUser user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new AppException("AUTH_011"));

        TutorProfile profile = buildTutorProfile(request, user);
        tutorProfileRepo.save(profile);

        savePayment(profile, request.getPayment());

    }

    public void setupForStudent(StudentSetupRequest request) {
        AppUser currentUser = securityUtils.getCurrentUser();

        if (tutorProfileRepo.existsByUser(currentUser)) {
            throw new AppException("STUDENT_PROFILE_EXIST");
        }
        AppUser user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new AppException("AUTH_011"));

        StudentProfile profile = buildStudentProfile(request, user);
        studentProfileRepo.save(profile);
    }

    private StudentProfile buildStudentProfile(StudentSetupRequest request, AppUser user) {
        StudentProfile  studentProfile=new StudentProfile();
        studentProfile.setUser(user);
        studentProfile.setStudentClass(request.getStudentClass());
        studentProfile.setSyllabus(request.getSyllabus());
        return studentProfile;
    }

    private TutorProfile buildTutorProfile(TutorSetupRequest request, AppUser user) {
        TutorProfile profile = new TutorProfile();

        profile.setUser(user);
        profile.setAadhaarNumber(request.getAadhaarNumber());
        profile.setHasVehicle(request.getHasVehicle());
        profile.setVehicleType(request.getVehicleType());

        profile.setQualification(request.getQualification());
        profile.setExperienceYears(request.getExperienceYears());

        profile.setSubjects(request.getSubjects());
        profile.setClassesHandled(request.getClassesHandled());
        profile.setSyllabusHandled(request.getSyllabusHandled());

        profile.setPreferredLocations(request.getPreferredLocations());
        profile.setRemarks(request.getRemarks());

        profile.setGuidelinesAccepted(request.getGuidelinesAccepted());
        return profile;
    }


    public Map<RoleName, Boolean> getProfileStatus(AppUser user) {
        Map<RoleName, Boolean> status = new HashMap<>();
        for (Role role : user.getRoles()) {
            RoleName roleName = role.getName();

            switch (roleName) {
                case STUDENT -> status.put(roleName, studentProfileRepo.existsByUser(user));

                case TUTOR -> status.put(roleName, tutorProfileRepo.existsByUser(user));
            }
        }
        return status;
    }


    public void savePayment(TutorProfile tutorProfile, PaymentDetails paymentDetails) {

        if (paymentDetails == null) {
            throw new AppException("PAYMENT_NOT_FOUND");
        }

        switch (paymentDetails.getPaymentMethod()) {
            case GPAY -> {
                if (paymentDetails.getGpayNumber() == null) {
                    throw new AppException("GPAY_NUMBER_REQUIRED");
                }
            }
            case BANK -> {
                if (paymentDetails.getAccountNumber() == null
                        || paymentDetails.getIfscCode() == null) {
                    throw new AppException("BANK_DETAILS_INCOMPLETE");
                }
            }
        }
        TutorPaymentDetails entity = buildEntity(tutorProfile, paymentDetails);
        paymentRepository.save(entity);
    }

    private TutorPaymentDetails buildEntity(TutorProfile profile, PaymentDetails payment) {
        TutorPaymentDetails entity = new TutorPaymentDetails();

        entity.setTutor(profile);
        entity.setPaymentMethod(payment.getPaymentMethod());

        entity.setGpayNumber(payment.getGpayNumber());

        entity.setAccountHolderName(payment.getAccountHolderName());
        entity.setBankName(payment.getBankName());
        entity.setBranchName(payment.getBranchName());
        entity.setAccountNumber(payment.getAccountNumber());
        entity.setIfscCode(payment.getIfscCode());

        return entity;
    }

}
