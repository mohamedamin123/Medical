package com.medical.medical.models.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

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

//-------------------------------------------------------------------------------------------------------------relations

    @JsonManagedReference("notification_secretaire")
    @OneToMany(mappedBy = "secretaire", cascade = CascadeType.ALL)
    private List<Notification> notifications;
}
