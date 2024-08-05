package com.medical.medical.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "consultation")
@Builder
public class Consultation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_consultation")
    private int idConsultation;

    private String note;

    @Column(name = "date_de_consultation")
    @Temporal(TemporalType.DATE)
    private Date dateDeConsultation;

//------------------------------------------------------------------------------------------------------------foreignKey
    @Column(name = "medecin_id")
    private Integer medecinId;

    @Column(name = "patient_id")
    private Integer patientId;
//-------------------------------------------------------------------------------------------------------------relations

    @JsonBackReference("consultation_medecin")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medecin_id", insertable = false, updatable = false)
    private Medecin medecin;

    @JsonBackReference("consultation_patient")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    private Patient patient;
}
