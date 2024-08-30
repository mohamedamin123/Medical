package com.medical.medical.controller.UIController.user;

import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.controller.API.SecretaireController;
import com.medical.medical.models.dto.req.MedecinReqDTO;
import com.medical.medical.models.dto.req.SecretaireReqDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class ProfileController {
    @FXML
    private Button annuler;
    @FXML
    private Button enregistrer;
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

    @FXML
    private Label prenomError;
    @FXML
    private Label nomError;
    @FXML
    private Label emailError;
    @FXML
    private Label telError;
    @FXML
    private Label dateDeNaissanceError;

    private MedecinResDTO medecin;
    private SecretaireResDTO secretaire;
    private MedecinReqDTO medecinReqDTO;

    private SecretaireReqDTO secretaireReqDTO;

    @Autowired
    private MedecinController medecinController;

    @Autowired
    private SecretaireController secretaireController;

    @FXML
    public void initialize() {
        init();
        enregistrer.setOnAction(event -> {
            try {
                mettreAjour();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        annuler.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    annulerBtn();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

    }

    @FXML
    private void mettreAjour() throws IOException {
        if (medecinController == null || secretaireController == null) {
            log.error("Controllers not initialized");
            return;
        }

        boolean isUpdated = false;

        // Validate and format the "nom" TextField input
        String nomN = nom.getText().trim();
        if (!nomN.isEmpty()) {
            nomN = nomN.substring(0, 1).toUpperCase() + nomN.substring(1).toLowerCase();
            nom.setText(nomN);
            nomError.setText("");
        } else {
            nomError.setText("Nom ne peut pas être vide");
            return;
        }

        // Validate the "prenom" field similarly
        String prenomN = prenom.getText().trim();
        if (!prenomN.isEmpty()) {
            prenomN = prenomN.substring(0, 1).toUpperCase() + prenomN.substring(1).toLowerCase();
            prenom.setText(prenomN);
            prenomError.setText("");
        } else {
            prenomError.setText("Prénom ne peut pas être vide");
            return;
        }

        // Validate the email field
        String emailText = email.getText().trim();
        if (emailText.isEmpty() || !emailText.matches("^(.+)@(.+)$")) {
            emailError.setText("Veuillez entrer un email valide");
            return;
        } else {
            emailError.setText("");
        }

        // Validate the telephone field
        String telText = tel.getText().trim();
        if (telText.isEmpty() || !telText.matches("\\d{8}")) {
            telError.setText("Veuillez entrer un numéro de téléphone valide");
            return;
        } else {
            telError.setText("");
        }

        // Validate and set the date of birth field
        LocalDate selectedDate = dateDeNaissance.getValue();
        if (selectedDate == null || selectedDate.isAfter(LocalDate.now())) {
            dateDeNaissanceError.setText("Veuillez sélectionner une date de naissance valide");
            return;
        } else {
            dateDeNaissanceError.setText("");
        }

        if (nomError.getText().isEmpty() &&
                prenomError.getText().isEmpty() &&
                emailError.getText().isEmpty() &&
                telError.getText().isEmpty() &&
                dateDeNaissanceError.getText().isEmpty()) {

            if (medecin != null) {
                 medecinReqDTO = MedecinReqDTO.builder()
                        .nom(nom.getText())
                        .prenom(prenom.getText())
                        .email(email.getText())
                        .tel(tel.getText())
                        .dateDeNaissance(dateDeNaissance.getValue())
                        .build();

                try {
                    medecinController.updateMedecin(medecinReqDTO);
                    log.info("Profile updated for Medecin: {}", medecinReqDTO.getNom());
                    isUpdated = true;
                } catch (Exception e) {
                    log.error("Error updating Medecin profile", e);
                }

            } else if (secretaire != null) {
                 secretaireReqDTO = SecretaireReqDTO.builder()
                        .nom(nom.getText())
                        .prenom(prenom.getText())
                        .email(email.getText())
                        .tel(tel.getText())
                        .dateDeNaissance(dateDeNaissance.getValue())
                        .build();

                try {
                    secretaireController.updateSecretaire(secretaireReqDTO);
                    log.info("Profile updated for Secretaire: {}", secretaireReqDTO.getNom());
                    isUpdated = true;
                } catch (Exception e) {
                    log.error("Error updating Secretaire profile", e);
                }
            }

            if (isUpdated) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Mise à jour réussie");
                alert.setHeaderText(null);
                alert.setContentText("Le profil a été mis à jour avec succès!");
                alert.showAndWait();

                Stage stage = (Stage) nom.getScene().getWindow();
                stage.close();


                if(medecin==null)
                {

                    secretaire.setNom(secretaireReqDTO.getNom());
                    secretaire.setPrenom(secretaireReqDTO.getPrenom());
                    secretaire.setTel(secretaireReqDTO.getTel());
                    secretaire.setDateDeNaissance(secretaireReqDTO.getDateDeNaissance());
                    changeFenetre("acceuil",secretaire.getEmail(),"secretaire",medecin,secretaire);
                }

                else {
                    medecin.setNom(medecinReqDTO.getNom());
                    medecin.setPrenom(medecinReqDTO.getPrenom());
                    medecin.setTel(medecinReqDTO.getTel());
                    medecin.setDateDeNaissance(medecinReqDTO.getDateDeNaissance());
                    changeFenetre("acceuil",medecin.getEmail(),"medecin",medecin,secretaire);
                }



            }
        }
    }

    private void init() {

        dateDeNaissance.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (date.isAfter(LocalDate.now())) {
                    setDisable(true);
                    setStyle("-fx-background-color: #ffc0cb;");
                }
            }
        });

        Platform.runLater(() -> {
            Stage stage = (Stage) nom.getScene().getWindow();
            if (stage != null) {
                Object userData = stage.getUserData();
                if (userData instanceof Object[] data) {
                    medecin = (MedecinResDTO) data[2];
                    secretaire = (SecretaireResDTO) data[3];

                    if (medecin != null && medecin.getNom() != null) {
                        nom.setText(medecin.getNom());
                        prenom.setText(medecin.getPrenom());
                        email.setText(medecin.getEmail());
                        tel.setText(medecin.getTel());

                        if (medecin.getDateDeNaissance() != null) {
                            dateDeNaissance.setValue(medecin.getDateDeNaissance());
                        }
                    } else if (secretaire != null && secretaire.getNom() != null) {
                        nom.setText(secretaire.getNom());
                        prenom.setText(secretaire.getPrenom());
                        email.setText(secretaire.getEmail());
                        tel.setText(secretaire.getTel());

                        if (secretaire.getDateDeNaissance() != null) {
                            dateDeNaissance.setValue(secretaire.getDateDeNaissance());
                        }
                    }
                }
            }
        });
    }


    private void annulerBtn() throws IOException {
        Stage stage = (Stage) nom.getScene().getWindow();
        stage.close();
        if(medecin==null)
        {

            changeFenetre("acceuil",secretaire.getEmail(),"secretaire",medecin,secretaire);
        }

        else {
            changeFenetre("acceuil",medecin.getEmail(),"medecin",medecin,secretaire);
        }
    }
}
