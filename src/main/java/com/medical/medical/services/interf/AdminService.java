package com.medical.medical.services.interf;

import com.medical.medical.models.dto.req.AdminReqDTO;
import com.medical.medical.models.dto.res.AdminResDTO;
import com.medical.medical.models.dto.res.AdminResDTO;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service

public interface AdminService {
    AdminResDTO saveAdmin(AdminReqDTO req);

    AdminResDTO updateAdmin(AdminReqDTO req);

    List<AdminResDTO> findAllAdmin();

     Optional<AdminResDTO> findAdminById(int id) ;

    Optional<AdminResDTO> findAdminByEmail(String email);


    void deleteAdmin(AdminReqDTO req);

    void deleteAdminById(int id);

    Optional<String> findPasswordByEmail(String email);







}