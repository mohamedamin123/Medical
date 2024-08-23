package com.medical.medical.services.impl;

import com.medical.medical.models.dto.req.DossierMedicalReqDTO;
import com.medical.medical.models.dto.res.DossierMedicalResDTO;
import com.medical.medical.models.entity.DossierMedical;
import com.medical.medical.models.mapper.DossierMedicalMapper;
import com.medical.medical.repository.DossierMedicalRepo;
import com.medical.medical.services.interf.DossierMedicalService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class DossierMedicalServiceImpl implements DossierMedicalService {

    private final DossierMedicalMapper mapper;

    private final DossierMedicalRepo repository;

//------------------------------------------------------------------------------------------------------------------save

    @Override
    public DossierMedicalResDTO saveDossierMedical(DossierMedicalReqDTO req) {
        DossierMedical emp=mapper.toEntity(req);
        repository.save(emp);
        return mapper.toRespDTO(emp);
    }

    @Override
    public DossierMedicalResDTO updateDossierMedical(DossierMedicalReqDTO req) {
        DossierMedical emp=mapper.toEntity(req);
        emp.setCreatedAt(this.repository.findById(req.getIdDossierMedical()).get().getCreatedAt());
        DossierMedical saved= repository.save(emp);
        saved.setDeletedAt(null);
        return mapper.toRespDTO(saved);
    }
//------------------------------------------------------------------------------------------------------------------find

    @Override
    public List<DossierMedicalResDTO> findAllDossierMedical() {

        List<DossierMedical> users = this.repository.findAll();
        return mapper.toAllRespDTO(users);
    }


    @Override
    public List<DossierMedicalResDTO> findDossierMedicalByIdPatient(Integer id) {

        List<DossierMedical> users = this.repository.findDossierMedicalByIdPatient(id);
        return mapper.toAllRespDTO(users);
    }

    @Override
    public List<DossierMedicalResDTO> findDossierMedicalByIdPatientAfterDelete(Integer id) {
        // Récupérer la liste des dossiers médicaux pour le patient spécifié
        List<DossierMedical> dossiersMedicals = this.repository.findDossierMedicalByIdPatient(id);

        // Filtrer les dossiers médicaux pour exclure ceux qui ont été supprimés (deletedAt != null)
        List<DossierMedical> filteredDossiersMedicals = dossiersMedicals.stream()
                .filter(dossierMedical -> dossierMedical.getDeletedAt() == null)
                .collect(Collectors.toList());

        // Convertir la liste filtrée en une liste de DTOs de réponse
        return mapper.toAllRespDTO(filteredDossiersMedicals);
    }


    @Override
    public Optional<DossierMedicalResDTO> findDossierMedicalById(int id) {
        Optional<DossierMedical> optionalDossierMedical = this.repository.findById(id);
        if (optionalDossierMedical.isPresent()) {
            DossierMedicalResDTO DossierMedicalResDTO = mapper.toRespDTO(optionalDossierMedical.get());
            return Optional.of(DossierMedicalResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<DossierMedicalResDTO> findAllDossierMedicalAfterDelete() {
        List<DossierMedical> dossiersMedicals = this.repository.findAll();
        List<DossierMedical> filteredDossiersMedicals = dossiersMedicals.stream()
                .filter(dossierMedical -> dossierMedical.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredDossiersMedicals);
    }

    @Override
    public Optional<DossierMedicalResDTO> findDossierMedicalByIdAfterDelete(int id) {
        Optional<DossierMedical> optionalDossierMedical = this.repository.findById(id);
        if (optionalDossierMedical.isPresent() && optionalDossierMedical.get().getDeletedAt() == null) {
            DossierMedicalResDTO dossierMedicalResDTO = mapper.toRespDTO(optionalDossierMedical.get());
            return Optional.of(dossierMedicalResDTO);
        } else {
            return Optional.empty();
        }
    }

//----------------------------------------------------------------------------------------------------------------delete


    @Override
    public void deleteDossierMedical(DossierMedicalReqDTO req) {
        DossierMedical emp=this.repository.findById(req.getIdDossierMedical()).get();
        emp.setDeletedAt(LocalDateTime.now());
        repository.save(emp);
    }

    @Override
    public void deleteDossierMedicalById(int id) {
        DossierMedical emp=this.repository.findById(id).get();
        emp.setDeletedAt(LocalDateTime.now());
        repository.save(emp);
    }
}
