package com.medical.medical.services.interf;

import com.medical.medical.models.dto.req.ConsultationReqDTO;
import com.medical.medical.models.dto.req.NotificationReqDTO;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.models.dto.res.NotificationResDTO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public interface NotificationService {
    NotificationResDTO saveNotification(NotificationReqDTO req);

    NotificationResDTO updateNotification(NotificationReqDTO req);

    List<NotificationResDTO> findAllNotification();


     Optional<NotificationResDTO> findNotificationById(int id) ;

    List<NotificationResDTO> findAllNotificationAfterDelete();

    Optional<NotificationResDTO> findNotificationByIdAfterDelete(int id);

    void deleteNotification(NotificationReqDTO req);

    void deleteNotificationById(int id);
}