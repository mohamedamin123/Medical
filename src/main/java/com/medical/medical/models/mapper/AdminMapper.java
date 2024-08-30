package com.medical.medical.models.mapper;
import com.medical.medical.models.dto.req.AdminReqDTO;
import com.medical.medical.models.dto.res.AdminResDTO;
import com.medical.medical.models.entity.Admin;
import com.medical.medical.models.entity.Admin;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AdminMapper {

    Admin toEntity(AdminReqDTO dto);

    AdminResDTO toRespDTO(Admin resp);

    List<Admin> toAllEntity(List<AdminReqDTO> dto);

    List<AdminResDTO> toAllRespDTO(List<Admin> resp);
}
