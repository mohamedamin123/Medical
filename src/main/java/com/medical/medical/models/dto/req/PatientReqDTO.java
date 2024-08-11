package com.medical.medical.models.dto.req;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PatientReqDTO extends UserReqDTO {
    private int idPatient;
    private String note;
}
