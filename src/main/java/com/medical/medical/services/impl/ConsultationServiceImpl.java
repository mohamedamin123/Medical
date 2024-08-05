package com.medical.medical.services.impl;

import com.medical.medical.models.dto.req.ConsultationReqDTO;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.models.entity.Consultation;
import com.medical.medical.models.mapper.ConsultationMapper;
import com.medical.medical.repository.ConsultationRepo;
import com.medical.medical.services.interf.ConsultationService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Primary
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationMapper mapper;

    private final ConsultationRepo repository;


    @Override
    public ConsultationResDTO saveConsultation(ConsultationReqDTO req) {
        Consultation emp=mapper.toEntity(req);
        repository.save(emp);
        return mapper.toRespDTO(emp);
    }

    @Override
    public ConsultationResDTO updateEmployees(ConsultationReqDTO req) {
        Consultation emp=mapper.toEntity(req);
        emp.setCreatedAt(this.repository.findById(req.getIdConsultation()).get().getCreatedAt());
        Consultation saved= repository.save(emp);
        return mapper.toRespDTO(saved);
    }

    @Override
    public List<ConsultationResDTO> findAllConsultation() {

        List<Consultation> users = this.repository.findAll();
        return mapper.toAllRespDTO(users);
    }


    @Override
    public Optional<ConsultationResDTO> findConsultationById(int id) {
        Optional<Consultation> optionalConsultation = this.repository.findById(id);
        if (optionalConsultation.isPresent()) {
            ConsultationResDTO consultationResDTO = mapper.toRespDTO(optionalConsultation.get());
            return Optional.of(consultationResDTO);
        } else {
            return Optional.empty();
        }
    }


    @Override
    public void deleteConsultation(ConsultationReqDTO req) {
        Consultation emp=this.repository.findById(req.getIdConsultation()).get();
        emp.setDeletedAt(LocalDateTime.now());
        repository.save(emp);
    }

    @Override
    public void deleteConsultationById(int id) {
        Consultation emp=this.repository.findById(id).get();
        emp.setDeletedAt(LocalDateTime.now());
        repository.save(emp);
    }
}
