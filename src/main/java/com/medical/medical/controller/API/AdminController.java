package com.medical.medical.controller.API;

import com.medical.medical.models.dto.req.AdminReqDTO;
import com.medical.medical.models.dto.res.AdminResDTO;
import com.medical.medical.services.interf.AdminService;
import com.medical.medical.services.interf.AdminService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admins/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {

    private final AdminService service;

//------------------------------------------------------------------------------------------------------------------find
    @GetMapping(path = "/find-all")
    public List<AdminResDTO> findAllAdmin()
    {
        return this.service.findAllAdmin();
    }

    @GetMapping(path = "find-by-id/{id}")
    public Optional<AdminResDTO> findAdminById(@PathVariable(name = "id")  Integer id)
    {

        return this.service.findAdminById(id);
    }

    @GetMapping(path = "/find-by-email/{email}")
    public Optional<AdminResDTO> findAdminByEmail(@PathVariable(name = "email")  String email)
    {
        return this.service.findAdminByEmail(email);
    }



    @GetMapping(path = "/find-password-by-email")
    public Optional<String> findPasswordByEmail(@RequestParam String email) {
        return this.service.findPasswordByEmail(email);
    }
//------------------------------------------------------------------------------------------------------------------save

    @PostMapping(path = "/save")
    public void saveAdmin(@RequestBody AdminReqDTO user)
    {
        this.service.saveAdmin(user);
    }

    @PutMapping(path = "/update")
    public void updateAdmin(@RequestBody AdminReqDTO user)
    {
        this.service.updateAdmin(user);
    }

//----------------------------------------------------------------------------------------------------------------delete

    @DeleteMapping(path = "/delete-by-id/{id}")
    public void deleteAdminById(@PathVariable(name = "id")  Integer id)
    {
        this.service.deleteAdminById(id);
    }

    @DeleteMapping(path = "/delete")
    public void deleteAdmin(@RequestBody AdminReqDTO user)
    {
        this.service.deleteAdmin(user);
    }
}
