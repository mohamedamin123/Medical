package com.medical.medical.controller.UIController.autre;

import com.medical.medical.controller.API.AdminController;
import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.controller.API.SecretaireController;
import com.medical.medical.ennum.Utilisateurs;
import com.medical.medical.models.dto.res.AdminResDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.utils.javaFxAPI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class LoginController {

    @Autowired
    private MedecinController medecinController;

    @Autowired
    private SecretaireController secretaireController;

    @Autowired
    private AdminController adminController;



    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @FXML
    private Button myButton;
    @FXML
    private TextField emailConnecter;
    @FXML
    private PasswordField passwordConnecter;
    @FXML
    private Label loginErreur;

    @FXML
    public void initialize() {
        myButton.setOnAction(event -> {
            loginErreur.setVisible(false);

            String email = emailConnecter.getText().trim();
            String password = passwordConnecter.getText().trim();

            if (email.isEmpty() && password.isEmpty()) {
                log.info("Email or password is empty");
                loginErreur.setText("L'email et le mot de passe sont vide");
                loginErreur.setVisible(true);

            } else if (email.isEmpty()) {
                log.info("Email is empty");
                loginErreur.setText("L'email est vide");
                loginErreur.setVisible(true);
            } else if (password.isEmpty()) {
                log.info("Password is empty");
                loginErreur.setText("Mot de passe est vide");
                loginErreur.setVisible(true);
            } else {
                Optional<MedecinResDTO> medecinResDTO = medecinController.findMedecinByEmail(email);
                Optional<SecretaireResDTO> secretaireResDTO = secretaireController.findSecretaireByEmail(email);
                Optional<AdminResDTO> adminResDTO = adminController.findAdminByEmail(email);


                try {
                    Optional<String> passwordM = medecinController.findPasswordByEmail(email);
                    Optional<String> passwordS = secretaireController.findPasswordByEmail(email);
                    Optional<String> passwordA = adminController.findPasswordByEmail(email);


                    if (passwordM.isPresent()) {
                        if (passwordEncoder.matches(password, passwordM.get())) {
                            String response = javaFxAPI.login(email, password, "medecin");
                            log.info(response);
                            log.info("medecin");
                            changeFenetre(email, "medecin", medecinResDTO.orElse(null), null);

                        } else {
                            log.info("Invalid password for Medecin");
                            loginErreur.setText("Mot de passe invalide pour Médecin");
                            loginErreur.setVisible(true);
                        }
                    } else if (passwordS.isPresent()) {
                        if (passwordEncoder.matches(password, passwordS.get())) {
                            String response = javaFxAPI.login(email, password, "secretaire");
                            log.info(response);
                            log.info("secretaire");
                            changeFenetre(email, "secretaire", null, secretaireResDTO.orElse(null));

                        } else {
                            log.info("Invalid password for Secretaire");
                            loginErreur.setText("Mot de passe invalide pour Secretaire");
                            loginErreur.setVisible(true);
                        }
                    } else if (passwordA.isPresent()) {
                        if (passwordEncoder.matches(password, passwordA.get())) {
                            String response = javaFxAPI.login(email, password, "ADMIN");
                            log.info(response);
                            log.info("admin");
                            changeFenetre(email, "admin", adminResDTO.get());

                        } else {
                            log.info("Invalid password for Secretaire");
                            loginErreur.setText("Mot de passe invalide pour Secretaire");
                            loginErreur.setVisible(true);
                        }
                    }

                    else {
                        log.info("Invalid email or password");
                        loginErreur.setText("L'email et le mot de passe sont incorrects");
                        loginErreur.setVisible(true);
                    }
                } catch (Exception e) {
                    log.error("An error occurred: ", e);
                }
            }
        });
    }

    private void changeFenetre(String email, String role, MedecinResDTO medecin, SecretaireResDTO secretaire) throws IOException {
        // Close the current window
        Stage stage = (Stage) myButton.getScene().getWindow();
        stage.close();

        // Load the new scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/templates/acceuil.fxml"));
        Parent root = fxmlLoader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Accueil");
        newStage.setScene(new Scene(root));

        // Set the user data
        newStage.setUserData(new Object[]{email, role, medecin, secretaire});

        // Show the new stage
        newStage.show();
    }


    private void changeFenetre(String email, String role, AdminResDTO adminResDTO) throws IOException {
        // Close the current window
        Stage stage = (Stage) myButton.getScene().getWindow();
        stage.close();

        // Load the new scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/templates/home_admin.fxml"));
        Parent root = fxmlLoader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Accueil");
        newStage.setScene(new Scene(root));

        // Set the user data
        newStage.setUserData(new Object[]{email, role, adminResDTO});

        // Show the new stage
        newStage.show();
    }

}
