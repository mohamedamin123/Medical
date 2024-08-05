package com.medical.medical.models.dto.req;


import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
@Data
@Builder
public class ConsultationReqDTO {

    private int idConsultation;
    private String note;

    private LocalDate dateDeConsultation;

}
