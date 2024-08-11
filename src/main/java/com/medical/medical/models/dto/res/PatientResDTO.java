package com.medical.medical.models.dto.res;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PatientResDTO extends UserResDTO{
    private String note;

}
