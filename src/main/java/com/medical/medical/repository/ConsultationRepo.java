package com.medical.medical.repository;

import com.medical.medical.models.entity.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConsultationRepo extends JpaRepository<Consultation,Integer> {


    List<Consultation> findConsultationsByIdMedecinAndJour(Integer id, LocalDate jour);

    List<Consultation> findConsultationsByIdMedecin(Integer id);


}
