package com.medical.medical.models.dto.res;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class DossierMedicalResDTO {


    private String typeDocument;
    private String fichier;
    private LocalDate jour;
    private LocalTime heure;
}
