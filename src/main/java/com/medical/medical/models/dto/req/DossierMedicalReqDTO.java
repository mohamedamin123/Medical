package com.medical.medical.models.dto.req;

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
public class DossierMedicalReqDTO {
    private int idDossierMedical;
    private String typeDocument;
    private String fichier;
    private LocalDate jour;
    private LocalTime heure;
    private byte[] contenue;

    private Integer idPatient;

    public DossierMedicalReqDTO(String typeDocument, String fichier,byte[] contenue) {
        this.typeDocument = typeDocument;
        this.fichier = fichier;
        this.jour = LocalDate.now();
        this.heure = LocalTime.now();
        this.contenue=contenue;
    }

    public DossierMedicalReqDTO(String typeDocument, String fichier,byte[] contenue,Integer iDpatient) {
        this(typeDocument,fichier,contenue);
        this.idPatient=iDpatient;
    }

}
