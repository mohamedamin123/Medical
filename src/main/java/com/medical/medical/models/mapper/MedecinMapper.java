package com.medical.medical.models.mapper;
import com.medical.medical.models.dto.req.DossierMedicalReqDTO;
import com.medical.medical.models.dto.req.MedecinReqDTO;
import com.medical.medical.models.dto.res.DossierMedicalResDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.entity.DossierMedical;
import com.medical.medical.models.entity.Medecin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedecinMapper {

    Medecin toEntity(MedecinReqDTO dto);

    MedecinResDTO toRespDTO(Medecin resp);

    List<Medecin> toAllEntity(List<MedecinReqDTO> dto);

    List<MedecinResDTO> toAllRespDTO(List<Medecin> resp);
}
