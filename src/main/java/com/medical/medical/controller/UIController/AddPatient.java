package com.medical.medical.controller.UIController;

import com.medical.medical.controller.API.DossierMedicalController;
import com.medical.medical.controller.API.PatientController;
import com.medical.medical.controller.API.SecretaireController;
import com.medical.medical.ennum.Sexe;
import com.medical.medical.models.dto.req.DossierMedicalReqDTO;
import com.medical.medical.models.dto.req.PatientReqDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.Objects;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class AddPatient {




    @FXML
    private VBox fileListContainer;

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

    @Autowired
    private DossierMedicalController dossierMedicalController;


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

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Documents", "*.pdf", "*.docx", "*.doc"),
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            HBox fileHBox = new HBox(10);
            fileHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            Label fileNameLabel = new Label(selectedFile.getAbsolutePath()); // Store full path
            fileHBox.getChildren().add(fileNameLabel);

            Button deleteButton = new Button();
            ImageView deleteIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/static/icons/supprimer.png")).toExternalForm()));
            deleteIcon.setFitWidth(16);
            deleteIcon.setFitHeight(16);
            deleteButton.setGraphic(deleteIcon);
            deleteButton.setOnAction(event -> fileListContainer.getChildren().remove(fileHBox));
            fileHBox.getChildren().add(deleteButton);

            Button openButton = new Button("Ouvrir");
            openButton.setOnAction(event -> {
                Platform.runLater(() -> {
                    try {
                        if (Desktop.isDesktopSupported()) {
                            Desktop desktop = Desktop.getDesktop();
                            if (desktop.isSupported(Desktop.Action.OPEN)) {
                                desktop.open(selectedFile);
                            } else {
                                showAlert(Alert.AlertType.ERROR, "Erreur", "L'ouverture du fichier n'est pas supportée sur ce système.");
                            }
                        } else {
                            showAlert(Alert.AlertType.ERROR, "Erreur", "Le support Desktop n'est pas disponible.");
                        }
                    } catch (IOException e) {
                        showAlert(Alert.AlertType.ERROR, "Erreur", "Erreur lors de l'ouverture du fichier: " + e.getMessage());
                    }
                });
            });
            fileHBox.getChildren().add(openButton);

            fileListContainer.getChildren().add(fileHBox);
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
        String batiment = (batimentComboBox.getValue() != null) ? batimentComboBox.getValue().trim() : "aucun"; // Avoid NullPointerException
        String code = codeField.getText().trim();
        String notes = notesField.getText().trim();
        String ville = villeField.getText().trim();

        String sexeValue = ((RadioButton) sexe.getSelectedToggle()).getText();
        Sexe sexee = sexeValue.equals("Homme") ? Sexe.HOMME : Sexe.FEMME;



        // Validation
        if (nom.isEmpty() || prenom.isEmpty()  || dateNaissance == null || telephone.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Erreur de Validation", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Validation des autres champs (email, CIN, téléphone, etc.)
        if(!cin.isEmpty()) {
            if (!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
                showAlert(Alert.AlertType.ERROR, "Erreur de Validation", "L'adresse email est invalide.");
                return;
            }
        }
        if(!cin.isEmpty()) {
            if (cin.length() != 8 || !cin.matches("\\d+")) {
                showAlert(Alert.AlertType.ERROR, "Erreur de Validation", "Le CIN doit comporter exactement 8 chiffres.");
                return;
            }
        }

        if (telephone.length() != 8 || !telephone.matches("\\d+")) {
            showAlert(Alert.AlertType.ERROR, "Erreur de Validation", "Le numéro de téléphone doit comporter exactement 8 chiffres.");
            return;
        }

        PatientReqDTO patientReqDTO=new PatientReqDTO(nom,prenom,telephone,email,dateNaissance,notes,batiment,code,cin,ville,sexee);
        PatientResDTO patientResDTO=patientController.savePatient(patientReqDTO);

        for (var node : fileListContainer.getChildren()) {
            if (node instanceof HBox) {
                HBox fileHBox = (HBox) node;
                Label fileNameLabel = (Label) fileHBox.getChildren().get(0);
                String filePath = fileNameLabel.getText(); // Use the full path

                File file = new File(filePath);
                byte[] fileContent = new byte[0];
                try {
                    fileContent = Files.readAllBytes(file.toPath());
                } catch (IOException e) {
                    showAlert(Alert.AlertType.ERROR, "Erreur de Lecture de Fichier", "Erreur lors de la lecture du fichier: " + e.getMessage());
                    continue;
                }

                System.out.println("id : "+patientResDTO.getIdPatient());
                String fileExtension = getFileExtension(file.getName());
                DossierMedicalReqDTO dossierMedicalReqDTO = new DossierMedicalReqDTO(fileExtension,fileNameLabel.getText(), fileContent, /* patient ID if needed */ patientResDTO.getIdPatient());

                // Call dossierMedicalController to save the file
                dossierMedicalController.saveDossierMedical(dossierMedicalReqDTO);
                stage.close();
                try {
                    changeFenetre("patient");
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private String getFileExtension(String fileName) {
        int index = fileName.lastIndexOf('.');
        return (index > 0) ? fileName.substring(index + 1).toLowerCase() : "";
    }

}
