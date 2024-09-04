package com.medical.medical.models.dto.res;
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
public class DossierMedicalResDTO {

    private int idDossierMedical;
    private String typeDocument;
    private String fichier;
    private LocalDate jour;
    private LocalTime heure;
    private byte[] contenue;

    private Integer idPatient;

}
