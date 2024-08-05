package com.medical.medical.models.dto.res;

import java.time.LocalDate;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ConsultationResDTO {

    private String note;

    private LocalDate dateDeConsultation;

}
