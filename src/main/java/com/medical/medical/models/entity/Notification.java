package com.medical.medical.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.medical.medical.ennum.Utilisateurs;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "notification")
@Builder
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_notification")
    private int idNotification;

    private String message;
    private Utilisateurs destinataire;
    private LocalDate jour;
    private LocalTime heure;

//------------------------------------------------------------------------------------------------------------foreignKey
    @Column(name = "secretaire_id")
    private Integer secretaireId;

    @Column(name = "patient_id")
    private Integer patientId;
//-------------------------------------------------------------------------------------------------------------relations

    @JsonBackReference("notification_secretaire")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "secretaire_id", insertable = false, updatable = false)
    private Secretaire secretaire;

    @JsonBackReference("notification_patient")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    private Patient patient;


}
