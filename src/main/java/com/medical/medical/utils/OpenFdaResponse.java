package com.medical.medical.utils;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OpenFdaResponse {

    private List<OpenFdaResult> results;

    public List<OpenFdaResult> getResults() {
        return results;
    }

    public void setResults(List<OpenFdaResult> results) {
        this.results = results;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class OpenFdaResult {

        private String name;

        @JsonProperty("active_ingredient")
        private List<String> activeIngredient;

        @JsonProperty("purpose")
        private List<String> purpose;

        @JsonProperty("warnings")
        private List<String> warnings;

        @JsonProperty("inactive_ingredient")
        private List<String> inactiveIngredient;

        @JsonProperty("dosage_and_administration")
        private List<String> dosageAndAdministration;


    }
}
