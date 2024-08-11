package com.medical.medical.services.interf;

import com.medical.medical.models.dto.req.ConsultationReqDTO;
import com.medical.medical.models.dto.req.MedecinReqDTO;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.entity.Medecin;
import com.medical.medical.security.LoginViewModel;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service

public interface MedecinService {
    MedecinResDTO saveMedecin(MedecinReqDTO req);

    MedecinResDTO updateMedecin(MedecinReqDTO req);

    List<MedecinResDTO> findAllMedecin();


     Optional<MedecinResDTO> findMedecinById(int id) ;

    Optional<MedecinResDTO> findMedecinByEmail(String email);
    Optional<MedecinResDTO> findMedecinByTel(String tel);

    List<MedecinResDTO> findMedecinsByDateDeNaissance(LocalDate dateDeNaissance);

    List<MedecinResDTO> findMedecinsByPrenomOrNom(String prenom,String nom);


    List<MedecinResDTO> findAllMedecinAfterDelete();


    Optional<MedecinResDTO> findMedecinByIdAfterDelete(int id) ;

    Optional<MedecinResDTO> findMedecinByEmailAfterDelete(String email);
    Optional<MedecinResDTO> findMedecinByTelAfterDelete(String tel);

    List<MedecinResDTO> findMedecinsByDateDeNaissanceAfterDelete(LocalDate dateDeNaissance);

    List<MedecinResDTO> findMedecinsByPrenomOrNomAfterDelete(String prenom,String nom);

    void deleteMedecin(MedecinReqDTO req);

    String findPasswordByEmail(String email);


    void deleteMedecinById(int id);





}