package com.medical.medical.services.interf;

import com.medical.medical.models.dto.req.MedicamentReqDTO;
import com.medical.medical.models.dto.res.MedicamentResDTO;
import com.medical.medical.models.entity.Medicament;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public interface MedicamentService {
    MedicamentResDTO saveMedicament(MedicamentReqDTO req);

    MedicamentResDTO updateMedicament(MedicamentReqDTO req);

    List<MedicamentResDTO> findAllMedicament();


     Optional<MedicamentResDTO> findMedicamentById(int id) ;

    List<MedicamentResDTO> findMedicamentsByIdPatient(Integer id);


    List<MedicamentResDTO> findAllMedicamentAfterDelete();

    Optional<MedicamentResDTO> findMedicamentByIdAfterDelete(int id);

    void deleteMedicament(MedicamentReqDTO req);

    void deleteMedicamentById(int id);
}