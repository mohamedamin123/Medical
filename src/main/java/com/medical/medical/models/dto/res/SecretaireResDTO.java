package com.medical.medical.models.dto.res;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
public class SecretaireResDTO extends UserResDTO {

    private int idSecretaire;


    private Integer idMedecin;

    private String password;
    private LocalDateTime deletedAt;


}
