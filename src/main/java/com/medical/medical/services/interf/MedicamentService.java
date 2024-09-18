package com.medical.medical.services.interf;

import com.medical.medical.models.dto.res.MedicamentResDTO;
import org.springframework.stereotype.Service;

@Service
public interface MedicamentService {
     MedicamentResDTO getDrugInfo(String activeIngredient);
}
