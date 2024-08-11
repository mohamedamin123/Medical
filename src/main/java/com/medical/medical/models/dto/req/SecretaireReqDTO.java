package com.medical.medical.models.dto.req;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class SecretaireReqDTO extends UserReqDTO{
    private int idSecretaire;
    private String password;

}
