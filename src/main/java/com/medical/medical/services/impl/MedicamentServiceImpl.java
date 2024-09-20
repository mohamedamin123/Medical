package com.medical.medical.services.impl;

import com.medical.medical.models.dto.req.MedicamentReqDTO;
import com.medical.medical.models.dto.res.MedicamentResDTO;
import com.medical.medical.models.entity.Medicament;
import com.medical.medical.models.mapper.MedicamentMapper;
import com.medical.medical.repository.MedicamentRepo;
import com.medical.medical.services.interf.MedicamentService;
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
public class MedicamentServiceImpl implements MedicamentService {

    private final MedicamentMapper mapper;

    private final MedicamentRepo repository;

//------------------------------------------------------------------------------------------------------------------save

    @Override
    public MedicamentResDTO saveMedicament(MedicamentReqDTO req) {
        Medicament emp=mapper.toEntity(req);
        repository.save(emp);
        return mapper.toRespDTO(emp);
    }

    @Override
    public MedicamentResDTO updateMedicament(MedicamentReqDTO req) {
        Medicament emp=mapper.toEntity(req);
        emp.setCreatedAt(this.repository.findById(req.getIdMedicament()).get().getCreatedAt());
        Medicament saved= repository.save(emp);
        return mapper.toRespDTO(saved);
    }
//------------------------------------------------------------------------------------------------------------------find

    @Override
    public List<MedicamentResDTO> findAllMedicament() {

        List<Medicament> users = this.repository.findAll();
        return mapper.toAllRespDTO(users);
    }


    @Override
    public Optional<MedicamentResDTO> findMedicamentById(int id) {
        Optional<Medicament> optionalMedicament = this.repository.findById(id);
        if (optionalMedicament.isPresent()) {
            MedicamentResDTO MedicamentResDTO = mapper.toRespDTO(optionalMedicament.get());
            return Optional.of(MedicamentResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MedicamentResDTO> findAllMedicamentAfterDelete() {
        List<Medicament> Medicaments = this.repository.findAll();
        List<Medicament> filteredMedicaments = Medicaments.stream()
                .filter(Medicament -> Medicament.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredMedicaments);
    }

    @Override
    public Optional<MedicamentResDTO> findMedicamentByIdAfterDelete(int id) {
        Optional<Medicament> optionalMedicament = this.repository.findById(id);
        if (optionalMedicament.isPresent() && optionalMedicament.get().getDeletedAt() == null) {
            MedicamentResDTO MedicamentResDTO = mapper.toRespDTO(optionalMedicament.get());
            return Optional.of(MedicamentResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<MedicamentResDTO> findMedicamentsByIdPatient(Integer id){
        List<Medicament> Medicaments = this.repository.findMedicamentsByIdPatient(id);
        List<Medicament> filteredMedicaments = Medicaments.stream()
                .filter(Medicament -> Medicament.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredMedicaments);
    }


//----------------------------------------------------------------------------------------------------------------delete


    @Override
    public void deleteMedicament(MedicamentReqDTO req) {
        Medicament emp=this.repository.findById(req.getIdMedicament()).get();
        emp.setDeletedAt(LocalDateTime.now());
        repository.save(emp);
    }

    @Override
    public void deleteMedicamentById(int id) {
        Medicament emp=this.repository.findById(id).get();
        emp.setDeletedAt(LocalDateTime.now());
        repository.save(emp);
    }
}
