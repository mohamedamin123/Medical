package com.medical.medical.models.dto.res;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ConsultationResDTO {

    private int idConsultation;

    private LocalDate jour;

    private LocalTime heure;

    private Integer idMedecin;

    private Integer idPatient;


}
