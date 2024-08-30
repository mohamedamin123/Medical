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


    public static String  login(String email, String password, String role) throws Exception {
        String urlString = "http://localhost:8081/"+role.toLowerCase()+"s"+"/"+role.toLowerCase()+"/find-by-email/" + email;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Ajouter l'en-tête d'authentification Basic
        String auth = email + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return "Failed : HTTP error code : " + responseCode;
        }
    }

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


}
