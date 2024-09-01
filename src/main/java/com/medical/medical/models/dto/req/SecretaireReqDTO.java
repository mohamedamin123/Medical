package com.medical.medical.models.dto.req;
import com.medical.medical.ennum.Utilisateurs;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@SuperBuilder
public class SecretaireReqDTO extends UserReqDTO{
    private int idSecretaire;
    private Integer idMedecin;

    private String password;

    private LocalDateTime deletedAt;


    public SecretaireReqDTO(String nom, String prenom, String tel, String email, String password) {
        super(nom, prenom, tel, email);
        this.setRole(Utilisateurs.SECRETAIRE);
        this.password = password;
    }
}
