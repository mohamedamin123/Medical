package com.medical.medical.models.dto.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MedicamentResDTO {
    private Integer idMedicament;
    private String nom;
    private String description;
    private Integer idPatient;

}
