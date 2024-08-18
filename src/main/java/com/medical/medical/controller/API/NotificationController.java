package com.medical.medical.controller.API;

import com.medical.medical.models.dto.req.NotificationReqDTO;
import com.medical.medical.models.dto.res.DossierMedicalResDTO;
import com.medical.medical.models.dto.res.NotificationResDTO;
import com.medical.medical.services.interf.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/Notifications/Notification")
@RequiredArgsConstructor
@Slf4j
public class NotificationController {

    private final NotificationService service;

//------------------------------------------------------------------------------------------------------------------find
    @GetMapping(path = "/find-all")
    public List<NotificationResDTO> findAllNotification()
    {
        return this.service.findAllNotification();
    }

    @GetMapping(path = "/find-by-id/{id}")
    public Optional<NotificationResDTO> findNotificationById(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de Notification est : {}", id);

        return this.service.findNotificationById(id);
    }

    @GetMapping(path = "/find-all-after-delete")
    public List<NotificationResDTO> findAllNotificationAfterDelete()
    {
        return this.service.findAllNotificationAfterDelete();
    }

    @GetMapping(path = "/after-delete/find-by-id/{id}")
    public Optional<NotificationResDTO> findNotificationByIdAfterDelete(@PathVariable(name = "id")  Integer id)
    {
        log.info("id de NotificationResDTO est : {}", id);

        return this.service.findNotificationByIdAfterDelete(id);
    }

//------------------------------------------------------------------------------------------------------------------save

    @PostMapping(path = "/save")
    public void saveNotification(@RequestBody NotificationReqDTO user)
    {
        this.service.saveNotification(user);
    }

    @PutMapping(path = "/update")
    public void updateNotification(@RequestBody NotificationReqDTO user)
    {
        this.service.updateNotification(user);
    }

//----------------------------------------------------------------------------------------------------------------delete

    @DeleteMapping(path = "/delete-by-id/{id}")
    public void deleteNotificationById(@PathVariable(name = "id")  Integer id)
    {
        this.service.deleteNotificationById(id);
    }

    @DeleteMapping(path = "/delete")
    public void deleteNotification(@RequestBody NotificationReqDTO user)
    {
        this.service.deleteNotification(user);
    }
}
