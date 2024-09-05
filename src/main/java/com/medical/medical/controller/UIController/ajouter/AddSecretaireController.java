package com.medical.medical.controller.UIController.ajouter;

import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.controller.API.SecretaireController;
import com.medical.medical.ennum.Utilisateurs;
import com.medical.medical.models.dto.req.MedecinReqDTO;
import com.medical.medical.models.dto.req.SecretaireReqDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.utils.ResAPI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class AddSecretaireController {
    @FXML
    private Label dateDeNaissanceError;
    @FXML
    private DatePicker dateDeNaissance;
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
    private PasswordField password;

    @FXML
    private Label prenomError;
    @FXML
    private Label nomError;
    @FXML
    private Label emailError;
    @FXML
    private Label telError;
    @FXML
    private Label passwordError;
    private MedecinResDTO medecin;
    private SecretaireResDTO secretaire;

    private Integer idM;
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



    private SecretaireReqDTO secretaireReqDTO;

    private SecretaireResDTO secret;


//    @Autowired
//    private MedecinController medecinController;
//
//    @Autowired
//    private SecretaireController secretaireController;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) nom.getScene().getWindow();
            if (stage != null) {
                Object userData = stage.getUserData();
                if (userData instanceof Object[] data) {
                    medecin = (MedecinResDTO) data[2];
                    idM = (Integer) data[4];

                    if (data.length > 5 && data[5] instanceof SecretaireResDTO) {
                        secret = (SecretaireResDTO) data[5];
                    } else {
                        secret = null; // or handle the case when data[5] does not exist or is not of type PatientResDTO
                    }

                    if(secret!=null) {
                        nom.setText(secret.getNom());
                        prenom.setText(secret.getPrenom());
                        email.setText(secret.getEmail());
                        dateDeNaissance.setValue(secret.getDateDeNaissance());
                        tel.setText(secret.getTel());
                    }

                }
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

    });
    }

    @FXML
    private void mettreAjour() throws IOException {

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

        // Validate and set the date of birth field
        String pass = password.getText();
        if (pass == null) {
            passwordError.setText("Veuillez sélectionner une mot de passe valide");
            return;
        } else {
            passwordError.setText("");
        }

        if (nomError.getText().isEmpty() &&
                prenomError.getText().isEmpty() &&
                emailError.getText().isEmpty() &&
                telError.getText().isEmpty() &&
                dateDeNaissanceError.getText().isEmpty() &&
                passwordError.getText().isEmpty()) {

            if (medecin != null) {
                secretaireReqDTO = SecretaireReqDTO.builder()
                        .nom(nom.getText())
                        .prenom(prenom.getText())
                        .email(email.getText())
                        .tel(tel.getText())
                        .password(password.getText())
                        .role(Utilisateurs.SECRETAIRE)
                        .dateDeNaissance(dateDeNaissance.getValue())
                        .idMedecin(idM)
                        .build();

                try {
                    if(secret==null){
                        //secretaireController.saveSecretaire(secretaireReqDTO);
                        ResAPI.save("secretaire",secretaireReqDTO);
                        Stage stage = (Stage) nom.getScene().getWindow();
                        stage.close();
                        changeFenetre("secretaire",medecin.getEmail(),"medecin",medecin,secretaire,idM);
                    }else {
                        if(passwordEncoder.matches(password.getText(),secret.getPassword())) {
                            secretaireReqDTO.setIdSecretaire(secret.getIdSecretaire());
                            //secretaireController.updateSecretaire(secretaireReqDTO);
                            ResAPI.update("secretaire",secretaireReqDTO);

                            Stage stage = (Stage) nom.getScene().getWindow();
                            stage.close();
                            changeFenetre("secretaire",medecin.getEmail(),"medecin",medecin,secretaire,idM);
                        } else {
                            showAlert(Alert.AlertType.WARNING, "Impossible", "Entrer votre mot de passe ");

                        }

                    }

                } catch (Exception e) {
                    log.error("Error updating Medecin profile", e);
                }

            }

        }
        }

    private void annulerBtn() throws IOException {
        Stage stage = (Stage) nom.getScene().getWindow();
        stage.close();

            changeFenetre("secretaire",medecin.getEmail(),"medecin",medecin,secretaire,idM);

    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
