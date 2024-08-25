package com.medical.medical.models.dto.res;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class RendezVousResDTO {
    private int idRendezVous;

    private LocalDate jour;
    private LocalTime heure;
    private String motif;
    private Integer idMedecin;
    private Integer idPatient;

    // Methods to convert fields to JavaFX properties

}
