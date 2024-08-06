package com.medical.medical.controller;

import com.medical.medical.models.dto.req.RendezVousReqDTO;
import com.medical.medical.models.dto.res.NotificationResDTO;
import com.medical.medical.models.dto.res.RendezVousResDTO;
import com.medical.medical.services.interf.RendezVousService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/RendezVouss/RendezVous")
@RequiredArgsConstructor
@Slf4j
public class RendezVousController {

    private final RendezVousService service;

//------------------------------------------------------------------------------------------------------------------find
    @GetMapping(path = "/find-all")
    public List<RendezVousResDTO> findAllRendezVous()
    {
        return this.service.findAllRendezVous();
    }

    @GetMapping(path = "/{id}")
    public Optional<RendezVousResDTO> findRendezVousById(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de RendezVous est : {}", id);

        return this.service.findRendezVousById(id);
    }


    @GetMapping(path = "/find-all-after-delete")
    public List<RendezVousResDTO> findAllRendezVousAfterDelete()
    {
        return this.service.findAllRendezVousAfterDelete();
    }

    @GetMapping(path = "/after-delete/{id}")
    public Optional<RendezVousResDTO> findRendezVousByIdAfterDelete(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de findAllRendezVousAfterDelete est : {}", id);

        return this.service.findRendezVousByIdAfterDelete(id);
    }

//------------------------------------------------------------------------------------------------------------------save

    @PostMapping(path = "/save")
    public void saveRendezVous(@RequestBody RendezVousReqDTO user)
    {
        this.service.saveRendezVous(user);
    }

    @PutMapping(path = "/update")
    public void updateRendezVous(@RequestBody RendezVousReqDTO user)
    {
        this.service.saveRendezVous(user);
    }

//----------------------------------------------------------------------------------------------------------------delete

    @DeleteMapping(path = "/{id}")
    public void deleteRendezVousById(@PathVariable(name = "id")  Integer id)
    {
        this.service.deleteRendezVousById(id);
    }

    @DeleteMapping(path = "/delete")
    public void deleteRendezVous(@RequestBody RendezVousReqDTO user)
    {
        this.service.deleteRendezVous(user);
    }
}
