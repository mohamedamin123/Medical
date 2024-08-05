package com.medical.medical.models.mapper;
import com.medical.medical.models.dto.req.MedecinReqDTO;
import com.medical.medical.models.dto.req.NotificationReqDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.NotificationResDTO;
import com.medical.medical.models.entity.Medecin;
import com.medical.medical.models.entity.Notification;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface NotificationMapper {

    Notification toEntity(NotificationReqDTO dto);

    NotificationResDTO toRespDTO(Notification resp);
    List<Notification> toAllEntity(List<NotificationReqDTO> dto);

    List<NotificationResDTO> toAllRespDTO(List<Notification> resp);
}
