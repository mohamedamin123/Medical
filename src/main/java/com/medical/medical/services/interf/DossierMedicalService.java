package com.medical.medical.services.interf;

import com.medical.medical.models.dto.req.DossierMedicalReqDTO;
import com.medical.medical.models.dto.req.NotificationReqDTO;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.models.dto.res.DossierMedicalResDTO;
import com.medical.medical.models.dto.res.NotificationResDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public interface DossierMedicalService {
    DossierMedicalResDTO saveDossierMedical(DossierMedicalReqDTO req);

    DossierMedicalResDTO updateDossierMedical(DossierMedicalReqDTO req);

    List<DossierMedicalResDTO> findAllDossierMedical();


     Optional<DossierMedicalResDTO> findDossierMedicalById(int id) ;

    List<DossierMedicalResDTO> findAllDossierMedicalAfterDelete();

    Optional<DossierMedicalResDTO> findDossierMedicalByIdAfterDelete(int id);

    void deleteDossierMedical(DossierMedicalReqDTO req);

    void deleteDossierMedicalById(int id);
}