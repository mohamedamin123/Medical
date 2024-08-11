package com.medical.medical.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
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


    @Column(name = "note", nullable = true)
    private String note;

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
//---------------------------------------------------------------------------------------------------------Constructeurs




}
