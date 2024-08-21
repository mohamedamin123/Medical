package com.medical.medical.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.medical.medical.ennum.Sexe;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import jakarta.validation.constraints.NotBlank;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@SuperBuilder
@Entity
@Table(name = "patient")
public class Patient extends User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_patient")
    private int idPatient;


    @Column(name = "CIN", nullable = true)
    @Size(min = 8, max = 8)
    private String CIN;

    @Column(name = "id_unique", nullable = true)
    private String idUnique;

    @Column(name = "batiment", nullable = true)
    private String batiment;


    @Column(name = "note", nullable = true)
    private String note;

    @Column(name = "sexe", nullable = false)
    private Sexe sexe;

    @Column(name = "ville", nullable = false)
    private String ville;

//----------------------------------------------------------------------------------------------------------------------
    @Column(name = "medecin_id")
    private Integer medecinId;
//--------------------------------------------------------------------------------------------------------------Relation
    @JsonManagedReference("rendez_vous_patient")
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<RendezVous> rendezVous;

    @JsonManagedReference("consultation_patient")
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Consultation> consultations;

    @JsonManagedReference("notification_patient")
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private List<Notification> notifications;

    @JsonBackReference("patient_medecin")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medecin_id", insertable = false, updatable = false)
    private Medecin medecin;

//---------------------------------------------------------------------------------------------------------Constructeurs




}
