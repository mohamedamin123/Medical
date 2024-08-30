package com.medical.medical.models.dto.res;
import lombok.Data;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
public class AdminResDTO extends UserResDTO{
    private int idAdmin;
}
