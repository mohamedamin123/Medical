package com.medical.medical.models.mapper;

import com.medical.medical.models.dto.req.PatientReqDTO;
import com.medical.medical.models.dto.req.RendezVousReqDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.RendezVousResDTO;
import com.medical.medical.models.entity.Patient;
import com.medical.medical.models.entity.RendezVous;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RendezVousMapper {

    RendezVous toEntity(RendezVousReqDTO dto);

    RendezVousResDTO toRespDTO(RendezVous resp);

    List<RendezVous> toAllEntity(List<RendezVousReqDTO> dto);

    List<RendezVousResDTO> toAllRespDTO(List<RendezVous> resp);

}
