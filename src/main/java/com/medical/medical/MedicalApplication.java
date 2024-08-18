package com.medical.medical;


import com.medical.medical.controller.UIController.JavaFXApp;
import javafx.application.Application;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MedicalApplication {
	private static ApplicationContext context;

	public static void main(String[] args) {

		//SpringApplication.run(MedicalApplication.class, args);

		Application.launch(JavaFXApp.class, args);
//
//		context = (ApplicationContext) SpringApplication.run(MedicalApplication.class, args);
//		SpringFXMLLoader.setApplicationContext((org.springframework.context.ApplicationContext) context);
	}

}
