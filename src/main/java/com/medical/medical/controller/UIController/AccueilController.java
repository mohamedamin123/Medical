package com.medical.medical.controller.UIController;

import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.controller.API.SecretaireController;
import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
                     email = (String) data[0];
                     role = (String) data[1];
                     medecin = (MedecinResDTO) data[2];
                     secretaire = (SecretaireResDTO) data[3];
                getFullNames(medecin,secretaire,email,role);


                    profile.setOnMouseClicked(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            try {
                                changeFenetre("profile");
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }
                    });






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

    private void getFullNames(MedecinResDTO medecin,SecretaireResDTO secretaire,String email, String role ) {
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
                        throw new RuntimeException(e);
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

    private void changeFenetre(String destination) throws IOException {
        // Load the new scene
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/templates/"+destination+".fxml"));
        Parent root = fxmlLoader.load();
        Stage newStage = new Stage();
        newStage.setTitle(destination);
        newStage.setScene(new Scene(root));

        newStage.setUserData(new Object[]{ medecin, secretaire});

        newStage.show();

    }
}
