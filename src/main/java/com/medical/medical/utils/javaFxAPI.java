package com.medical.medical.utils;

import com.medical.medical.controller.UIController.JavaFXApp;
import com.medical.medical.models.dto.req.RendezVousReqDTO;
import com.medical.medical.models.dto.res.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;
@Slf4j
public class javaFxAPI {

    private static final String URL_API = "http://192.168.163.66:8081/";




    public static void changeFenetre(String destination) throws IOException {
        ConfigurableApplicationContext springContext = JavaFXApp.getSpringContext();
        if (springContext == null) {
            log.error("Spring context is not initialized");
            throw new IllegalStateException("Spring context is not initialized");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(javaFxAPI.class.getResource("/templates/" + destination + ".fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            log.error("Error loading FXML file for " + destination, e);
            throw e;
        }

        Object controller = fxmlLoader.getController();
        if (controller == null) {
            log.error("Controller is null");
            throw new IllegalStateException("Controller is not instantiated");
        }

        Stage newStage = new Stage();
        newStage.setTitle(destination);
        newStage.setScene(new Scene(root));

        // Pass user data to the new stage

        newStage.show();
    }

    public static void changeFenetre(String destination, String email, String role, MedecinResDTO medecin, SecretaireResDTO secretaire, Integer id) throws IOException {
        ConfigurableApplicationContext springContext = JavaFXApp.getSpringContext();
        if (springContext == null) {
            log.error("Spring context is not initialized");
            throw new IllegalStateException("Spring context is not initialized");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(javaFxAPI.class.getResource("/templates/" + destination + ".fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            log.error("Error loading FXML file for " + destination, e);
            throw e;
        }

        Object controller = fxmlLoader.getController();
        if (controller == null) {
            log.error("Controller is null");
            throw new IllegalStateException("Controller is not instantiated");
        }

        Stage newStage = new Stage();
        newStage.setTitle(destination);
        newStage.setScene(new Scene(root));

        // Pass user data to the new stage
        newStage.setUserData(new Object[]{email, role, medecin, secretaire,id});

        newStage.show();
    }


    public static void changeFenetre(String destination, String email, String role, MedecinResDTO medecin, SecretaireResDTO secretaire, Integer id, RendezVousReqDTO newRendezVous) throws IOException {
        ConfigurableApplicationContext springContext = JavaFXApp.getSpringContext();
        if (springContext == null) {
            log.error("Spring context is not initialized");
            throw new IllegalStateException("Spring context is not initialized");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(javaFxAPI.class.getResource("/templates/" + destination + ".fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            log.error("Error loading FXML file for " + destination, e);
            throw e;
        }

        Object controller = fxmlLoader.getController();
        if (controller == null) {
            log.error("Controller is null");
            throw new IllegalStateException("Controller is not instantiated");
        }

        Stage newStage = new Stage();
        newStage.setTitle(destination);
        newStage.setScene(new Scene(root));

        // Pass user data to the new stage
        newStage.setUserData(new Object[]{email, role, medecin, secretaire,id,newRendezVous});

        newStage.show();
    }



    public static void changeFenetre(String destination, String email, String role, MedecinResDTO medecin, SecretaireResDTO secretaire) throws IOException {
        ConfigurableApplicationContext springContext = JavaFXApp.getSpringContext();
        if (springContext == null) {
            log.error("Spring context is not initialized");
            throw new IllegalStateException("Spring context is not initialized");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(javaFxAPI.class.getResource("/templates/" + destination + ".fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            log.error("Error loading FXML file for " + destination, e);
            throw e;
        }

        Object controller = fxmlLoader.getController();
        if (controller == null) {
            log.error("Controller is null");
            throw new IllegalStateException("Controller is not instantiated");
        }

        Stage newStage = new Stage();
        newStage.setTitle(destination);
        newStage.setScene(new Scene(root));

        // Pass user data to the new stage
        newStage.setUserData(new Object[]{email, role, medecin, secretaire});

        newStage.show();
    }






    public static void changeFenetre(String destination, String email, String role, MedecinResDTO medecin, SecretaireResDTO secretaire, Integer id, PatientResDTO patientResDTO) throws IOException {
        ConfigurableApplicationContext springContext = JavaFXApp.getSpringContext();
        if (springContext == null) {
            log.error("Spring context is not initialized");
            throw new IllegalStateException("Spring context is not initialized");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(javaFxAPI.class.getResource("/templates/" + destination + ".fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            log.error("Error loading FXML file for " + destination, e);
            throw e;
        }

        Object controller = fxmlLoader.getController();
        if (controller == null) {
            log.error("Controller is null");
            throw new IllegalStateException("Controller is not instantiated");
        }

        Stage newStage = new Stage();
        newStage.setTitle(destination);
        newStage.setScene(new Scene(root));

        // Pass user data to the new stage
        newStage.setUserData(new Object[]{email, role, medecin, secretaire,id,patientResDTO});


        newStage.show();
    }



    public static void changeFenetre(String destination, String email, String role, MedecinResDTO medecin, SecretaireResDTO secretaire, Integer id, RendezVousResDTO newRendezVous) throws IOException {
        ConfigurableApplicationContext springContext = JavaFXApp.getSpringContext();
        if (springContext == null) {
            log.error("Spring context is not initialized");
            throw new IllegalStateException("Spring context is not initialized");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(javaFxAPI.class.getResource("/templates/" + destination + ".fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            log.error("Error loading FXML file for " + destination, e);
            throw e;
        }

        Object controller = fxmlLoader.getController();
        if (controller == null) {
            log.error("Controller is null");
            throw new IllegalStateException("Controller is not instantiated");
        }

        Stage newStage = new Stage();
        newStage.setTitle(destination);
        newStage.setScene(new Scene(root));

        // Pass user data to the new stage
        newStage.setUserData(new Object[]{email, role, medecin, secretaire,id,newRendezVous});

        newStage.show();
    }



    public static void changeFenetre(String destination, String email, String role, MedecinResDTO medecin, SecretaireResDTO secretaire, Integer id, SecretaireResDTO newRendezVous) throws IOException {
        ConfigurableApplicationContext springContext = JavaFXApp.getSpringContext();
        if (springContext == null) {
            log.error("Spring context is not initialized");
            throw new IllegalStateException("Spring context is not initialized");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(javaFxAPI.class.getResource("/templates/" + destination + ".fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            log.error("Error loading FXML file for " + destination, e);
            throw e;
        }

        Object controller = fxmlLoader.getController();
        if (controller == null) {
            log.error("Controller is null");
            throw new IllegalStateException("Controller is not instantiated");
        }

        Stage newStage = new Stage();
        newStage.setTitle(destination);
        newStage.setScene(new Scene(root));

        // Pass user data to the new stage
        newStage.setUserData(new Object[]{email, role, medecin, secretaire,id,newRendezVous});

        newStage.show();
    }

    public static void changeFenetre(String destination, AdminResDTO adminResDTO) throws IOException {
        ConfigurableApplicationContext springContext = JavaFXApp.getSpringContext();
        if (springContext == null) {
            log.error("Spring context is not initialized");
            throw new IllegalStateException("Spring context is not initialized");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(javaFxAPI.class.getResource("/templates/" + destination + ".fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            log.error("Error loading FXML file for " + destination, e);
            throw e;
        }

        Object controller = fxmlLoader.getController();
        if (controller == null) {
            log.error("Controller is null");
            throw new IllegalStateException("Controller is not instantiated");
        }

        Stage newStage = new Stage();
        newStage.setTitle(destination);
        newStage.setScene(new Scene(root));

        // Pass user data to the new stage
        newStage.setUserData(new Object[]{adminResDTO});


        newStage.show();
    }

    public static void changeFenetre(String destination, AdminResDTO adminResDTO,SecretaireResDTO secretaireResDTO) throws IOException {
        ConfigurableApplicationContext springContext = JavaFXApp.getSpringContext();
        if (springContext == null) {
            log.error("Spring context is not initialized");
            throw new IllegalStateException("Spring context is not initialized");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(javaFxAPI.class.getResource("/templates/" + destination + ".fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            log.error("Error loading FXML file for " + destination, e);
            throw e;
        }

        Object controller = fxmlLoader.getController();
        if (controller == null) {
            log.error("Controller is null");
            throw new IllegalStateException("Controller is not instantiated");
        }

        Stage newStage = new Stage();
        newStage.setTitle(destination);
        newStage.setScene(new Scene(root));

        // Pass user data to the new stage
        newStage.setUserData(new Object[]{adminResDTO,secretaireResDTO});


        newStage.show();
    }


    public static void changeFenetre(String destination, AdminResDTO adminResDTO,MedecinResDTO medecin) throws IOException {
        ConfigurableApplicationContext springContext = JavaFXApp.getSpringContext();
        if (springContext == null) {
            log.error("Spring context is not initialized");
            throw new IllegalStateException("Spring context is not initialized");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(javaFxAPI.class.getResource("/templates/" + destination + ".fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            log.error("Error loading FXML file for " + destination, e);
            throw e;
        }

        Object controller = fxmlLoader.getController();
        if (controller == null) {
            log.error("Controller is null");
            throw new IllegalStateException("Controller is not instantiated");
        }

        Stage newStage = new Stage();
        newStage.setTitle(destination);
        newStage.setScene(new Scene(root));

        // Pass user data to the new stage
        newStage.setUserData(new Object[]{adminResDTO,medecin});


        newStage.show();
    }


    public static void changeFenetre(String destination, AdminResDTO adminResDTO,SecretaireResDTO secretaireResDTO,int id) throws IOException {
        ConfigurableApplicationContext springContext = JavaFXApp.getSpringContext();
        if (springContext == null) {
            log.error("Spring context is not initialized");
            throw new IllegalStateException("Spring context is not initialized");
        }

        FXMLLoader fxmlLoader = new FXMLLoader(javaFxAPI.class.getResource("/templates/" + destination + ".fxml"));
        fxmlLoader.setControllerFactory(springContext::getBean);

        Parent root;
        try {
            root = fxmlLoader.load();
        } catch (IOException e) {
            log.error("Error loading FXML file for " + destination, e);
            throw e;
        }

        Object controller = fxmlLoader.getController();
        if (controller == null) {
            log.error("Controller is null");
            throw new IllegalStateException("Controller is not instantiated");
        }

        Stage newStage = new Stage();
        newStage.setTitle(destination);
        newStage.setScene(new Scene(root));

        // Pass user data to the new stage
        newStage.setUserData(new Object[]{adminResDTO,secretaireResDTO,id});


        newStage.show();
    }

}
