package com.medical.medical.models.dto.req;
import com.medical.medical.ennum.Sexe;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class PatientReqDTO extends UserReqDTO {
    private int idPatient;
    private String note;
    private String batiment;
    private String idUnique;
    private String CIN;
    private Integer medecinId;

    private Sexe sexe;
}
