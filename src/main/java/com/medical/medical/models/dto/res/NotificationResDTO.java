package com.medical.medical.models.dto.res;
import com.medical.medical.ennum.Utilisateurs;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResDTO {
    private String message;
    private Utilisateurs destinataire;
    private LocalDate jour;
    private LocalTime heure;
}
