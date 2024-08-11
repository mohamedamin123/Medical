package com.medical.medical;

import com.medical.medical.controller.API.ConsultationController;
import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.models.dto.req.ConsultationReqDTO;
import com.medical.medical.models.dto.req.MedecinReqDTO;
import com.medical.medical.models.entity.Medecin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
class MedecinTests {
	@Autowired
	private MedecinController medecinController;

	@Test
	void save() {
		LocalDate dateOfBirth = LocalDate.of(1980, 5, 15); // Example date: May 15, 1980

		MedecinReqDTO medecin=new MedecinReqDTO("gana","amin","95147455","mohamedaming146@gmail.com",dateOfBirth,"123");
		medecinController.saveMedecin(medecin);

	}

	@Test
	void find_all() {

		System.out.println(medecinController.findAllMedecin());

	}






}
