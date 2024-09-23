package com.medical.medical.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DrugResDTO {
    // Fields that might return a list or a single value, so use List<String>
    private String name; // Add this line
    private List<String> ingredientActif;
    private List<String> usage;
    private String avertissements;
    private List<String> ingredientsInactifs;
    private List<String> posologieEtAdministration;

    @Override
    public String toString() {
        return "DrugResDTO{" + "\n" +
                "ingredientActif=" + formatList(ingredientActif) + "\n" +
                ", usage=" + formatList(usage) + "\n" +
                ", avertissements='" + avertissements + '\'' + "\n" +
                ", ingredientsInactifs=" + formatList(ingredientsInactifs) + "\n" +
                ", posologieEtAdministration=" + formatList(posologieEtAdministration) + "\n" +
                '}';
    }

    // Helper method to format lists for cleaner output in toString()
    private String formatList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "N/A";
        }
        return String.join(", ", list);
    }
}
