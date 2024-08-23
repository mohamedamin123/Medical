package com.medical.medical.repository;

import com.medical.medical.models.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository

public interface PatientRepo extends JpaRepository<Patient,Integer> {

    List<Patient> findPatientsByDateDeNaissance(LocalDate dateDeNaissance);

    List<Patient> findPatientsByPrenomOrNom(String prenom,String nom);

    List<Patient> findPatientsByIdMedecin(Integer id);

    Optional<Patient> findPatientByIdPatient(Integer id);


    Optional<Patient> findPatientByEmail(String email);
    Optional<Patient> findPatientByTel(String tel);


}
