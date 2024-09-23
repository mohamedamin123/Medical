package com.medical.medical.services.impl;

import com.medical.medical.models.dto.res.DrugResDTO;
import com.medical.medical.services.interf.DrugService;
import com.medical.medical.utils.OpenFdaResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.List;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;


@Service
@RequiredArgsConstructor
@Primary
public class DrugServiceImpl implements DrugService {

    @Value("${api.openfda.url}")
    private String openFdaApiUrl;

    private final RestTemplate restTemplate = new RestTemplate();


    @Override
    public DrugResDTO getDrugInfo(String activeIngredient) {
        // Encode the active ingredient to ensure correct URL formatting
//        String encodedActiveIngredient = UriUtils.encode(activeIngredient, StandardCharsets.UTF_8);

        String url = UriComponentsBuilder.fromHttpUrl(openFdaApiUrl + "/label.json")
                .queryParam("search", "active_ingredient:" + activeIngredient)
                .queryParam("limit", 1)
                .toUriString();

        try {
            // Adding headers
            HttpHeaders headers = new HttpHeaders();
            headers.set("User-Agent", "Mozilla/5.0");
            headers.set("Accept", "application/json");

            System.out.println("Request URL: " + url);
            System.out.println("Request Headers: " + headers);

            HttpEntity<String> entity = new HttpEntity<>(headers);
            url=url.replace("%20"," ");
            ResponseEntity<OpenFdaResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, OpenFdaResponse.class);

            System.out.println("Response Status Code: " + responseEntity.getStatusCode());

            OpenFdaResponse response = responseEntity.getBody();

            if (response != null && response.getResults() != null && !response.getResults().isEmpty()) {
                return mapToDrugResDTO(response.getResults().get(0));
            } else {
                return getEmptyDrugInfo();
            }
        } catch (HttpClientErrorException e) {
            System.err.println("Client error: " + e.getStatusCode() + " - " + e.getResponseBodyAsString());

            return getEmptyDrugInfo();
        } catch (Exception e) {
            System.err.println("Error fetching drug info: " + url);
            System.err.println("Error fetching drug info: " + e.getMessage());
            return getEmptyDrugInfo();
        }
    }





    // Method to map the API response to DrugResDTO
    private DrugResDTO mapToDrugResDTO(OpenFdaResponse.OpenFdaResult result) {
        DrugResDTO drugResDTO = new DrugResDTO();

        // Map each relevant field, including the drug name
        drugResDTO.setName(result.getName() != null ? result.getName() : "Unknown"); // Assuming result.getDrugName() exists
        drugResDTO.setIngredientActif(result.getActiveIngredient() != null ? List.of(result.getActiveIngredient().toString()) : List.of());
        drugResDTO.setUsage(result.getPurpose() != null ? List.of(result.getPurpose().toString()) : List.of());
        drugResDTO.setAvertissements(result.getWarnings().get(0));
        drugResDTO.setIngredientsInactifs(result.getInactiveIngredient() != null ? List.of(result.getInactiveIngredient().toString()) : List.of());
        drugResDTO.setPosologieEtAdministration(result.getDosageAndAdministration() != null ? List.of(result.getDosageAndAdministration().toString()) : List.of());

        return drugResDTO;
    }


    // Return an empty drug information DTO when no results are found or on error
    private DrugResDTO getEmptyDrugInfo() {
        DrugResDTO emptyDrugRes = new DrugResDTO();
        emptyDrugRes.setAvertissements("Aucune information disponible pour cet ingrédient.");
        return emptyDrugRes;
    }
}
