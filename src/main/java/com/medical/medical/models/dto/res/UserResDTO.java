package com.medical.medical.models.dto.res;

import com.medical.medical.exceptions.UserException;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.Period;

@Data
@SuperBuilder
public class UserResDTO {
    private String nom;

    private String prenom;

    private String tel;

    private String email;

    private LocalDate dateDeNaissance;
    private Boolean statut;
    public String getFullName() throws UserException {
        if (this.nom == null) {
            throw new UserException(UserException.NOM_INCORRECT);
        } else if(this.prenom == null) {
            throw new UserException((UserException.PRENOM_INCORRECT));
        }
        return this.prenom +" "+this.nom;
    }
    public String getAge() throws UserException {
        if (this.dateDeNaissance == null) {
            throw new UserException(UserException.DATE_DE_NAISSANCE_INVALIDE);
        }
        return String.valueOf(Period.between(this.dateDeNaissance, LocalDate.now()).getYears());
    }


    public UserResDTO(String nom, String prenom, String tel, String email, LocalDate dateDeNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.tel = tel;
        this.email = email;
        this.dateDeNaissance = dateDeNaissance;
    }

    @Override
    public String toString() {
        return "UserResDTO{" +
                "nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", dateDeNaissance=" + dateDeNaissance +
                '}';
    }
}
