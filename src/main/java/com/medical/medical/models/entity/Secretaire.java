package com.medical.medical.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "secretaire")
public class Secretaire extends User  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_secretaire")
    private int idSecretaire;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

//-------------------------------------------------------------------------------------------------------------relations

    @JsonManagedReference("notification_secretaire")
    @OneToMany(mappedBy = "secretaire", cascade = CascadeType.ALL)
    private List<Notification> notifications;


//---------------------------------------------------------------------------------------------------------Constructeurs


    public Secretaire(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance, String password) {
        super(nom, prenom, tel, email, dateDeNaissance);
        this.password = password;
    }
}
