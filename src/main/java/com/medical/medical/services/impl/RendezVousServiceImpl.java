package com.medical.medical.services.impl;

import com.medical.medical.models.dto.req.RendezVousReqDTO;
import com.medical.medical.models.dto.res.RendezVousResDTO;
import com.medical.medical.models.entity.RendezVous;
import com.medical.medical.models.mapper.RendezVousMapper;
import com.medical.medical.repository.RendezVousRepo;
import com.medical.medical.services.interf.RendezVousService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class RendezVousServiceImpl implements RendezVousService {

    private final RendezVousMapper mapper;

    private final RendezVousRepo repository;

//------------------------------------------------------------------------------------------------------------------save

    @Override
    public RendezVousResDTO saveRendezVous(RendezVousReqDTO req) {
        RendezVous emp=mapper.toEntity(req);
        repository.save(emp);
        return mapper.toRespDTO(emp);
    }

    @Override
    public RendezVousResDTO updateRendezVous(RendezVousReqDTO req) {
        RendezVous emp=mapper.toEntity(req);
        emp.setCreatedAt(this.repository.findById(req.getIdRendezVous()).get().getCreatedAt());
        RendezVous saved= repository.save(emp);
        return mapper.toRespDTO(saved);
    }
//------------------------------------------------------------------------------------------------------------------find

    @Override
    public List<RendezVousResDTO> findAllRendezVous() {

        List<RendezVous> users = this.repository.findAll();
        return mapper.toAllRespDTO(users);
    }


    @Override
    public Optional<RendezVousResDTO> findRendezVousById(int id) {
        Optional<RendezVous> optionalRendezVous = this.repository.findById(id);
        if (optionalRendezVous.isPresent()) {
            RendezVousResDTO RendezVousResDTO = mapper.toRespDTO(optionalRendezVous.get());
            return Optional.of(RendezVousResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<RendezVousResDTO> findAllRendezVousAfterDelete() {
        List<RendezVous> rendezVousList = this.repository.findAll();
        List<RendezVous> filteredRendezVousList = rendezVousList.stream()
                .filter(rendezVous -> rendezVous.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredRendezVousList);
    }

    @Override
    public Optional<RendezVousResDTO> findRendezVousByIdAfterDelete(int id) {
        Optional<RendezVous> optionalRendezVous = this.repository.findById(id);
        if (optionalRendezVous.isPresent() && optionalRendezVous.get().getDeletedAt() == null) {
            RendezVousResDTO rendezVousResDTO = mapper.toRespDTO(optionalRendezVous.get());
            return Optional.of(rendezVousResDTO);
        } else {
            return Optional.empty();
        }
    }


//----------------------------------------------------------------------------------------------------------------delete

    @Override
    public void deleteRendezVous(RendezVousReqDTO req) {
        RendezVous emp=this.repository.findById(req.getIdRendezVous()).get();
        emp.setDeletedAt(LocalDateTime.now());
        repository.save(emp);
    }

    @Override
    public void deleteRendezVousById(int id) {
        RendezVous emp=this.repository.findById(id).get();
        emp.setDeletedAt(LocalDateTime.now());
        repository.save(emp);
    }
}
