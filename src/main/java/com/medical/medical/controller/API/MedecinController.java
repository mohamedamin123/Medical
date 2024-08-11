package com.medical.medical.controller.API;

import com.medical.medical.models.dto.req.MedecinReqDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.security.LoginViewModel;
import com.medical.medical.services.interf.MedecinService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medecins/medecin")
@RequiredArgsConstructor
@Slf4j
public class MedecinController {

    private final MedecinService service;

//------------------------------------------------------------------------------------------------------------------find
    @GetMapping(path = "/find-all")
    public List<MedecinResDTO> findAllMedecin()
    {
        return this.service.findAllMedecin();
    }

    @GetMapping(path = "find-by-id/{id}")
    public Optional<MedecinResDTO> findMedecinById(@PathVariable(name = "id")  Integer id)
    {

        return this.service.findMedecinById(id);
    }

    @GetMapping(path = "/find-by-email/{email}")
    public Optional<MedecinResDTO> findMedecinByEmail(@PathVariable(name = "email")  String email)
    {

        return this.service.findMedecinByEmail(email);
    }

    @GetMapping(path = "/find-by-tel/{tel}")
    public Optional<MedecinResDTO> findMedecinByTel(@PathVariable(name = "tel")  String tel)
    {

        return this.service.findMedecinByTel(tel);
    }

    @GetMapping(path = "/find-all-by-date-de-naissance")
    public List<MedecinResDTO> findMedecinsByDateDeNaissance(@RequestParam LocalDate date)
    {
        return this.service.findMedecinsByDateDeNaissance(date);
    }

    @GetMapping(path = "/find-all-by-prenom-or-nom")
    public List<MedecinResDTO> findMedecinsByPrenomOrNom(@RequestParam String prenom,@RequestParam String nom)
    {
        return this.service.findMedecinsByPrenomOrNom(prenom, nom);
    }



    @GetMapping(path = "/find-all-after-delete")
    public List<MedecinResDTO> findAllMedecinAfterDelete()
    {
        return this.service.findAllMedecinAfterDelete();
    }

    @GetMapping(path = "/after-delete/find-by-id/{id}")
    public Optional<MedecinResDTO> findMedecinByIdAfterDelete(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de Medecin est : {}", id);

        return this.service.findMedecinByIdAfterDelete(id);
    }

    @GetMapping(path = "/after-delete/find-by-email/{email}")
    public Optional<MedecinResDTO> findMedecinByEmailAfterDelete(@PathVariable(name = "email")  String email)
    {
        log.info("email de Medecin est : {}", email);

        return this.service.findMedecinByEmailAfterDelete(email);
    }

    @GetMapping(path = "/after-delete/find-by-tel/{tel}")
    public Optional<MedecinResDTO> findMedecinByTelAfterDelete(@PathVariable(name = "tel")  String tel)
    {
        log.info("id de Medecin est : {}", tel);
        log.info("tel de Medecin est : {}", tel);

        return this.service.findMedecinByTelAfterDelete(tel);
    }

    @GetMapping(path = "/find-all-by-date-de-naissance-after-delete")
    public List<MedecinResDTO> findMedecinsByDateDeNaissanceAfterDelete(@RequestParam LocalDate date)
    {
        return this.service.findMedecinsByDateDeNaissanceAfterDelete(date);
    }

    @GetMapping(path = "/find-all-by-prenom-or-nom-after-delete")
    public List<MedecinResDTO> findMedecinsByPrenomOrNomAfterDelete(@RequestParam String prenom,@RequestParam String nom)
    {
        return this.service.findMedecinsByPrenomOrNomAfterDelete(prenom, nom);
    }

    @GetMapping(path = "/find-password-by-email")
    public String findPasswordByEmail(@RequestParam String email) {
        return this.service.findPasswordByEmail(email);
    }
//------------------------------------------------------------------------------------------------------------------save

    @PostMapping(path = "/save")
    public void saveMedecin(@RequestBody MedecinReqDTO user)
    {
        this.service.saveMedecin(user);
    }

    @PutMapping(path = "/update")
    public void updateMedecin(@RequestBody MedecinReqDTO user)
    {
        this.service.saveMedecin(user);
    }

//----------------------------------------------------------------------------------------------------------------delete

    @DeleteMapping(path = "/delete-by-id/{id}")
    public void deleteMedecinById(@PathVariable(name = "id")  Integer id)
    {
        this.service.deleteMedecinById(id);
    }

    @DeleteMapping(path = "/delete")
    public void deleteMedecin(@RequestBody MedecinReqDTO user)
    {
        this.service.deleteMedecin(user);
    }
}
