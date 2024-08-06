package com.medical.medical.services.impl;

import com.medical.medical.models.dto.req.SecretaireReqDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.models.entity.Secretaire;
import com.medical.medical.models.entity.Secretaire;
import com.medical.medical.models.mapper.SecretaireMapper;
import com.medical.medical.repository.SecretaireRepo;
import com.medical.medical.services.interf.SecretaireService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class SecretaireServiceImpl implements SecretaireService {

    private final SecretaireMapper mapper;

    private final SecretaireRepo repository;

//------------------------------------------------------------------------------------------------------------------save

    @Override
    public SecretaireResDTO saveSecretaire(SecretaireReqDTO req) {
        Secretaire emp=mapper.toEntity(req);
        repository.save(emp);
        return mapper.toRespDTO(emp);
    }

    @Override
    public SecretaireResDTO updateSecretaire(SecretaireReqDTO req) {
        Secretaire emp=mapper.toEntity(req);
        emp.setCreatedAt(this.repository.findById(req.getIdSecretaire()).get().getCreatedAt());
        Secretaire saved= repository.save(emp);
        return mapper.toRespDTO(saved);
    }
//------------------------------------------------------------------------------------------------------------------find

    @Override
    public List<SecretaireResDTO> findAllSecretaire() {

        List<Secretaire> users = this.repository.findAll();
        return mapper.toAllRespDTO(users);
    }


    @Override
    public Optional<SecretaireResDTO> findSecretaireById(int id) {
        Optional<Secretaire> optionalSecretaire = this.repository.findById(id);
        if (optionalSecretaire.isPresent()) {
            SecretaireResDTO SecretaireResDTO = mapper.toRespDTO(optionalSecretaire.get());
            return Optional.of(SecretaireResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<SecretaireResDTO> findSecretaireByEmail(String email) {
        Optional<Secretaire> optionalSecretaire = this.repository.findSecretaireByEmail(email);
        if (optionalSecretaire.isPresent()) {
            SecretaireResDTO SecretaireResDTO = mapper.toRespDTO(optionalSecretaire.get());
            return Optional.of(SecretaireResDTO);
        } else {
            return Optional.empty();
        }    }

    @Override
    public Optional<SecretaireResDTO> findSecretaireByTel(String tel) {
        Optional<Secretaire> optionalSecretaire = this.repository.findSecretaireByTel(tel);
        if (optionalSecretaire.isPresent()) {
            SecretaireResDTO SecretaireResDTO = mapper.toRespDTO(optionalSecretaire.get());
            return Optional.of(SecretaireResDTO);
        } else {
            return Optional.empty();
        }    }

    @Override
    public List<SecretaireResDTO> findSecretairesByDateDeNaissance(LocalDate dateDeNaissance) {
        List<Secretaire> users = this.repository.findSecretairesByDateDeNaissance(dateDeNaissance);
        return mapper.toAllRespDTO(users);    }

    @Override
    public List<SecretaireResDTO> findSecretairesByPrenomOrNom(String prenom, String nom) {
        List<Secretaire> users = this.repository.findSecretairesByPrenomOrNom(prenom, nom);
        return mapper.toAllRespDTO(users);
    }
    @Override
    public List<SecretaireResDTO> findAllSecretaireAfterDelete() {
        List<Secretaire> secretaires = this.repository.findAll();
        List<Secretaire> filteredSecretaires = secretaires.stream()
                .filter(secretaire -> secretaire.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredSecretaires);
    }

    @Override
    public Optional<SecretaireResDTO> findSecretaireByIdAfterDelete(int id) {
        Optional<Secretaire> optionalSecretaire = this.repository.findById(id);
        if (optionalSecretaire.isPresent() && optionalSecretaire.get().getDeletedAt() == null) {
            SecretaireResDTO secretaireResDTO = mapper.toRespDTO(optionalSecretaire.get());
            return Optional.of(secretaireResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<SecretaireResDTO> findSecretaireByEmailAfterDelete(String email) {
        Optional<Secretaire> optionalSecretaire = this.repository.findSecretaireByEmail(email);
        if (optionalSecretaire.isPresent() && optionalSecretaire.get().getDeletedAt() == null) {
            SecretaireResDTO secretaireResDTO = mapper.toRespDTO(optionalSecretaire.get());
            return Optional.of(secretaireResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<SecretaireResDTO> findSecretaireByTelAfterDelete(String tel) {
        Optional<Secretaire> optionalSecretaire = this.repository.findSecretaireByTel(tel);
        if (optionalSecretaire.isPresent() && optionalSecretaire.get().getDeletedAt() == null) {
            SecretaireResDTO secretaireResDTO = mapper.toRespDTO(optionalSecretaire.get());
            return Optional.of(secretaireResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<SecretaireResDTO> findSecretairesByDateDeNaissanceAfterDelete(LocalDate dateDeNaissance) {
        List<Secretaire> secretaires = this.repository.findSecretairesByDateDeNaissance(dateDeNaissance);
        List<Secretaire> filteredSecretaires = secretaires.stream()
                .filter(secretaire -> secretaire.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredSecretaires);
    }

    @Override
    public List<SecretaireResDTO> findSecretairesByPrenomOrNomAfterDelete(String prenom, String nom) {
        List<Secretaire> secretaires = this.repository.findSecretairesByPrenomOrNom(prenom, nom);
        List<Secretaire> filteredSecretaires = secretaires.stream()
                .filter(secretaire -> secretaire.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredSecretaires);
    }

//----------------------------------------------------------------------------------------------------------------delete

    @Override
    public void deleteSecretaire(SecretaireReqDTO req) {
        Secretaire emp=this.repository.findById(req.getIdSecretaire()).get();
        emp.setDeletedAt(LocalDateTime.now());
        emp.setStatut(false);
        repository.save(emp);
    }

    @Override
    public void deleteSecretaireById(int id) {
        Secretaire emp=this.repository.findById(id).get();
        emp.setDeletedAt(LocalDateTime.now());
        emp.setStatut(false);
        repository.save(emp);
    }
}
