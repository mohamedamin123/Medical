package com.medical.medical.controller.API;

import com.medical.medical.models.dto.req.DossierMedicalReqDTO;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.models.dto.res.DossierMedicalResDTO;
import com.medical.medical.services.interf.DossierMedicalService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/DossierMedicals/DossierMedical")
@RequiredArgsConstructor
@Slf4j
public class DossierMedicalController {

    private final DossierMedicalService service;

//------------------------------------------------------------------------------------------------------------------find
    @GetMapping(path = "/find-all")
    public List<DossierMedicalResDTO> findAllDossierMedical()
    {
        return this.service.findAllDossierMedical();
    }

    @GetMapping(path = "/find-by-id/{id}")
    public Optional<DossierMedicalResDTO> findDossierMedicalById(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de DossierMedical est : {}", id);

        return this.service.findDossierMedicalById(id);
    }

    @GetMapping(path = "/find-all-after-delete")
    public List<DossierMedicalResDTO> findAllDossierMedicalAfterDelete()
    {
        return this.service.findAllDossierMedicalAfterDelete();
    }

    @GetMapping(path = "/after-delete/find-by-id/{id}")
    public Optional<DossierMedicalResDTO> findDossierMedicalByIdAfterDelete(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de DossierMedicalResDTO est : {}", id);

        return this.service.findDossierMedicalByIdAfterDelete(id);
    }

//------------------------------------------------------------------------------------------------------------------save

    @PostMapping(path = "/save")
    public void saveDossierMedical(@RequestBody DossierMedicalReqDTO user)
    {
        this.service.saveDossierMedical(user);
    }

    @PutMapping(path = "/update")
    public void updateDossierMedical(@RequestBody DossierMedicalReqDTO user)
    {
        this.service.updateDossierMedical(user);
    }

//----------------------------------------------------------------------------------------------------------------delete

    @DeleteMapping(path = "/delete-by-id/{id}")
    public void deleteDossierMedicalById(@PathVariable(name = "id")  Integer id)
    {
        this.service.deleteDossierMedicalById(id);
    }

    @DeleteMapping(path = "/delete")
    public void deleteDossierMedical(@RequestBody DossierMedicalReqDTO user)
    {
        this.service.deleteDossierMedical(user);
    }
}
