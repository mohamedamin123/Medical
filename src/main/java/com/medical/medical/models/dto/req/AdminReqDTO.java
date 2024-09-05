package com.medical.medical.models.dto.req;

import com.medical.medical.ennum.Utilisateurs;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
public class AdminReqDTO extends UserReqDTO {

    private int idAdmin;
    private String password;

    // Constructeur personnalisé
    public AdminReqDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String password) {
        super(nom, prenom, tel, email, dateDeNaissance);
        this.password = password;
        this.setRole(Utilisateurs.MEDECIN);
    }

    // Constructeur par défaut
    public AdminReqDTO() {
        super();
    }
}
