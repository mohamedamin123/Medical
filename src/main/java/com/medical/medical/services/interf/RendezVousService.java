package com.medical.medical.services.interf;

import com.medical.medical.models.dto.req.PatientReqDTO;
import com.medical.medical.models.dto.req.RendezVousReqDTO;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.RendezVousResDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public interface RendezVousService {
    RendezVousResDTO saveRendezVous(RendezVousReqDTO req);

    RendezVousResDTO updateRendezVous(RendezVousReqDTO req);

    List<RendezVousResDTO> findAllRendezVous();


     Optional<RendezVousResDTO> findRendezVousById(int id) ;


    List<RendezVousResDTO> findAllRendezVousAfterDelete();

    Optional<RendezVousResDTO> findRendezVousByIdAfterDelete(int id);

    void deleteRendezVous(RendezVousReqDTO req);

    void deleteRendezVousById(int id);
}