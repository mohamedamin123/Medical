package com.medical.medical.models.dto.req;
import jakarta.persistence.Column;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RendezVousReqDTO {

    private int idRendezVous;

    private LocalDate jour;

    private LocalTime heure;

    private String motif;

    private Integer idMedecin;

    private Integer idPatient;

}
