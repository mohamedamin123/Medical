package com.medical.medical.controller.API;

import com.medical.medical.models.dto.req.PatientReqDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.services.interf.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Patients/Patient")
@RequiredArgsConstructor
@Slf4j
public class PatientController {

    private final PatientService service;

//------------------------------------------------------------------------------------------------------------------find
    @GetMapping(path = "/find-all")
    public List<PatientResDTO> findAllPatient()
    {
        return this.service.findAllPatient();
    }

    @GetMapping(path = "/find-by-id/{id}")
    public Optional<PatientResDTO> findPatientById(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de Patient est : {}", id);

        return this.service.findPatientById(id);
    }

    @GetMapping(path = "/find-by-email/{email}")
    public Optional<PatientResDTO> findPatientByEmail(@PathVariable(name = "email")  String email)
    {
        log.info("email de Patient est : {}", email);

        return this.service.findPatientByEmail(email);
    }

    @GetMapping(path = "/find-by-tel/{tel}")
    public Optional<PatientResDTO> findPatientByTel(@PathVariable(name = "tel")  String tel)
    {
        log.info("id de Patient est : {}", tel);
        log.info("tel de Patient est : {}", tel);

        return this.service.findPatientByTel(tel);
    }

    @GetMapping(path = "/find-all-by-date-de-naissance")
    public List<PatientResDTO> findPatientsByDateDeNaissance(@RequestParam LocalDate date)
    {
        return this.service.findPatientsByDateDeNaissance(date);
    }

    @GetMapping(path = "/find-all-by-prenom-or-nom")
    public List<PatientResDTO> findPatientsByPrenomOrNom(@RequestParam String prenom,@RequestParam String nom)
    {
        return this.service.findPatientsByPrenomOrNom(prenom, nom);
    }


    @GetMapping(path = "/find-all-after-delete")
    public List<PatientResDTO> findAllPatientAfterDelete()
    {
        return this.service.findAllPatientAfterDelete();
    }

    @GetMapping(path = "/after-delete/find-by-id/{id}")
    public Optional<PatientResDTO> findPatientByIdAfterDelete(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de Patient est : {}", id);

        return this.service.findPatientByIdAfterDelete(id);
    }

    @GetMapping(path = "/after-delete/find-by-email/{email}")
    public Optional<PatientResDTO> findPatientByEmailAfterDelete(@PathVariable(name = "email")  String email)
    {
        log.info("email de Patient est : {}", email);

        return this.service.findPatientByEmailAfterDelete(email);
    }

    @GetMapping(path = "/after-deletefind-by-tel//{tel}")
    public Optional<PatientResDTO> findPatientByTelAfterDelete(@PathVariable(name = "tel")  String tel)
    {
        log.info("id de Patient est : {}", tel);
        log.info("tel de Patient est : {}", tel);

        return this.service.findPatientByTelAfterDelete(tel);
    }

    @GetMapping(path = "/find-all-by-date-de-naissance-after-delete")
    public List<PatientResDTO> findPatientsByDateDeNaissanceAfterDelete(@RequestParam LocalDate date)
    {
        return this.service.findPatientsByDateDeNaissanceAfterDelete(date);
    }

    @GetMapping(path = "/find-all-by-prenom-or-nom-after-delete")
    public List<PatientResDTO> findPatientsByPrenomOrNomAfterDelete(@RequestParam String prenom,@RequestParam String nom)
    {
        return this.service.findPatientsByPrenomOrNomAfterDelete(prenom, nom);
    }
//------------------------------------------------------------------------------------------------------------------save

    @PostMapping(path = "/save")
    public void savePatient(@RequestBody PatientReqDTO user)
    {
        this.service.savePatient(user);
    }

    @PutMapping(path = "/update")
    public void updatePatient(@RequestBody PatientReqDTO user)
    {
        this.service.savePatient(user);
    }

//----------------------------------------------------------------------------------------------------------------delete

    @DeleteMapping(path = "/delete-by-id/{id}")
    public void deletePatientById(@PathVariable(name = "id")  Integer id)
    {
        this.service.deletePatientById(id);
    }

    @DeleteMapping(path = "/delete")
    public void deletePatient(@RequestBody PatientReqDTO user)
    {
        this.service.deletePatient(user);
    }
}
