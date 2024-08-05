package com.medical.medical.models.dto.res;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class RendezVousResDTO {
    private LocalDate jour;

    private LocalTime heure;
}
