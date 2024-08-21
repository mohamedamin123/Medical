package com.medical.medical.controller.UIController;

import com.medical.medical.controller.API.PatientController;
import com.medical.medical.controller.API.SecretaireController;
import com.medical.medical.ennum.Sexe;
import com.medical.medical.models.dto.req.PatientReqDTO;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class AddPatient {

    @FXML
    private TextField villeField;
    @FXML
    private Button annulerButton;
    @FXML
    private TextField nomField;
    @FXML
    private TextField prenomField;
    @FXML
    private TextField CINField;
    @FXML
    private DatePicker dobField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField TelField;
    @FXML
    private ComboBox<String> batimentComboBox;
    @FXML
    private TextField codeField;
    @FXML
    private TextArea notesField;
    @FXML
    private Button chargerFichierButton;
    @FXML
    private Button saveButton;
    @FXML
    private ToggleGroup sexe;
    private Stage stage;


    @Autowired
    private PatientController patientController;


    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) CINField.getScene().getWindow();

            // Restrict CINField to 8 digits only
            CINField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 8) {
                    CINField.setText(newValue.substring(0, 8));
                }
                if (!newValue.matches("\\d*")) {
                    CINField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });

            // Restrict TelField to 8 digits only
            TelField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (newValue.length() > 8) {
                    TelField.setText(newValue.substring(0, 8));
                }
                if (!newValue.matches("\\d*")) {
                    TelField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            });

            // Setup file chooser for "Charger fichier" button
            chargerFichierButton.setOnAction(event -> handleChargerFichier());

            // Setup save button action
            saveButton.setOnAction(event -> handleSave());

            annulerButton.setOnAction(event -> {
                try {
                    stage.close();
                    changeFenetre("patient");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            });


        });


    }

    @FXML
    private void handleChargerFichier() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger un fichier");

        // Set file type filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Documents", "*.pdf", "*.docx", "*.doc"),
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Handle the file (e.g., display the file name, process the file, etc.)
            System.out.println("Fichier sélectionné: " + selectedFile.getAbsolutePath());
        }
    }

    private void handleSave() {
        String nom = nomField.getText().trim().trim();
        if (!nom.isEmpty()) {
            nom = nom.substring(0, 1).toUpperCase() + nom.substring(1).toLowerCase();
            nomField.setText(nom);
        }

        String prenom = prenomField.getText().trim();

            if (!prenom.isEmpty()) {
                prenom = prenom.substring(0, 1).toUpperCase() + prenom.substring(1).toLowerCase();
                prenomField.setText(prenom);
            }

        String cin = CINField.getText().trim();
        LocalDate dateNaissance = dobField.getValue(); // Keep as LocalDate
        String email = emailField.getText().trim();
        String telephone = TelField.getText().trim();
        String batiment = batimentComboBox.getValue().trim();
        if(batiment.isEmpty()){
            batiment="aucun";
        }
        String code = codeField.getText().trim();
        String notes = notesField.getText().trim();
        String viles = villeField.getText().trim();

        String sexeValue = ((RadioButton) sexe.getSelectedToggle()).getText();
        Sexe sexee;
        if(sexeValue.equals("Homme"))
            sexee= Sexe.HOMME;
        else
            sexee=Sexe.FEMME;



        // Validation
        if (nom.isEmpty() || prenom.isEmpty() || cin.isEmpty() || dateNaissance==null || telephone.isEmpty()) {
            System.out.println("Veuillez remplir tous les champs obligatoires.");
            return;
        }

        if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
            System.out.println("L'adresse email est invalide.");
            return;
        }

        if (cin.length() != 8 || !cin.matches("\\d+")) {
            System.out.println("Le CIN doit comporter exactement 8 chiffres.");
            return;
        }

        if (telephone.length() != 8 || !telephone.matches("\\d+")) {
            System.out.println("Le numéro de téléphone doit comporter exactement 8 chiffres.");
            return;
        }

        // Display the information in the console
        log.info("Nom: " + nom);
        log.info("Prénom: " + prenom);
        log.info("CIN: " + cin);
        log.info("Date de Naissance: " + dateNaissance);
        log.info("Email: " + email);
        log.info("Téléphone: " + telephone);
        log.info("Bâtiment: " + batiment);
        log.info("Code Unique: " + code);
        log.info("Notes: " + notes);
        log.info("villle"+viles);
        PatientReqDTO patientReqDTO=new PatientReqDTO(nom,prenom,telephone,email,dateNaissance,notes,batiment,code,cin,viles,sexee);
        patientController.savePatient(patientReqDTO);

    }
}
