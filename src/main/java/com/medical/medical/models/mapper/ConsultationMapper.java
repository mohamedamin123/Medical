package com.medical.medical.models.mapper;
import com.medical.medical.models.dto.req.ConsultationReqDTO;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.models.entity.Consultation;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ConsultationMapper {

    Consultation toEntity(ConsultationReqDTO dto);

    ConsultationResDTO toRespDTO(Consultation resp);

    List<Consultation> toAllEntity(List<ConsultationReqDTO> dto);

    List<ConsultationResDTO> toAllRespDTO(List<Consultation> resp);
}
