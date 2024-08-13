package com.medical.medical.controller.UIController;

import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.controller.API.SecretaireController;
import com.medical.medical.utils.javaFxAPI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class LoginController {

    @Autowired
    private MedecinController medecinController;

    @Autowired
    private SecretaireController secretaireController;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @FXML
    private Button myButton;
    @FXML
    private TextField emailConnecter;
    @FXML
    private PasswordField passwordConnecter;

    @FXML
    public void initialize() {
        myButton.setOnAction(event -> {
            String email = emailConnecter.getText().trim();
            String password = passwordConnecter.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                log.info("Email or password is empty");
            } else {
                try {
                    Optional<String> passwordM = medecinController.findPasswordByEmail(email);
                    Optional<String> passwordS = secretaireController.findPasswordByEmail(email);

                    if (passwordM.isPresent()) {
                        if (passwordEncoder.matches(password, passwordM.get())) {
                            String response = javaFxAPI.login(email, password, "medecin");
                            log.info(response);
                            log.info("medecin");
                            // Close the current window
                            Stage stage = (Stage) myButton.getScene().getWindow();
                            stage.close();

                            // Load the Accueil window
                            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/templates/acceuil.fxml"));
                            Parent root = fxmlLoader.load();
                            Stage newStage = new Stage();
                            newStage.setTitle("Accueil");
                            newStage.setScene(new Scene(root));
                            newStage.show();
                        } else {
                            log.info("Invalid password for Medecin");
                        }
                    }
                    else if (passwordS.isPresent()) {
                        if (passwordEncoder.matches(password, passwordS.get())) {
                            String response = javaFxAPI.login(email, password, "secretaire");
                            log.info(response);
                            log.info("secretaire");

                        } else {
                            log.info("Invalid password for Secretaire");
                        }
                    } else {
                        log.info("Invalid password ");
                    }
                } catch (Exception e) {
                    log.error("An error occurred: ", e);
                }
            }
        });
    }

}
