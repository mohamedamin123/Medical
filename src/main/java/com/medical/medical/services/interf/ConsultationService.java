package com.medical.medical.services.interf;

import com.medical.medical.models.dto.req.ConsultationReqDTO;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.models.entity.Consultation;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service

public interface ConsultationService {
    ConsultationResDTO saveConsultation(ConsultationReqDTO req);

    ConsultationResDTO updateConsultation(ConsultationReqDTO req);

    List<ConsultationResDTO> findAllConsultation();

    List<ConsultationResDTO> findConsultationsByIdMedecinAndJour(Integer id, LocalDate jour);

     Optional<ConsultationResDTO> findConsultationById(int id) ;

     List<ConsultationResDTO> findAllConsultationAfterDelete();

     Optional<ConsultationResDTO> findConsultationByIdAfterDelete(int id);

    void deleteConsultation(ConsultationReqDTO req);

    void deleteConsultationById(int id);
}