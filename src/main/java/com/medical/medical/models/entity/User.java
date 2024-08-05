package com.medical.medical.models.entity;

import com.medical.medical.ennum.Utilisateurs;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@MappedSuperclass
public class User {


    @Column(name = "nom", nullable = false)
    private String nom;

    @Column(name = "prenom", nullable = false)
    private String prenom;

    @Column(name = "telephone", nullable = false)
    private String tel;

    @Column(name = "email", nullable = true)
    private String email;

    @Column(name = "date_de_naissance")
    private LocalDate dateDeNaissance;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private Utilisateurs role;
}
