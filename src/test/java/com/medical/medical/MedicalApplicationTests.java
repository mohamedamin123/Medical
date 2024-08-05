package com.medical.medical;

import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.entity.Patient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MedicalApplicationTests {

	@Test
	void getAgeUser() {
		try {
			Patient p1=new Patient();
			System.out.println(p1.getAge());
		}
		catch(UserException ignored){
			System.out.println(ignored.getMessage());
		}
	}


}
