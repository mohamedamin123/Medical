package com.medical.medical.repository;

import com.medical.medical.models.entity.RendezVous;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface RendezVousRepo extends JpaRepository<RendezVous,Integer> {
}
