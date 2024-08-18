package com.medical.medical.models.dto.res;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;


@Data
@SuperBuilder
public class MedecinResDTO extends UserResDTO{

    private String specialite;

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
