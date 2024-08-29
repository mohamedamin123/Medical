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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class ConsultationServiceImpl implements ConsultationService {

    private final ConsultationMapper mapper;

    private final ConsultationRepo repository;

//------------------------------------------------------------------------------------------------------------------save

    @Override
    public ConsultationResDTO saveConsultation(ConsultationReqDTO req) {
        System.out.println(req);
        Consultation emp=mapper.toEntity(req);
        System.out.println(emp);

        repository.save(emp);
        return mapper.toRespDTO(emp);
    }

    @Override
    public ConsultationResDTO updateConsultation(ConsultationReqDTO req) {
        Consultation emp=mapper.toEntity(req);
        emp.setCreatedAt(this.repository.findById(req.getIdConsultation()).get().getCreatedAt());
        Consultation saved= repository.save(emp);
        saved.setDeletedAt(null);
        return mapper.toRespDTO(saved);
    }
//------------------------------------------------------------------------------------------------------------------find

    @Override
    public List<ConsultationResDTO> findAllConsultation() {

        List<Consultation> users = this.repository.findAll();
        return mapper.toAllRespDTO(users);
    }

    @Override
    public List<ConsultationResDTO> findConsultationsByIdMedecinAndJour(Integer id, LocalDate jour) {

        List<Consultation> consultations = this.repository.findConsultationsByIdMedecinAndJour(id,jour);
        List<Consultation> filteredConsultations = consultations.stream()
                .filter(consultation -> consultation.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredConsultations);
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
    public List<ConsultationResDTO> findAllConsultationAfterDelete() {
        List<Consultation> consultations = this.repository.findAll();
        List<Consultation> filteredConsultations = consultations.stream()
                .filter(consultation -> consultation.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredConsultations);
    }

    @Override
    public Optional<ConsultationResDTO> findConsultationByIdAfterDelete(int id) {
        Optional<Consultation> optionalConsultation = this.repository.findById(id);
        if (optionalConsultation.isPresent() && optionalConsultation.get().getDeletedAt() == null) {
            ConsultationResDTO consultationResDTO = mapper.toRespDTO(optionalConsultation.get());
            return Optional.of(consultationResDTO);
        } else {
            return Optional.empty();
        }
    }









//----------------------------------------------------------------------------------------------------------------delete


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
