package com.medical.medical.controller.API;

import com.medical.medical.models.dto.req.ConsultationReqDTO;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.services.interf.ConsultationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/consultations/consultation")
@RequiredArgsConstructor
@Slf4j
public class ConsultationController {

    private final ConsultationService service;

//------------------------------------------------------------------------------------------------------------------find
    @GetMapping(path = "/find-all")
    public List<ConsultationResDTO> findAllConsultation()
    {
        return this.service.findAllConsultation();
    }

    @GetMapping(path = "/find-all-by-medecin-id-and-date")
    public List<ConsultationResDTO> findConsultationsByIdMedecinAndJour( @RequestParam Integer id, @RequestParam  LocalDate jour) {
        return this.service.findConsultationsByIdMedecinAndJour(id,jour);
    }


        @GetMapping(path = "/find-by-id/{id}")
    public Optional<ConsultationResDTO> findConsultationById(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de consultation est : {}", id);

        return this.service.findConsultationById(id);
    }

    @GetMapping(path = "/find-all-after-delete")
    public List<ConsultationResDTO> findAllConsultationAfterDelete()
    {
        return this.service.findAllConsultationAfterDelete();
    }

    @GetMapping(path = "/after-delete/find-by-id/{id}")
    public Optional<ConsultationResDTO> findConsultationByIdAfterDelete(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de consultation est : {}", id);

        return this.service.findConsultationByIdAfterDelete(id);
    }

//------------------------------------------------------------------------------------------------------------------save

    @PostMapping(path = "/save")
    public void saveConsultation(@RequestBody ConsultationReqDTO user)
    {
        this.service.saveConsultation(user);
    }

    @PutMapping(path = "/update")
    public void updateConsultation(@RequestBody ConsultationReqDTO user)
    {
        this.service.updateConsultation(user);
    }

//----------------------------------------------------------------------------------------------------------------delete

    @DeleteMapping(path = "/delete-by-id/{id}")
    public void deleteConsultationById(@PathVariable(name = "id")  Integer id)
    {
        this.service.deleteConsultationById(id);
    }

    @DeleteMapping(path = "/delete")
    public void deleteConsultation(@RequestBody ConsultationReqDTO user)
    {
        this.service.deleteConsultation(user);
    }
}
