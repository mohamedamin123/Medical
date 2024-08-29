package com.medical.medical.utils;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class PatientItem {

    private final String name;
    private final Integer id;
    private final String arrivalTime;

    public PatientItem(String name, String arrivalTime) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.id=0;
    }

    public PatientItem(String name, String arrivalTime,Integer id) {
        this.name = name;
        this.arrivalTime = arrivalTime;
        this.id=id;
    }

    public PatientItem(String name, Integer id) {
        this.name = name;
        this.id = id;
        this.arrivalTime="";
    }

}
