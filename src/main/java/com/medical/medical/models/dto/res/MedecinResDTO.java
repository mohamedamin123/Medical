package com.medical.medical.models.dto.res;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;


@Data
@SuperBuilder
public class MedecinResDTO extends UserResDTO{

    private int idMedecin;
    private String specialite;
    private LocalDateTime deletedAt;


    @Override
    public String toString() {
        return "MedecinResDTO{" +
                "nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom()+ '\'' +
                ", tel='" + getTel() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", dateDeNaissance=" + getDateDeNaissance() +
                "specialite='" + specialite + '\'' +
                '}';
    }
}
