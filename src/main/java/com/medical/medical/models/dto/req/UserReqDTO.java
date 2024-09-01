package com.medical.medical.models.dto.req;

import com.medical.medical.ennum.Utilisateurs;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder

public class UserReqDTO {
    private String nom;
    private String prenom;
    private String tel;
    private String email;
    private LocalDate dateDeNaissance;
    private Utilisateurs role;
    private Boolean statut;

    // Constructeur par défaut
    public UserReqDTO() {}

    // Constructeur avec certains paramètres
    public UserReqDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.dateDeNaissance = dateDeNaissance;

    }

    public UserReqDTO(String nom, String prenom, String tel, String email) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
    }
}
