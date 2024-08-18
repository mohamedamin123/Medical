package com.medical.medical.models.entity;

import com.medical.medical.ennum.Utilisateurs;
import com.medical.medical.exceptions.UserException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@MappedSuperclass
public abstract class User {

    @NotBlank
    @Column(name = "nom", nullable = false)
    private String nom;

    @NotBlank
    @Column(name = "prenom", nullable = false)
    private String prenom;

    @NotBlank
    @Size(min = 8, max = 8)
    @Column(name = "telephone", nullable = false)
    private String tel;

    @NotBlank
    @Column(name = "email", nullable = true)
    @Email
    private String email;

    @Past
    @Column(name = "date_de_naissance")
    private LocalDate dateDeNaissance;

    private Boolean statut;


    @NotBlank
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Utilisateurs role;


    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

//---------------------------------------------------------------------------------------------------------Constructeurs


    public User(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.dateDeNaissance = dateDeNaissance;
    }

    public String getFullName() throws UserException {
        if (this.nom == null) {
            throw new UserException(UserException.NOM_INCORRECT);
        } else if(this.prenom == null) {
            throw new UserException((UserException.PRENOM_INCORRECT));
        }
        return this.prenom +" "+this.nom;
    }
    public String getAge() throws UserException {
        if (this.dateDeNaissance == null) {
            throw new UserException(UserException.DATE_DE_NAISSANCE_INVALIDE);
        }
        return String.valueOf(Period.between(this.dateDeNaissance, LocalDate.now()).getYears());
    }

    @Override
    public String toString() {
        return "User{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", dateDeNaissance=" + dateDeNaissance +
                ", statut=" + statut +
                ", role=" + role +
                '}';
    }
}
