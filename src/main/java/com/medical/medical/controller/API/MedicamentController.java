package com.medical.medical.controller.API;

import com.medical.medical.models.dto.req.MedicamentReqDTO;
import com.medical.medical.models.dto.res.MedicamentResDTO;
import com.medical.medical.services.interf.MedicamentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/medicaments/medicament")
@RequiredArgsConstructor
@Slf4j
public class MedicamentController {

    private final MedicamentService service;

//------------------------------------------------------------------------------------------------------------------find
    @GetMapping(path = "/find-all")
    public List<MedicamentResDTO> findAllMedicament()
    {
        return this.service.findAllMedicament();
    }

    @GetMapping(path = "/find-by-id/{id}")
    public Optional<MedicamentResDTO> findMedicamentById(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de Medicament est : {}", id);

        return this.service.findMedicamentById(id);
    }

    @GetMapping(path = "/find-by-id-pation-after-delete/{id}")
    public List<MedicamentResDTO> findMedicamentByIdPatientAfterDelete(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de Medicament est : {}", id);

        return this.service.findMedicamentsByIdPatient(id);
    }

    @GetMapping(path = "/find-all-after-delete")
    public List<MedicamentResDTO> findAllMedicamentAfterDelete()
    {
        return this.service.findAllMedicamentAfterDelete();
    }

    @GetMapping(path = "/after-delete/find-by-id/{id}")
    public Optional<MedicamentResDTO> findMedicamentByIdAfterDelete(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de MedicamentResDTO est : {}", id);

        return this.service.findMedicamentByIdAfterDelete(id);
    }

//------------------------------------------------------------------------------------------------------------------save

    @PostMapping(path = "/save")
    public void saveMedicament(@RequestBody MedicamentReqDTO user)
    {
        this.service.saveMedicament(user);
    }

    @PutMapping(path = "/update")
    public void updateMedicament(@RequestBody MedicamentReqDTO user)
    {
        this.service.updateMedicament(user);
    }

//----------------------------------------------------------------------------------------------------------------delete

    @DeleteMapping(path = "/delete-by-id/{id}")
    public void deleteMedicamentById(@PathVariable(name = "id")  Integer id)
    {
        this.service.deleteMedicamentById(id);
    }

    @DeleteMapping(path = "/delete")
    public void deleteMedicament(@RequestBody MedicamentReqDTO user)
    {
        this.service.deleteMedicament(user);
    }
}
