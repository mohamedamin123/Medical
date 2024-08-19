package com.medical.medical.models.dto.res;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
public class PatientResDTO extends UserResDTO{
    private String note;

    private String idUnique;

    private String CIN;


    public PatientResDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String note, String idUnique, String CIN) {
        super(nom, prenom, tel, email, dateDeNaissance);
        this.note = note;
        this.idUnique = idUnique;
        this.CIN = CIN;
    }
}
