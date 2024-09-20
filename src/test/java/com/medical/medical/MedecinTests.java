package com.medical.medical;

import com.medical.medical.controller.API.AdminController;
import com.medical.medical.controller.API.DrugController;
import com.medical.medical.controller.API.RendezVousController;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MedecinTests {

	@Autowired
	private AdminController adminController;

	@Autowired
	private RendezVousController rendezVousController;

	@Autowired
	private DrugController drugController;

//	@Autowired
//	private UpdateDatabaseConfig updateDatabaseConfig;

//	@Test
//	void testDatabaseScanner() {
//		MySQLScanner scanner = new MySQLScanner(updateDatabaseConfig,"amin","amin");
//
//		// Lancez la mise à jour de la configuration de la base de données si disponible
//		String foundIp = scanner.updateDatabaseConfigIfAvailable();
//
//		if (foundIp != null) {
//			System.out.println("Database found at: " + foundIp);
//		} else {
//			System.out.println("No MySQL server with the database " + " found.");
//		}
//	}

	@Test
	void find() {
		String name="PANADOL EXTRA";
		System.out.println(name.toLowerCase());
		System.out.println(drugController.getDrugData(name.toLowerCase()));
	}
}
