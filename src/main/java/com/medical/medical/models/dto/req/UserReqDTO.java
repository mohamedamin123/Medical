package com.medical.medical.models.dto.req;

import com.medical.medical.ennum.Utilisateurs;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public  class UserReqDTO {
    private String nom;

    private String prenom;

    private String tel;

    private String email;

    private LocalDate dateDeNaissance;

    private String password;

    private Utilisateurs role;

}
