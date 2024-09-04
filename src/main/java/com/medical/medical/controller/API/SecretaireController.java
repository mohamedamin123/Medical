package com.medical.medical.controller.API;

import com.medical.medical.models.dto.req.SecretaireReqDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.models.entity.Secretaire;
import com.medical.medical.services.interf.SecretaireService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/secretaires/secretaire")
@RequiredArgsConstructor
@Slf4j
public class SecretaireController {

    private final SecretaireService service;

//------------------------------------------------------------------------------------------------------------------find
    @GetMapping(path = "/find-all")
    public List<SecretaireResDTO> findAllSecretaire()
    {
        return this.service.findAllSecretaire();
    }

    @GetMapping(path = "/find-by-id/{id}")
    public Optional<SecretaireResDTO> findSecretaireById(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de Secretaire est : {}", id);

        return this.service.findSecretaireById(id);
    }

    @GetMapping(path = "/find-by-email/{email}")
    public Optional<SecretaireResDTO> findSecretaireByEmail(@PathVariable(name = "email")  String email)
    {
        log.info("email de Secretaire est : {}", email);

        return this.service.findSecretaireByEmail(email);
    }

    @GetMapping(path = "/find-by-tel/{tel}")
    public Optional<SecretaireResDTO> findSecretaireByTel(@PathVariable(name = "tel")  String tel)
    {
        log.info("id de Secretaire est : {}", tel);
        log.info("tel de Secretaire est : {}", tel);

        return this.service.findSecretaireByTel(tel);
    }

    @GetMapping(path = "/find-all-by-date-de-naissance")
    public List<SecretaireResDTO> findSecretairesByDateDeNaissance(@RequestParam LocalDate date)
    {
        return this.service.findSecretairesByDateDeNaissance(date);
    }

    @GetMapping(path = "/find-all-by-medecin-id")
    public List<SecretaireResDTO> findSecretairesByIdMedecin(@RequestParam  Integer id) {
        return this.service.findSecretairesByIdMedecin(id);

    }
    @GetMapping(path = "/find-all-by-prenom-or-nom")
    public List<SecretaireResDTO> findSecretairesByPrenomOrNom(@RequestParam String prenom,@RequestParam String nom)
    {
        return this.service.findSecretairesByPrenomOrNom(prenom, nom);
    }

    @GetMapping(path = "/find-all-after-delete")
    public List<SecretaireResDTO> findAllSecretaireAfterDelete()
    {
        return this.service.findAllSecretaireAfterDelete();
    }

    @GetMapping(path = "/after-delete/find-by-id/{id}")
    public Optional<SecretaireResDTO> findSecretaireByIdAfterDelete(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de Secretaire est : {}", id);

        return this.service.findSecretaireByIdAfterDelete(id);
    }

    @GetMapping(path = "/after-delete/find-by-email/{email}")
    public Optional<SecretaireResDTO> findSecretaireByEmailAfterDelete(@PathVariable(name = "email")  String email)
    {
        log.info("email de Secretaire est : {}", email);

        return this.service.findSecretaireByEmailAfterDelete(email);
    }

    @GetMapping(path = "/after-delete/find-by-tel/{tel}")
    public Optional<SecretaireResDTO> findSecretaireByTelAfterDelete(@PathVariable(name = "tel")  String tel)
    {
        log.info("id de Secretaire est : {}", tel);
        log.info("tel de Secretaire est : {}", tel);

        return this.service.findSecretaireByTelAfterDelete(tel);
    }

    @GetMapping(path = "/find-all-by-date-de-naissance-after-delete")
    public List<SecretaireResDTO> findSecretairesByDateDeNaissanceAfterDelete(@RequestParam LocalDate date)
    {
        return this.service.findSecretairesByDateDeNaissanceAfterDelete(date);
    }

    @GetMapping(path = "/find-all-by-prenom-or-nom-after-delete")
    public List<SecretaireResDTO> findSecretairesByPrenomOrNomAfterDelete(@RequestParam String prenom,@RequestParam String nom)
    {
        return this.service.findSecretairesByPrenomOrNomAfterDelete(prenom, nom);
    }

    @GetMapping(path = "/find-password-by-email")
    public Optional<String> findPasswordByEmail(@RequestParam String email) {
        return this.service.findPasswordByEmail(email);
    }
//------------------------------------------------------------------------------------------------------------------save

    @PostMapping(path = "/save")
    public void saveSecretaire(@RequestBody SecretaireReqDTO user)
    {
        this.service.saveSecretaire(user);
    }

    @PutMapping(path = "/update")
    public void updateSecretaire(@RequestBody SecretaireReqDTO user)
    {
        this.service.updateSecretaire(user);
    }

//----------------------------------------------------------------------------------------------------------------delete

    @DeleteMapping(path = "/delete-by-id/{id}")
    public void deleteSecretaireById(@PathVariable(name = "id")  Integer id)
    {
        this.service.deleteSecretaireById(id);
    }

    @DeleteMapping(path = "/delete")
    public void deleteSecretaire(@RequestBody SecretaireReqDTO user)
    {
        this.service.deleteSecretaire(user);
    }
}
