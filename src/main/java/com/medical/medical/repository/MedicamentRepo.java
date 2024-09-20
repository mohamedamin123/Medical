package com.medical.medical.repository;

import com.medical.medical.models.entity.Medecin;
import com.medical.medical.models.entity.Medicament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MedicamentRepo extends JpaRepository<Medicament,Integer> {

    List<Medicament> findMedicamentsByIdPatient(Integer id);
}
