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

	@Test
	void saveConsultation() {

//		ConsultationReqDTO c1=new ConsultationReqDTO("bonjour", LocalDate.now());
//		cc.saveConsultation(c1);

	}

	@Test
	void DeleteConsultationById() {

		cc.deleteConsultationById(1) ;

	}

	@Test
	void FindAllConsultation() {

		System.out.println(cc.findAllConsultation().size()) ;

	}

	@Test
	void FindAllConsultationAfterDelete() {

		System.out.println(cc.findAllConsultationAfterDelete().size() );

	}





}
