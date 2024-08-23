package com.medical.medical.services.impl;

import com.medical.medical.models.dto.req.PatientReqDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.entity.Medecin;
import com.medical.medical.models.entity.Patient;
import com.medical.medical.models.entity.Patient;
import com.medical.medical.models.mapper.PatientMapper;
import com.medical.medical.repository.PatientRepo;
import com.medical.medical.services.interf.PatientService;
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
public class PatientServiceImpl implements PatientService {

    private final PatientMapper mapper;

    private final PatientRepo repository;

//------------------------------------------------------------------------------------------------------------------save

    @Override
    public PatientResDTO savePatient(PatientReqDTO req) {
        Patient emp=mapper.toEntity(req);
        repository.save(emp);
        return mapper.toRespDTO(emp);
    }

    @Override
    public PatientResDTO updatePatient(PatientReqDTO req) {
        System.out.println("cc "+req.getIdPatient());
        // Convertir DTO en entité pour obtenir les nouvelles données
        Patient updatedMedecin = mapper.toEntity(req);
        System.out.println(updatedMedecin);

        // Trouver le médecin existant par son email
        Optional<Patient> existingMedecinOptional = this.repository.findPatientByIdPatient(req.getIdPatient());

        if (existingMedecinOptional.isPresent()) {

            // Obtenir le médecin existant
            Patient existingMedecin = existingMedecinOptional.get();

            // Mettre à jour les champs pertinents avec les nouvelles valeurs
            existingMedecin.setNom(updatedMedecin.getNom());
            existingMedecin.setPrenom(updatedMedecin.getPrenom());
            existingMedecin.setTel(updatedMedecin.getTel());
            existingMedecin.setEmail(updatedMedecin.getEmail());
            existingMedecin.setDateDeNaissance(updatedMedecin.getDateDeNaissance());
            existingMedecin.setCIN(updatedMedecin.getCIN());
            existingMedecin.setIdUnique(updatedMedecin.getIdUnique());
            existingMedecin.setAdresse(updatedMedecin.getAdresse());
            existingMedecin.setRemboursement(updatedMedecin.getRemboursement());
            existingMedecin.setNote(updatedMedecin.getNote());
            existingMedecin.setSexe(updatedMedecin.getSexe());

            existingMedecin.setUpdatedAt(LocalDateTime.now()); // Assurez-vous que vous avez un champ 'updatedAt' dans votre entité

            existingMedecin.setDeletedAt(null);

            Patient savedMedecin = repository.save(existingMedecin);

            return mapper.toRespDTO(savedMedecin);
        }

        return null;
    }
//------------------------------------------------------------------------------------------------------------------find

    @Override
    public List<PatientResDTO> findAllPatient() {

        List<Patient> users = this.repository.findAll();
        return mapper.toAllRespDTO(users);
    }


    @Override
    public List<PatientResDTO> findPatientsByIdMedecin(Integer id) {
        List<Patient> users = this.repository.findPatientsByIdMedecin(id);
        return mapper.toAllRespDTO(users);    }


    @Override
    public Optional<PatientResDTO> findPatientById(int id) {
        Optional<Patient> optionalPatient = this.repository.findById(id);
        if (optionalPatient.isPresent()) {
            PatientResDTO PatientResDTO = mapper.toRespDTO(optionalPatient.get());
            return Optional.of(PatientResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PatientResDTO> findPatientByEmail(String email) {
        Optional<Patient> optionalPatient = this.repository.findPatientByEmail(email);
        if (optionalPatient.isPresent()) {
            PatientResDTO PatientResDTO = mapper.toRespDTO(optionalPatient.get());
            return Optional.of(PatientResDTO);
        } else {
            return Optional.empty();
        }    }

    @Override
    public Optional<PatientResDTO> findPatientByTel(String tel) {
        Optional<Patient> optionalPatient = this.repository.findPatientByTel(tel);
        if (optionalPatient.isPresent()) {
            PatientResDTO PatientResDTO = mapper.toRespDTO(optionalPatient.get());
            return Optional.of(PatientResDTO);
        } else {
            return Optional.empty();
        }    }

    @Override
    public List<PatientResDTO> findPatientsByDateDeNaissance(LocalDate dateDeNaissance) {
        List<Patient> users = this.repository.findPatientsByDateDeNaissance(dateDeNaissance);
        return mapper.toAllRespDTO(users);    }

    @Override
    public List<PatientResDTO> findPatientsByPrenomOrNom(String prenom, String nom) {
        List<Patient> users = this.repository.findPatientsByPrenomOrNom(prenom, nom);
        return mapper.toAllRespDTO(users);
    }

    @Override
    public List<PatientResDTO> findAllPatientAfterDelete() {
        List<Patient> patients = this.repository.findAll();
        List<Patient> filteredPatients = patients.stream()
                .filter(patient -> patient.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredPatients);
    }

    @Override
    public Optional<PatientResDTO> findPatientByIdAfterDelete(int id) {
        Optional<Patient> optionalPatient = this.repository.findById(id);
        if (optionalPatient.isPresent() && optionalPatient.get().getDeletedAt() == null) {
            PatientResDTO patientResDTO = mapper.toRespDTO(optionalPatient.get());
            return Optional.of(patientResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PatientResDTO> findPatientByEmailAfterDelete(String email) {
        Optional<Patient> optionalPatient = this.repository.findPatientByEmail(email);
        if (optionalPatient.isPresent() && optionalPatient.get().getDeletedAt() == null) {
            PatientResDTO patientResDTO = mapper.toRespDTO(optionalPatient.get());
            return Optional.of(patientResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public Optional<PatientResDTO> findPatientByTelAfterDelete(String tel) {
        Optional<Patient> optionalPatient = this.repository.findPatientByTel(tel);
        if (optionalPatient.isPresent() && optionalPatient.get().getDeletedAt() == null) {
            PatientResDTO patientResDTO = mapper.toRespDTO(optionalPatient.get());
            return Optional.of(patientResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<PatientResDTO> findPatientsByDateDeNaissanceAfterDelete(LocalDate dateDeNaissance) {
        List<Patient> patients = this.repository.findPatientsByDateDeNaissance(dateDeNaissance);
        List<Patient> filteredPatients = patients.stream()
                .filter(patient -> patient.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredPatients);
    }

    @Override
    public List<PatientResDTO> findPatientsByPrenomOrNomAfterDelete(String prenom, String nom) {
        List<Patient> patients = this.repository.findPatientsByPrenomOrNom(prenom, nom);
        List<Patient> filteredPatients = patients.stream()
                .filter(patient -> patient.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredPatients);
    }

//----------------------------------------------------------------------------------------------------------------delete

    @Override
    public void deletePatient(PatientReqDTO req) {
        Patient emp=this.repository.findById(req.getIdPatient()).get();
        emp.setDeletedAt(LocalDateTime.now());
        emp.setStatut(false);
        repository.save(emp);
    }

    @Override
    public void deletePatientById(int id) {
        Patient emp=this.repository.findById(id).get();
        emp.setDeletedAt(LocalDateTime.now());
        emp.setStatut(false);
        repository.save(emp);
    }
}
