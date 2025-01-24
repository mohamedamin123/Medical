package com.medical.medical;

import com.medical.medical.controller.API.ConsultationController;
import com.medical.medical.controller.API.SecretaireController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ConsultationTests {

	@Autowired
	private ConsultationController controller;



	@Test
	void findByIdMedecinDesc() {
		System.out.println(controller.findConsultationsByIdMedecinOrderByJourDesc(4));
		System.out.println("-------------------------------");
		System.out.println(controller.findConsultationsByIdMedecin(4));
	}
}
