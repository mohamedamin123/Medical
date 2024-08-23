package com.medical.medical;

import com.medical.medical.controller.API.ConsultationController;
import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.controller.API.PatientController;
import com.medical.medical.models.dto.req.ConsultationReqDTO;
import com.medical.medical.models.dto.req.MedecinReqDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.entity.Medecin;
import com.medical.medical.models.entity.Patient;
import com.medical.medical.repository.PatientRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

@SpringBootTest
class MedecinTests {
	@Autowired
	private MedecinController medecinController;
	@Autowired
	private  PatientRepo repository;



	@Test
	void save() {
		LocalDate dateOfBirth = LocalDate.of(1980, 5, 15); // Example date: May 15, 1980

		MedecinReqDTO medecin=new MedecinReqDTO("ganar","amin","95147455","mohamedaming146@gmail.com",dateOfBirth,"123");
		medecinController.saveMedecin(medecin);

	}

	@Test
	void miseAjour() {

		Optional<Patient> p=repository.findPatientByIdPatient(14);
		System.out.println(p);

//		medecinController.updateMedecin(medecin);

	}

	@Test
	void find_all() {

		System.out.println(medecinController.findAllMedecin());

	}






}
