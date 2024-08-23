package com.medical.medical.repository;

import com.medical.medical.models.entity.DossierMedical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository

public interface DossierMedicalRepo extends JpaRepository<DossierMedical,Integer> {


    List<DossierMedical> findDossierMedicalByIdPatient(Integer integer);
}
