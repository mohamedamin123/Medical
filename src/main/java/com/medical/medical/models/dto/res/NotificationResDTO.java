package com.medical.medical.models.dto.res;
import com.medical.medical.ennum.Utilisateurs;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
public class NotificationResDTO {
    private String message;
    private Utilisateurs destinataire;
    private LocalDate jour;
    private LocalTime heure;
}
