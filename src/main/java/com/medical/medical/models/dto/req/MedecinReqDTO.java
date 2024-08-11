package com.medical.medical.models.dto.req;

import com.medical.medical.ennum.Utilisateurs;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder

public class MedecinReqDTO extends UserReqDTO {
    private int idMedecin;
    private String specialite;
    private String password;

    // Constructeur personnalisé
    public MedecinReqDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String password) {
        super(nom, prenom, tel, email, dateDeNaissance);
        this.password = password;
        this.setRole(Utilisateurs.MEDECIN);
    }

    // Constructeur par défaut
    public MedecinReqDTO() {
        super();
    }
}
