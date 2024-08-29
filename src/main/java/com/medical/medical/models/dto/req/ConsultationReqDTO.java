package com.medical.medical.models.dto.req;


import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;
@Data
@Builder
@AllArgsConstructor

public class ConsultationReqDTO {

    private int idConsultation;

    private Integer idMedecin;

    private Integer idPatient;

    private LocalDate jour;

    private LocalTime heure;

    public ConsultationReqDTO(Integer medecinId, Integer patientId, LocalDate jour, LocalTime heure) {
        this.idMedecin = medecinId;
        this.idPatient = patientId;
        this.jour = jour;
        this.heure = heure;
    }
}
