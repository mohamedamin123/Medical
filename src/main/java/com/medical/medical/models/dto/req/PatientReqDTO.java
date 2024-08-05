package com.medical.medical.models.dto.req;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PatientReqDTO {
    private int idPatient;
    private String note;
}
