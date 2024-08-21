package com.medical.medical.models.dto.req;
import com.medical.medical.ennum.Sexe;
import com.medical.medical.ennum.Utilisateurs;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import javax.management.relation.Role;
import java.time.LocalDate;

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
    private String ville;

    public PatientReqDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String note, String batiment, String idUnique, String CIN,String ville) {
        super(nom, prenom, tel, email, dateDeNaissance);
        this.note = note;
        this.batiment = batiment;
        this.idUnique = idUnique;
        this.CIN = CIN;
        this.ville=ville;
        this.setRole(Utilisateurs.PATIENT);
    }
    public PatientReqDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String note, String batiment, String idUnique, String CIN,String ville,Integer medecinId) {
        super(nom, prenom, tel, email, dateDeNaissance);
        this.note = note;
        this.batiment = batiment;
        this.idUnique = idUnique;
        this.CIN = CIN;
        this.medecinId=medecinId;
        this.ville=ville;
        this.setRole(Utilisateurs.PATIENT);

    }

    public PatientReqDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String note, String batiment, String idUnique, String CIN,String ville,Sexe sexe) {
        this(nom, prenom, tel, email, dateDeNaissance,note,batiment,idUnique,CIN,ville);
        this.sexe=sexe;
    }
    public PatientReqDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String note, String batiment, String idUnique, String CIN,String ville,Integer medecinId,Sexe sexe) {
        this(nom, prenom, tel, email, dateDeNaissance,note,batiment,idUnique,CIN,ville,medecinId);
        this.sexe=sexe;
    }
}
