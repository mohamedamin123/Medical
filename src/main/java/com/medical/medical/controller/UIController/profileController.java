package com.medical.medical.controller.UIController;

import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.controller.API.SecretaireController;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@Slf4j
public class profileController {

    @FXML
    private TextField nom;
    @FXML
    private TextField prenom;
    @FXML
    private TextField email;
    @FXML
    private TextField tel;
    @FXML
    private DatePicker dateDeNaissance;

    private MedecinResDTO medecin;
    private SecretaireResDTO secretaire;

    @Autowired
    private MedecinController medecinController;

    @Autowired
    private SecretaireController secretaireController;

    @FXML
    public void initialize() {
        // Restrict date selection to past or present dates
        dateDeNaissance.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;"); // Optional: highlight future dates
                }
            }
        });

        // Initialize UI after stage is loaded
        Platform.runLater(() -> {
            Stage stage = (Stage) nom.getScene().getWindow();
            if (stage != null) {
                Object userData = stage.getUserData();
                if (userData instanceof Object[] data) {
                    medecin = (MedecinResDTO) data[0];
                    secretaire = (SecretaireResDTO) data[1];

                    if (medecin != null && medecin.getNom() != null) {
                        nom.setText(medecin.getNom());
                        prenom.setText(medecin.getPrenom());
                        email.setText(medecin.getEmail());
                        tel.setText(medecin.getTel());

                        // Set date in DatePicker
                        if (medecin.getDateDeNaissance() != null) {
                            dateDeNaissance.setValue(medecin.getDateDeNaissance());
                        }
                    } else if (secretaire != null && secretaire.getNom() != null) {
                        nom.setText(secretaire.getNom());
                        prenom.setText(secretaire.getPrenom());
                        email.setText(secretaire.getEmail());
                        tel.setText(secretaire.getTel());

                        // Set date in DatePicker
                        if (secretaire.getDateDeNaissance() != null) {
                            dateDeNaissance.setValue(secretaire.getDateDeNaissance());
                        }
                    }
                }
            }
        });
    }
}
