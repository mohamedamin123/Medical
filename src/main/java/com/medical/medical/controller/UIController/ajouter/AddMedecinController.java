package com.medical.medical.controller.UIController.ajouter;

import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.ennum.Utilisateurs;
import com.medical.medical.models.dto.req.MedecinReqDTO;
import com.medical.medical.models.dto.res.AdminResDTO;
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
public class AddMedecinController {

    @FXML
    private Label specialiteError;
    @FXML
    private TextField specialite;
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
    private AdminResDTO admin;

    MedecinReqDTO medecin;

    MedecinResDTO medecinResDTOO;




    @Autowired
    private MedecinController medecinController;


    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) nom.getScene().getWindow();
            if (stage != null) {
                Object userData = stage.getUserData();
                if (userData instanceof Object[] data) {
                    admin = (AdminResDTO) data[0];
                    if(data.length==2) {
                        if (data[1] instanceof MedecinResDTO) {
                            medecinResDTOO = (MedecinResDTO) data[1];
                            // Traitez le cas pour MedecinResDTO ici
                        }
                    }

                    if(medecinResDTOO!=null) {
                        nom.setText(medecinResDTOO.getNom());
                        prenom.setText(medecinResDTOO.getPrenom());
                        email.setText(medecinResDTOO.getEmail());
                        dateDeNaissance.setValue(medecinResDTOO.getDateDeNaissance());
                        tel.setText(medecinResDTOO.getTel());
                        specialite.setText(medecinResDTOO.getSpecialite());
                        password.setText("a1e2r35f");
                        password.setEditable(false);
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

        String specialiteText = specialite.getText().trim();
        if (specialiteText.isEmpty()) {
            specialiteError.setText("Veuillez entrer un specialite valide");
            return;
        } else {
            specialiteError.setText("");
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
                specialiteError.getText().isEmpty() &&
                telError.getText().isEmpty() &&
                dateDeNaissanceError.getText().isEmpty() &&
                passwordError.getText().isEmpty()) {

            if (admin != null) {
                medecin = MedecinReqDTO.builder()
                        .nom(nom.getText())
                        .prenom(prenom.getText())
                        .email(email.getText())
                        .tel(tel.getText())
                        .password(password.getText())
                        .role(Utilisateurs.MEDECIN)
                        .dateDeNaissance(dateDeNaissance.getValue())
                        .specialite(specialite.getText())
                        .build();

                try {
                    if(medecinResDTOO==null)
                    medecinController.saveMedecin(medecin);
                    else
                    {
                        medecin.setIdMedecin(medecinResDTOO.getIdMedecin());
                        medecinController.updateMedecin(medecin);
                    }
                    Stage stage = (Stage) nom.getScene().getWindow();
                    stage.close();
                    if(medecinResDTOO!=null)
                    {
                        medecinResDTOO=null;
                        changeFenetre("liste_medecin",admin);
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

        if(medecinResDTOO!=null)
        {
            medecinResDTOO=null;
            changeFenetre("liste_medecin",admin);
        }
    }
}
