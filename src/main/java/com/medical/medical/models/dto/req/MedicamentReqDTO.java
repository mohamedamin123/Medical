package com.medical.medical.models.dto.req;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MedicamentReqDTO {
    private int idMedicament;
    private String nom;
    private String description;
    private Integer idPatient;

}
