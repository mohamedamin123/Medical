package com.medical.medical.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Historique {
    private final LocalDate date;
    private final int nombre; // Update the field name to match the TableColumn definition
}

