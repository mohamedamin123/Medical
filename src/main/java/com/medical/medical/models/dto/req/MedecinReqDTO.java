package com.medical.medical.models.dto.req;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedecinReqDTO {

    private int idMedecin;
    private String specialite;

}

