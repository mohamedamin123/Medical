package com.medical.medical.controller.API;

import com.medical.medical.models.dto.res.MedicamentResDTO;
import com.medical.medical.services.impl.MedicamentServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/medicaments/medicament")
@RequiredArgsConstructor
@Slf4j
public class MedicamentController {

    private final MedicamentServiceImpl medicamentService;

    @GetMapping("/{activeIngredient}")
    public ResponseEntity<MedicamentResDTO> getDrugData(@PathVariable String activeIngredient) {
        MedicamentResDTO drugInfo = medicamentService.getDrugInfo(activeIngredient);
        if ("Aucune information disponible pour cet ingrédient.".equals(drugInfo.getAvertissements())) {
            return new ResponseEntity<>(drugInfo, HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(drugInfo, HttpStatus.OK);
    }
}
