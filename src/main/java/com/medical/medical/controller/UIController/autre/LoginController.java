package com.medical.medical.controller.UIController.autre;

import com.medical.medical.models.dto.res.AdminResDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.utils.ResAPI;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

@Component
@Slf4j
public class LoginController {

    @Getter
    private static String savedPassword;
    @Getter
    private static String savedEmail;
    @Getter
    private static String savedRole;

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
                loginErreur.setText("L'email et le mot de passe sont vides");
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
                try {
                    // Try to login as Secretaire
                    Optional<SecretaireResDTO> secretaireResDTO = Optional.ofNullable(ResAPI.login("secretaire", email, password, SecretaireResDTO.class));
                    if (secretaireResDTO.isPresent()) {
                        log.info("Secretaire found");
                        if (passwordEncoder.matches(password, secretaireResDTO.get().getPassword())) {
                            if (secretaireResDTO.get().getStatut()) {
                                savedPassword = password;
                                savedEmail = email;
                                savedRole ="secretaire";
                                changeFenetre(email, "secretaire", null, secretaireResDTO.orElse(null));
                                return;
                            } else {
                                showAlert(Alert.AlertType.WARNING, "Impossible", "Vous ne pouvez pas accéder à cette interface !");
                                return;
                            }
                        } else {
                            log.info("Invalid password for Secretaire");
                            loginErreur.setText("Mot de passe invalide pour Secretaire");
                            loginErreur.setVisible(true);
                            return;
                        }
                    }

                    // Try to login as Medecin
                    Optional<MedecinResDTO> medecinResDTO = Optional.ofNullable(ResAPI.login("medecin", email, password, MedecinResDTO.class));
                    if (medecinResDTO.isPresent()) {
                        log.info("Medecin found");
                        if (passwordEncoder.matches(password, medecinResDTO.get().getPassword())) {
                            if (medecinResDTO.get().getStatut()) {
                                savedPassword = password;
                                savedEmail = email;
                                savedRole ="medecin";
                                changeFenetre(email, "medecin", medecinResDTO.orElse(null), null);
                                return;
                            } else {
                                showAlert(Alert.AlertType.WARNING, "Impossible", "Vous ne pouvez pas accéder à cette interface !");
                                return;
                            }
                        } else {
                            log.info("Invalid password for Medecin");
                            loginErreur.setText("Mot de passe invalide pour Médecin");
                            loginErreur.setVisible(true);
                            return;
                        }
                    }


                    // Try to login as Admin
                    Optional<AdminResDTO> adminResDTO = Optional.ofNullable(ResAPI.login("admin", email, password, AdminResDTO.class));
                    if (adminResDTO.isPresent()) {
                        log.info("Admin found");
                        if (passwordEncoder.matches(password, adminResDTO.get().getPassword())) {
                            savedPassword = password;
                            savedEmail = email;
                            savedRole ="admin";
                            changeFenetre(email, "admin", adminResDTO.get());
                            return;
                        } else {
                            log.info("Invalid password for Admin");
                            loginErreur.setText("Mot de passe invalide pour Admin");
                            loginErreur.setVisible(true);
                            return;
                        }
                    }
                } catch (Exception e) {
                    loginErreur.setText("L'email ou/et le mot de passe sont incorrects");
                    loginErreur.setVisible(true);
                }
            }
        });
    }

    private void changeFenetre(String email, String role, MedecinResDTO medecin, SecretaireResDTO secretaire) throws IOException {
        Stage stage = (Stage) myButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/templates/acceuil.fxml"));
        Parent root = fxmlLoader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Accueil");
        newStage.setScene(new Scene(root));
        newStage.setUserData(new Object[]{email, role, medecin, secretaire});
        newStage.show();
    }

    private void changeFenetre(String email, String role, AdminResDTO adminResDTO) throws IOException {
        Stage stage = (Stage) myButton.getScene().getWindow();
        stage.close();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/templates/home_admin.fxml"));
        Parent root = fxmlLoader.load();
        Stage newStage = new Stage();
        newStage.setTitle("Accueil");
        newStage.setScene(new Scene(root));
        newStage.setUserData(new Object[]{email, role, adminResDTO});
        newStage.show();
    }

    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
