package com.medical.medical.repository;

import com.medical.medical.models.entity.Admin;
import com.medical.medical.models.entity.Medecin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository

public interface AdminRepo extends JpaRepository<Admin,Integer> {
    Optional<Admin> findAdminByEmail(String email);
    @Query("SELECT e.password FROM Admin e WHERE e.email = :email")
    Optional<String> findPasswordByEmail(@Param("email") String email);


}
