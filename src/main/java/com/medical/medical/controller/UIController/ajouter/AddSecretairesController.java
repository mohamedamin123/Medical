package com.medical.medical.controller.UIController.ajouter;

import com.medical.medical.controller.API.SecretaireController;
import com.medical.medical.ennum.Utilisateurs;
import com.medical.medical.models.dto.req.SecretaireReqDTO;
import com.medical.medical.models.dto.res.AdminResDTO;
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
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class AddSecretairesController {
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

    SecretaireReqDTO secretaireReqDTO;
    SecretaireResDTO secretaireResDTO;



//    @Autowired
//    private SecretaireController secretaireController;


    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) nom.getScene().getWindow();
            if (stage != null) {
                Object userData = stage.getUserData();
                if (userData instanceof Object[] data) {
                    admin = (AdminResDTO) data[0];
                    if(data.length>=2) {
                       if (data[1] instanceof SecretaireResDTO) {
                            secretaireResDTO = (SecretaireResDTO) data[1];
                            // Traitez le cas pour SecretaireResDTO ici
                        }
//                        enregistrer.setVisible(false);
//                       if(data.length>=3) {
//                            if(data[2]!=null)
//                            {
//                                int id = (int) data[2];
//                                if (id==1){
//                                    annuler.setVisible(false);
//                                    enregistrer.setVisible(false);
//                                }
//                            }
//                        }
                    }
                    if(secretaireResDTO!=null) {
                        nom.setText(secretaireResDTO.getNom());
                        prenom.setText(secretaireResDTO.getPrenom());
                        email.setText(secretaireResDTO.getEmail());
                        dateDeNaissance.setValue(secretaireResDTO.getDateDeNaissance());
                        tel.setText(secretaireResDTO.getTel());
                        password.setText("a1e2r35f");
                        password.setEditable(false);

                        nom.setEditable(false);
                        prenom.setEditable(false);
                        email.setEditable(false);
                        dateDeNaissance.setEditable(false);
                        tel.setEditable(false);
                        password.setEditable(false);
                        annuler.setVisible(false);
                        enregistrer.setVisible(false);
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

            if (admin != null) {
                secretaireReqDTO = SecretaireReqDTO.builder()
                        .nom(nom.getText())
                        .prenom(prenom.getText())
                        .email(email.getText())
                        .tel(tel.getText())
                        .password(password.getText())
                        .role(Utilisateurs.MEDECIN)
                        .dateDeNaissance(dateDeNaissance.getValue())
                        .build();

                try {
                    if(secretaireResDTO==null)
                    //secretaireController.saveSecretaire(secretaireReqDTO);
                    ResAPI.save("secretaire",secretaireReqDTO);

                    else
                    {
                        secretaireReqDTO.setIdMedecin(secretaireResDTO.getIdSecretaire());
                        //secretaireController.updateSecretaire(secretaireReqDTO);
                        ResAPI.update("secretaire",secretaireReqDTO);
                    }
                    Stage stage = (Stage) nom.getScene().getWindow();
                    stage.close();
                    changeFenetre("liste_secretaires",admin);
                    if(secretaireResDTO!=null) {
                        secretaireResDTO=null;
                    }



                } catch (Exception e) {
                    log.error("Error updating Secretaire profile", e);
                }

            }

        }
        }

    private void annulerBtn() throws IOException {
        Stage stage = (Stage) nom.getScene().getWindow();
        stage.close();
        changeFenetre("liste_secretaires",admin);

        if(secretaireResDTO!=null)
        {
            secretaireResDTO=null;


        }
    }
}
