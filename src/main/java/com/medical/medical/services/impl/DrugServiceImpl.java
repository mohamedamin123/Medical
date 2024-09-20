package com.medical.medical.services.impl;

import com.medical.medical.models.dto.res.DrugResDTO;
import com.medical.medical.services.interf.DrugService;
import lombok.RequiredArgsConstructor;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
@Primary
public class DrugServiceImpl implements DrugService {

    private static final String API_URL = "https://api.fda.gov/drug/label.json?search=active_ingredient:%s";

    @Override
    public DrugResDTO getDrugInfo(String activeIngredient) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format(API_URL, activeIngredient);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            String responseBody = response.getBody();

            // Parse JSON response
            JSONObject jsonResponse = new JSONObject(responseBody);
            JSONArray results = jsonResponse.getJSONArray("results");

            // Check if results are empty
            if (results.length() == 0) {
                DrugResDTO notFoundDTO = new DrugResDTO();
                notFoundDTO.setIngredientActif("Non disponible");
                notFoundDTO.setUsage("Non disponible");
                notFoundDTO.setAvertissements("Aucune information disponible pour cet ingrédient.");
                notFoundDTO.setIngredientsInactifs("Non disponible");
                notFoundDTO.setPosologieEtAdministration("Non disponible");
                return notFoundDTO;
            }

            // Process the first result if available
            JSONObject drugInfo = results.getJSONObject(0);

            // Create and populate DTO
            DrugResDTO drugInfoDTO = new DrugResDTO();
            drugInfoDTO.setIngredientActif(drugInfo.optJSONArray("active_ingredient") != null
                    ? drugInfo.getJSONArray("active_ingredient").join(", ")
                    : "Non disponible");
            drugInfoDTO.setUsage(drugInfo.optJSONArray("purpose") != null
                    ? drugInfo.getJSONArray("purpose").join(", ")
                    : "Non disponible");
            drugInfoDTO.setAvertissements(drugInfo.optJSONArray("warnings") != null
                    ? drugInfo.getJSONArray("warnings").join(", ")
                    : "Non disponible");
            drugInfoDTO.setIngredientsInactifs(drugInfo.optJSONArray("inactive_ingredient") != null
                    ? drugInfo.getJSONArray("inactive_ingredient").join(", ")
                    : "Non disponible");
            drugInfoDTO.setPosologieEtAdministration(drugInfo.optJSONArray("dosage_and_administration") != null
                    ? drugInfo.getJSONArray("dosage_and_administration").join(", ")
                    : "Non disponible");

            return drugInfoDTO;

        } catch (Exception e) {
            // Handle errors gracefully
            DrugResDTO errorDTO = new DrugResDTO();
            errorDTO.setIngredientActif("Erreur");
            errorDTO.setUsage("Erreur");
            errorDTO.setAvertissements("Erreur lors de la récupération des informations.");
            errorDTO.setIngredientsInactifs("Erreur");
            errorDTO.setPosologieEtAdministration("Erreur");
            return errorDTO;
        }
    }
}
