package com.medical.medical.controller.UIController;

import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.controller.API.SecretaireController;
import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class AccueilController {

    @FXML
    private ImageView profile;

    @FXML
    private ImageView secretaireI;

    @FXML
    private ImageView rendezVous;

    @FXML
    private ImageView patient;

    @FXML
    private Label name;

    @Autowired
    private MedecinController medecinController;

    @Autowired
    private SecretaireController secretaireController;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String role;

    private MedecinResDTO medecin;
    private SecretaireResDTO secretaire;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) name.getScene().getWindow();
            if (stage != null) {
                Object userData = stage.getUserData();
                if (userData instanceof Object[] data) {
                    if (data.length >= 4) {
                        System.out.println("data : "+email+ " "+role);
                        email = (String) data[0];
                        role = (String) data[1];
                        medecin = (data[2] instanceof MedecinResDTO) ? (MedecinResDTO) data[2] : null;
                        secretaire = (data[3] instanceof SecretaireResDTO) ? (SecretaireResDTO) data[3] : null;
                        getFullNames(medecin, secretaire, email, role);

                        profile.setOnMouseClicked(event -> {
                            try {
                                changeFenetre("profile", email, role, medecin, secretaire);
                                stage.close();
                            } catch (IOException e) {
                                log.error("Error changing window", e);
                            }
                        });

                        patient.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                setPatient();
                            }
                        });

                        secretaireI.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                setSecretaire();
                            }
                        });

                        rendezVous.setOnMouseClicked(new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                setRendezVous();
                            }
                        });


                    } else {
                        log.error("User data array does not have the expected number of elements");
                        name.setText("User data array does not have the expected number of elements");
                    }
                } else {
                    log.error("User data is not in the expected format");
                    name.setText("User data is not in the expected format");
                }
            } else {
                log.error("Stage is null");
                name.setText("Stage is null");
            }
        });
    }

    private void setRendezVous() {
        log.info("rendez");
    }

    private void setSecretaire() {
        log.info("secretaire");
    }


    private void getFullNames(MedecinResDTO medecin, SecretaireResDTO secretaire, String email, String role) {
        if (email != null && role != null) {
            log.info("Email: " + email);
            log.info("Role: " + role);

            if (role.equals("medecin")) {
                if (medecin != null) {
                    try {
                        name.setText("Bonjour Dr. " + medecin.getFullName());
                    } catch (UserException e) {
                        log.error("Error retrieving Medecin details", e);
                        name.setText("Error retrieving Medecin details");
                    }
                } else {
                    log.error("Medecin data is null for email: " + email);
                    name.setText("Medecin data is not available");
                }
            } else if (role.equals("secretaire")) {
                if (secretaire != null) {
                    try {
                        name.setText("Bonjour " + secretaire.getFullName());
                    } catch (UserException e) {
                        log.error("Error retrieving Secretaire details", e);
                        name.setText("Error retrieving Secretaire details");
                    }
                } else {
                    log.error("Secretaire data is null for email: " + email);
                    name.setText("Secretaire data is not available");
                }
            } else {
                log.error("Unknown role: " + role);
                name.setText("Unknown role");
            }
        } else {
            log.error("Email or Role is null");
            name.setText("Email or Role is null");
        }
    }

    private void setPatient() {
        log.info("patient");

    }

}
