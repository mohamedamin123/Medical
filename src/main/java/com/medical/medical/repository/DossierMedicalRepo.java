package com.medical.medical.repository;

import com.medical.medical.models.entity.DossierMedical;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface DossierMedicalRepo extends JpaRepository<DossierMedical,Integer> {
}
