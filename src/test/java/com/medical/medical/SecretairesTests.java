package com.medical.medical;

import com.medical.medical.controller.API.AdminController;
import com.medical.medical.controller.API.DrugController;
import com.medical.medical.controller.API.RendezVousController;
import com.medical.medical.controller.API.SecretaireController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SecretairesTests {

	@Autowired
	private SecretaireController controller;



	@Test
	void findByIdMedecin() {
		System.out.println(controller.findSecretairesByIdMedecin(5));
	}
}
