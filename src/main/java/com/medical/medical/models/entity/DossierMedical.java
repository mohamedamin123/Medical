package com.medical.medical.models.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "dossier_medical")
@Builder
public class DossierMedical {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_dossier_medical")
    private int idDossierMedical;
    @Column(name = "type_document", nullable = false)
    @NotBlank
    private String typeDocument;

    @Column(name = "contenu_document", columnDefinition = "TEXT", nullable = true)
    private String fichier;
    private LocalDate jour;
    private LocalTime heure;


    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

//------------------------------------------------------------------------------------------------------------ForeignKey
    @Column(name = "patient_id")
    private Integer IDpatient;
//--------------------------------------------------------------------------------------------------------------Relation

    @JsonBackReference("dossier_patient")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", insertable = false, updatable = false)
    private Patient patient;

}
