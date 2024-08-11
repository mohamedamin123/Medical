package com.medical.medical;


import com.medical.medical.controller.UIController.JavaFXApp;
import javafx.application.Application;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicalApplication {

	public static void main(String[] args) {

		//SpringApplication.run(MedicalApplication.class, args);

		Application.launch(JavaFXApp.class, args);

	}

}
