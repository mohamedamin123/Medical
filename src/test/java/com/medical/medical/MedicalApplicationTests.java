package com.medical.medical;

import com.medical.medical.controller.API.ConsultationController;
import com.medical.medical.models.dto.req.ConsultationReqDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class MedicalApplicationTests {
	@Autowired
	private ConsultationController cc;


}
