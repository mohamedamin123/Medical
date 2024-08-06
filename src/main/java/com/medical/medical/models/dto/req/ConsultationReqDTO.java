package com.medical.medical.models.dto.req;


import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.Date;
@Data
@Builder
@AllArgsConstructor

public class ConsultationReqDTO {

    private int idConsultation;
    private String note;

    private LocalDate dateDeConsultation;

    public ConsultationReqDTO(String note, LocalDate dateDeConsultation) {
        this.note = note;
        this.dateDeConsultation = dateDeConsultation;
    }
}
