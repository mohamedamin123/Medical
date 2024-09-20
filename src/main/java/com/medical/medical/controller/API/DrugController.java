package com.medical.medical.controller.API;

import com.medical.medical.models.dto.res.DrugResDTO;
import com.medical.medical.services.impl.DrugServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/drugs")
@RequiredArgsConstructor
@Slf4j
public class DrugController {

    private final DrugServiceImpl medicamentService;

    // Get drug information by active ingredient
    @GetMapping("/drug/{activeIngredient}")
    public ResponseEntity<DrugResDTO> getDrugData(@PathVariable String activeIngredient) {
        DrugResDTO drugInfo = medicamentService.getDrugInfo(activeIngredient);
        if ("Aucune information disponible pour cet ingrédient.".equals(drugInfo.getAvertissements())) {
            return new ResponseEntity<>(drugInfo, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(drugInfo, HttpStatus.OK);
    }

    // New method: Get all drugs
    @GetMapping("/find-all")
    public ResponseEntity<List<DrugResDTO>> getAllDrugs() {
        List<DrugResDTO> drugsList = medicamentService.findAllDrugs();
        if (drugsList.isEmpty() || "Erreur".equals(drugsList.get(0).getAvertissements())) {
            return new ResponseEntity<>(drugsList, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(drugsList, HttpStatus.OK);
    }

}
