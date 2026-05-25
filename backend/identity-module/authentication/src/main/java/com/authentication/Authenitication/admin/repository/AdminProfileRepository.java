package com.authentication.Authenitication.admin.repository;

import com.authentication.Authenitication.admin.entity.AdminProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AdminProfileRepository extends JpaRepository<AdminProfile, UUID> {


}
