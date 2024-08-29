package com.medical.medical.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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

    @Column(name = "jour")
    private LocalDate jour;

    @Column(name = "heure")
    @Temporal(TemporalType.TIME)
    private LocalTime heure;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

//------------------------------------------------------------------------------------------------------------foreignKey
    @Column(name = "medecin_id")
    private Integer idMedecin;

    @Column(name = "patient_id")
    private Integer idPatient;
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
