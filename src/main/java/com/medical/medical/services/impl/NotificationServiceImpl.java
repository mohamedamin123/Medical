package com.medical.medical.services.impl;

import com.medical.medical.models.dto.req.NotificationReqDTO;
import com.medical.medical.models.dto.res.NotificationResDTO;
import com.medical.medical.models.entity.Notification;
import com.medical.medical.models.mapper.NotificationMapper;
import com.medical.medical.repository.NotificationRepo;
import com.medical.medical.services.interf.NotificationService;
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
public class NotificationServiceImpl implements NotificationService {

    private final NotificationMapper mapper;

    private final NotificationRepo repository;

//------------------------------------------------------------------------------------------------------------------save

    @Override
    public NotificationResDTO saveNotification(NotificationReqDTO req) {
        Notification emp=mapper.toEntity(req);
        repository.save(emp);
        return mapper.toRespDTO(emp);
    }

    @Override
    public NotificationResDTO updateNotification(NotificationReqDTO req) {
        Notification emp=mapper.toEntity(req);
        emp.setCreatedAt(this.repository.findById(req.getIdNotification()).get().getCreatedAt());
        Notification saved= repository.save(emp);
        return mapper.toRespDTO(saved);
    }
//------------------------------------------------------------------------------------------------------------------find

    @Override
    public List<NotificationResDTO> findAllNotification() {

        List<Notification> users = this.repository.findAll();
        return mapper.toAllRespDTO(users);
    }


    @Override
    public Optional<NotificationResDTO> findNotificationById(int id) {
        Optional<Notification> optionalNotification = this.repository.findById(id);
        if (optionalNotification.isPresent()) {
            NotificationResDTO NotificationResDTO = mapper.toRespDTO(optionalNotification.get());
            return Optional.of(NotificationResDTO);
        } else {
            return Optional.empty();
        }
    }

    @Override
    public List<NotificationResDTO> findAllNotificationAfterDelete() {
        List<Notification> notifications = this.repository.findAll();
        List<Notification> filteredNotifications = notifications.stream()
                .filter(notification -> notification.getDeletedAt() == null)
                .collect(Collectors.toList());
        return mapper.toAllRespDTO(filteredNotifications);
    }

    @Override
    public Optional<NotificationResDTO> findNotificationByIdAfterDelete(int id) {
        Optional<Notification> optionalNotification = this.repository.findById(id);
        if (optionalNotification.isPresent() && optionalNotification.get().getDeletedAt() == null) {
            NotificationResDTO notificationResDTO = mapper.toRespDTO(optionalNotification.get());
            return Optional.of(notificationResDTO);
        } else {
            return Optional.empty();
        }
    }

//----------------------------------------------------------------------------------------------------------------delete


    @Override
    public void deleteNotification(NotificationReqDTO req) {
        Notification emp=this.repository.findById(req.getIdNotification()).get();
        emp.setDeletedAt(LocalDateTime.now());
        repository.save(emp);
    }

    @Override
    public void deleteNotificationById(int id) {
        Notification emp=this.repository.findById(id).get();
        emp.setDeletedAt(LocalDateTime.now());
        repository.save(emp);
    }
}
