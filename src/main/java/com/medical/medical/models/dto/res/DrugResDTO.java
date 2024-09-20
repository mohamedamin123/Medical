package com.medical.medical.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugResDTO {
    private String ingredientActif;
    private String usage;
    private String avertissements;
    private String ingredientsInactifs;
    private String posologieEtAdministration;

    @Override
    public String toString() {
        return "DrugResDTO{" + "\n"+
                "ingredientActif='" + ingredientActif + '\'' +"\n"+
                ", usage='" + usage + '\'' +"\n"+
                ", avertissements='" + avertissements + '\'' +"\n"+
                ", ingredientsInactifs='" + ingredientsInactifs + '\'' +"\n"+
                ", posologieEtAdministration='" + posologieEtAdministration + '\'' +"\n"+
                '}';
    }
}
