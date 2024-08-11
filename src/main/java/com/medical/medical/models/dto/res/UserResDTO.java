package com.medical.medical.models.dto.res;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
@Data
@SuperBuilder
public class UserResDTO {
    private String nom;

    private String prenom;

    private String tel;

    private String email;

    private LocalDate dateDeNaissance;
}
