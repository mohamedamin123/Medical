package com.medical.medical.models.mapper;
import com.medical.medical.models.dto.req.ConsultationReqDTO;
import com.medical.medical.models.dto.req.DossierMedicalReqDTO;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.models.dto.res.DossierMedicalResDTO;
import com.medical.medical.models.entity.Consultation;
import com.medical.medical.models.entity.DossierMedical;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DossierMedicalMapper {

    DossierMedical toEntity(DossierMedicalReqDTO dto);

    DossierMedicalResDTO toRespDTO(DossierMedical resp);

    List<DossierMedical> toAllEntity(List<DossierMedicalReqDTO> dto);

    List<DossierMedicalResDTO> toAllRespDTO(List<DossierMedical> resp);
}
