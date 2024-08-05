package com.medical.medical.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "rendez_vous")
@Builder
public class RendezVous {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rendez_vous")
    private int idRendezVous;

    @Column(name = "jour")
    private LocalDate jour;

    @Column(name = "heure")
    @Temporal(TemporalType.TIME)
    private LocalTime heure;


//------------------------------------------------------------------------------------------------------------foreignKey
    @Column(name = "medecin_id")
    private Integer medecinId;

    @Column(name = "patient_id")
    private Integer patientId;
//-------------------------------------------------------------------------------------------------------------relations

    @JsonBackReference("rendez_vous_medecin")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medecin_id", insertable = false, updatable = false)
    private Medecin medecin;

    @JsonBackReference("rendez_vous_patient")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    private Patient patient;

//---------------------------------------------------------------------------------------------------------Constrcuteurs

}
