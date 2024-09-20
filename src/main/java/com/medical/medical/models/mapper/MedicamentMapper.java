package com.medical.medical.models.mapper;
import com.medical.medical.models.dto.req.MedicamentReqDTO;
import com.medical.medical.models.dto.res.MedicamentResDTO;
import com.medical.medical.models.entity.Medicament;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MedicamentMapper {

    Medicament toEntity(MedicamentReqDTO dto);

    MedicamentResDTO toRespDTO(Medicament resp);

    List<Medicament> toAllEntity(List<MedicamentReqDTO> dto);

    List<MedicamentResDTO> toAllRespDTO(List<Medicament> resp);
}
