package com.medical.medical.repository;

import com.medical.medical.models.entity.Medecin;
import com.medical.medical.models.entity.Secretaire;
import com.medical.medical.security.LoginViewModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository

public interface MedecinRepo extends JpaRepository<Medecin,Integer> {
    Optional<Medecin> findMedecinByEmail(String email);
    Optional<Medecin> findMedecinByTel(String tel);

    List<Medecin> findMedecinsByDateDeNaissance(LocalDate dateDeNaissance);

    List<Medecin> findMedecinsByPrenomOrNom(String prenom,String nom);

    @Query("SELECT e.password FROM Medecin e WHERE e.email = :email")
    Optional<String> findPasswordByEmail(@Param("email") String email);


}
