package com.authentication.Authenitication.user.service;


import com.authentication.Authenitication.AuthenticationModule.entity.AppUser;
import com.authentication.Authenitication.AuthenticationModule.exception.AppException;
import com.authentication.Authenitication.AuthenticationModule.repository.UserRepository;
import com.authentication.Authenitication.AuthenticationModule.security.SecurityUtils;
import com.authentication.Authenitication.Authorization.Enum.RoleName;
import com.authentication.Authenitication.Student.entity.StudentProfile;
import com.authentication.Authenitication.Student.repository.StudentProfileRepository;
import com.authentication.Authenitication.Tutor.dto.PaymentDetails;
import com.authentication.Authenitication.Tutor.dto.TutorProfileResponse;
import com.authentication.Authenitication.Tutor.dto.TutorSetupRequest;
import com.authentication.Authenitication.Tutor.dto.TutorUpdateRequest;
import com.authentication.Authenitication.Tutor.entity.TutorPaymentDetails;
import com.authentication.Authenitication.Tutor.entity.TutorProfile;
import com.authentication.Authenitication.Tutor.repository.TutorProfileRepository;
import com.authentication.Authenitication.payment.repository.PaymentRepository;
import com.authentication.Authenitication.role.Role;
import com.authentication.Authenitication.user.dto.StudentProfileResponse;
import com.authentication.Authenitication.user.dto.StudentSetupRequest;
import com.authentication.Authenitication.user.dto.StudentUpdateRequest;
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
        StudentProfile studentProfile = new StudentProfile();
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

    public TutorProfileResponse getTutorProfile() {

        AppUser currentUser = securityUtils.getCurrentUser();

        AppUser user = userRepository.findById(currentUser.getId())
                .orElseThrow(() -> new AppException("AUTH_011"));

        TutorProfile profile = tutorProfileRepo.findById(user.getId())
                .orElseThrow(() -> new AppException("TUTOR_PROFILE_NOT_FOUND"));

        TutorPaymentDetails payment = paymentRepository.findByTutorId(profile.getUser().getId()).orElseThrow(() -> new AppException("TUTOR_PROFILE_PAYMENT_DETAILS_NOT_FOUND"));


        return mapToResponse(profile, payment);
    }

    private TutorProfileResponse mapToResponse(
            TutorProfile profile,
            TutorPaymentDetails payment) {

        TutorProfileResponse res = new TutorProfileResponse();

        res.setAadhaarNumber(profile.getAadhaarNumber());
        res.setHasVehicle(profile.getHasVehicle());
        res.setVehicleType(profile.getVehicleType());

        res.setQualification(profile.getQualification());
        res.setExperienceYears(profile.getExperienceYears());

        res.setSubjects(profile.getSubjects());
        res.setClassesHandled(profile.getClassesHandled());
        res.setSyllabusHandled(profile.getSyllabusHandled());

        res.setPreferredLocations(profile.getPreferredLocations());
        res.setRemarks(profile.getRemarks());

        res.setPayment(mapPayment(payment));

        return res;
    }

    private PaymentDetails mapPayment(TutorPaymentDetails entity) {

        if (entity == null) return null;

        PaymentDetails dto = new PaymentDetails();

        dto.setPaymentMethod(entity.getPaymentMethod());
        dto.setGpayNumber(entity.getGpayNumber());

        dto.setAccountHolderName(entity.getAccountHolderName());
        dto.setBankName(entity.getBankName());
        dto.setBranchName(entity.getBranchName());
        dto.setAccountNumber(entity.getAccountNumber());
        dto.setIfscCode(entity.getIfscCode());

        return dto;
    }

    public TutorProfileResponse updateTutorProfile(TutorUpdateRequest request) {

        AppUser currentUser = securityUtils.getCurrentUser();

        AppUser user = userRepository.findById(currentUser.getId())
                .orElseThrow();


        TutorProfile profile = tutorProfileRepo.findById(user.getId())
                .orElseThrow(() -> new AppException("TUTOR_PROFILE_NOT_FOUND"));

        // update fields
        profile.setHasVehicle(request.getHasVehicle());
        profile.setVehicleType(request.getVehicleType());

        profile.setQualification(request.getQualification());
        profile.setExperienceYears(request.getExperienceYears());

        profile.setSubjects(request.getSubjects());
        profile.setClassesHandled(request.getClassesHandled());
        profile.setSyllabusHandled(request.getSyllabusHandled());

        profile.setPreferredLocations(request.getPreferredLocations());
        profile.setRemarks(request.getRemarks());

        tutorProfileRepo.save(profile);

        TutorPaymentDetails payment = updatePayment(profile, request.getPayment());

        return mapToResponse(profile, payment);
    }

    private TutorPaymentDetails updatePayment(TutorProfile profile, PaymentDetails payment) {

        TutorPaymentDetails entity =
                paymentRepository.findByTutorId(profile.getUser().getId()).orElseThrow(() -> new AppException("TUTOR_PROFILE_PAYMENT_DETAILS_NOT_FOUND"));

        if (entity == null) {
            entity = new TutorPaymentDetails();
            entity.setTutor(profile);
        }

        entity.setPaymentMethod(payment.getPaymentMethod());
        entity.setGpayNumber(payment.getGpayNumber());

        entity.setAccountHolderName(payment.getAccountHolderName());
        entity.setBankName(payment.getBankName());
        entity.setBranchName(payment.getBranchName());
        entity.setAccountNumber(payment.getAccountNumber());
        entity.setIfscCode(payment.getIfscCode());

        return paymentRepository.save(entity);

    }

    public StudentProfileResponse getStudentProfile() {

        AppUser currentUser = securityUtils.getCurrentUser();

        AppUser user = userRepository.findById(currentUser.getId())
                .orElseThrow();

        StudentProfile profile = studentProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException("STUDENT_PROFILE_NOT_FOUND"));

        StudentProfileResponse res = new StudentProfileResponse();
        res.setStudentClass(profile.getStudentClass());
        res.setSyllabus(profile.getSyllabus());

        return res;
    }

    public StudentProfileResponse updateStudentProfile(StudentUpdateRequest request) {

        AppUser currentUser = securityUtils.getCurrentUser();

        AppUser user = userRepository.findById(currentUser.getId())
                .orElseThrow();

        StudentProfile profile = studentProfileRepo.findByUserId(user.getId())
                .orElseThrow(() -> new AppException("STUDENT_PROFILE_NOT_FOUND"));

        profile.setStudentClass(request.getStudentClass());
        profile.setSyllabus(request.getSyllabus());

        studentProfileRepo.save(profile);

        StudentProfileResponse res = new StudentProfileResponse();
        res.setStudentClass(profile.getStudentClass());
        res.setSyllabus(profile.getSyllabus());

        return res;
    }
}
