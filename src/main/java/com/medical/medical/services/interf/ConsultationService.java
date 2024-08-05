package com.medical.medical.services.interf;

import com.medical.medical.models.dto.req.ConsultationReqDTO;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public interface ConsultationService {
    ConsultationResDTO saveConsultation(ConsultationReqDTO req);

    ConsultationResDTO updateEmployees(ConsultationReqDTO req);

    List<ConsultationResDTO> findAllConsultation();


     Optional<ConsultationResDTO> findConsultationById(int id) ;

    void deleteConsultation(ConsultationReqDTO req);

    void deleteConsultationById(int id);
}