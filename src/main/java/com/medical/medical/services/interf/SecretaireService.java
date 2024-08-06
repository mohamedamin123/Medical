package com.medical.medical.services.interf;

import com.medical.medical.models.dto.req.PatientReqDTO;
import com.medical.medical.models.dto.req.SecretaireReqDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.models.entity.Secretaire;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service

public interface SecretaireService {
    SecretaireResDTO saveSecretaire(SecretaireReqDTO req);

    SecretaireResDTO updateSecretaire(SecretaireReqDTO req);

    List<SecretaireResDTO> findAllSecretaire();


     Optional<SecretaireResDTO> findSecretaireById(int id) ;

    Optional<SecretaireResDTO> findSecretaireByEmail(String email);
    Optional<SecretaireResDTO> findSecretaireByTel(String tel);

    List<SecretaireResDTO> findSecretairesByDateDeNaissance(LocalDate dateDeNaissance);

    List<SecretaireResDTO> findSecretairesByPrenomOrNom(String prenom,String nom);


    List<SecretaireResDTO> findAllSecretaireAfterDelete();


    Optional<SecretaireResDTO> findSecretaireByIdAfterDelete(int id) ;

    Optional<SecretaireResDTO> findSecretaireByEmailAfterDelete(String email);
    Optional<SecretaireResDTO> findSecretaireByTelAfterDelete(String tel);

    List<SecretaireResDTO> findSecretairesByDateDeNaissanceAfterDelete(LocalDate dateDeNaissance);

    List<SecretaireResDTO> findSecretairesByPrenomOrNomAfterDelete(String prenom,String nom);



    void deleteSecretaire(SecretaireReqDTO req);

    void deleteSecretaireById(int id);
}