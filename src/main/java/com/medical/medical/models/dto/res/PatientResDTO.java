package com.medical.medical.models.dto.res;
import com.medical.medical.ennum.Sexe;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
public class PatientResDTO extends UserResDTO{
    private int idPatient;
    private String note;
    private String idUnique;
    private String CIN;
    private String batiment;
    private Integer medecinId;
    private Sexe sexe;
    private String ville;



    public PatientResDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String note, String idUnique, String CIN,String ville) {
        super(nom, prenom, tel, email, dateDeNaissance);
        this.note = note;
        this.idUnique = idUnique;
        this.CIN = CIN;
        this.ville=ville;
    }
    public PatientResDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String note, String idUnique, String CIN,Sexe sexe,Integer medecinId,String ville) {
        this(nom, prenom, tel, email, dateDeNaissance,note,idUnique,CIN,ville);
        this.medecinId=medecinId;
        this.sexe=sexe;
    }

    @Override
    public String toString() {
        return "PatientResDTO{" +
                "nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", tel='" + getTel() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", dateDeNaissance=" + getDateDeNaissance() +
                ", note='" + note + '\'' +
                ", idUnique='" + idUnique + '\'' +
                ", CIN='" + CIN + '\'' +
                ", Medecin id='" + medecinId + '\'' +

                '}';
    }
}
