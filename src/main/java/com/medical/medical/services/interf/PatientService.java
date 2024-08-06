package com.medical.medical.services.interf;

import com.medical.medical.models.dto.req.ConsultationReqDTO;
import com.medical.medical.models.dto.req.PatientReqDTO;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.entity.Patient;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service

public interface PatientService {
    PatientResDTO savePatient(PatientReqDTO req);

    PatientResDTO updatePatient(PatientReqDTO req);

    List<PatientResDTO> findAllPatient();


     Optional<PatientResDTO> findPatientById(int id) ;

    List<PatientResDTO> findPatientsByDateDeNaissance(LocalDate dateDeNaissance);

    List<PatientResDTO> findPatientsByPrenomOrNom(String prenom,String nom);

    Optional<PatientResDTO> findPatientByEmail(String email);
    Optional<PatientResDTO> findPatientByTel(String tel);


    List<PatientResDTO> findAllPatientAfterDelete();


    Optional<PatientResDTO> findPatientByIdAfterDelete(int id) ;

    List<PatientResDTO> findPatientsByDateDeNaissanceAfterDelete(LocalDate dateDeNaissance);

    List<PatientResDTO> findPatientsByPrenomOrNomAfterDelete(String prenom,String nom);

    Optional<PatientResDTO> findPatientByEmailAfterDelete(String email);
    Optional<PatientResDTO> findPatientByTelAfterDelete(String tel);

    void deletePatient(PatientReqDTO req);

    void deletePatientById(int id);
}