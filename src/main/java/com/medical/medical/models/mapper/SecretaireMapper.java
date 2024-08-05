package com.medical.medical.models.mapper;
import com.medical.medical.models.dto.req.PatientReqDTO;
import com.medical.medical.models.dto.req.SecretaireReqDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.models.entity.Patient;
import com.medical.medical.models.entity.Secretaire;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface SecretaireMapper {

    Secretaire toEntity(SecretaireReqDTO dto);

    SecretaireResDTO toRespDTO(Secretaire resp);

    List<Secretaire> toAllEntity(List<SecretaireReqDTO> dto);

    List<SecretaireResDTO> toAllRespDTO(List<Secretaire> resp);
}
