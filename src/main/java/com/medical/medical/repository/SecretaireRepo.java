package com.medical.medical.repository;

import com.medical.medical.models.entity.Patient;
import com.medical.medical.models.entity.Secretaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository

public interface SecretaireRepo extends JpaRepository<Secretaire,Integer> {

    List<Secretaire> findSecretairesByIdMedecin(Integer id);
    Optional<Secretaire> findSecretaireByEmail(String email);
    Optional<Secretaire> findSecretaireByTel(String tel);

    List<Secretaire> findSecretairesByDateDeNaissance(LocalDate dateDeNaissance);

    List<Secretaire> findSecretairesByPrenomOrNom(String prenom,String nom);

    @Query("SELECT e.password FROM Secretaire e WHERE e.email = :email")
    Optional<String> findPasswordByEmail(@Param("email") String email);
}
