package com.medical.medical.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "medecin")
public class Medecin extends User{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_medecin")
    private int idMedecin;
    @NotBlank
    private String specialite;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;
//--------------------------------------------------------------------------------------------------------------Relation
    @JsonManagedReference("rendez_vous_medecin")
    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL)
    private List<RendezVous> rendezVous;

    @JsonManagedReference("consultation_medecin")
    @OneToMany(mappedBy = "medecin", cascade = CascadeType.ALL)
    private List<Consultation> consultations;

    public Medecin(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String password) {
        super(nom, prenom, tel, email, dateDeNaissance);
        this.password = password;
    }

    @Override
    public String toString() {
        return "Medecin{" +
                "nom='" + getNom() + '\'' +
                ", prenom='" + getPrenom() + '\'' +
                ", tel='" + getTel() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", dateDeNaissance=" + getDateDeNaissance() +
                ", statut=" + getStatut() +
                ", role=" + getRole() +
                "idMedecin=" + idMedecin +
                ", specialite='" + specialite + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
