package com.medical.medical.services.interf;

import com.medical.medical.models.dto.res.DrugResDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DrugService {
     DrugResDTO getDrugInfo(String activeIngredient);

//     List<DrugResDTO> findAllDrugs();
}
