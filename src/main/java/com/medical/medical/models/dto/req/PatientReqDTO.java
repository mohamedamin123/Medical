package com.medical.medical.models.dto.req;
import com.medical.medical.ennum.Sexe;
import com.medical.medical.ennum.Utilisateurs;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.management.relation.Role;
import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class PatientReqDTO extends UserReqDTO {
    private int idPatient;
    private String note;
    private String remboursement;
    private String idUnique;
    private String CIN;
    private Integer idMedecin;
    private Sexe sexe;
    private String adresse;
    private Boolean maladie;


    public PatientReqDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String note, String batiment, String idUnique, String CIN,String ville) {
        super(nom, prenom, tel, email, dateDeNaissance);
        this.note = note;
        this.remboursement = batiment;
        this.idUnique = idUnique;
        this.CIN = CIN;
        this.adresse=ville;
        this.setRole(Utilisateurs.PATIENT);
    }
    public PatientReqDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String note, String batiment, String idUnique, String CIN,String ville,Integer medecinId) {
        super(nom, prenom, tel, email, dateDeNaissance);
        this.note = note;
        this.remboursement = batiment;
        this.idUnique = idUnique;
        this.CIN = CIN;
        this.idMedecin=medecinId;
        this.adresse=ville;
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
    public PatientReqDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String note, String batiment, String idUnique, String CIN,String ville,Sexe sexe,Integer idM) {
        this(nom, prenom, tel, email, dateDeNaissance,note,batiment,idUnique,CIN,ville,sexe);
        this.idMedecin=idM;
    }

    public PatientReqDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String note, String batiment, String idUnique, String CIN,String ville,Sexe sexe,Integer idM,int idP) {
        this(nom, prenom, tel, email, dateDeNaissance,note,batiment,idUnique,CIN,ville,sexe,idM);
        this.idPatient=idP;
    }

}
