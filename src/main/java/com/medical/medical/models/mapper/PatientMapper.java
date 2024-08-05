package com.medical.medical.models.mapper;
import com.medical.medical.models.dto.req.NotificationReqDTO;
import com.medical.medical.models.dto.req.PatientReqDTO;
import com.medical.medical.models.dto.res.NotificationResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.entity.Notification;
import com.medical.medical.models.entity.Patient;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatientMapper {

    Patient toEntity(PatientReqDTO dto);

    PatientResDTO toRespDTO(Patient resp);

    List<Patient> toAllEntity(List<PatientReqDTO> dto);

    List<PatientResDTO> toAllRespDTO(List<Patient> resp);
}
