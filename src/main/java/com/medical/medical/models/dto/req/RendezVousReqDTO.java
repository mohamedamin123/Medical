package com.medical.medical.models.dto.req;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class RendezVousReqDTO {

    private int idRendezVous;

    private LocalDate jour;

    private LocalTime heure;

    private String motif;

    private Integer idMedecin;

    private Integer idPatient;

}
