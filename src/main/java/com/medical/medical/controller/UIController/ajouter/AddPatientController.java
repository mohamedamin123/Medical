package com.medical.medical.controller.UIController.ajouter;

import com.medical.medical.controller.API.DossierMedicalController;
import com.medical.medical.controller.API.PatientController;
import com.medical.medical.ennum.Sexe;
import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.req.DossierMedicalReqDTO;
import com.medical.medical.models.dto.req.PatientReqDTO;
import com.medical.medical.models.dto.res.DossierMedicalResDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.utils.ResAPI;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.Objects;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class AddPatientController {
    @FXML
    private Label labelMedicament;
    @FXML
    private Button chargerMedicamentsButton;
    @FXML
    private ComboBox<String> maladieComboBox;
    @FXML
    private TextField ageField;
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
    private Integer idM;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String role;

    private MedecinResDTO medecin;
    private SecretaireResDTO secretaire;

    private PatientResDTO patientResDTO;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) CINField.getScene().getWindow();

            Object userData = stage.getUserData();
            if (userData instanceof Object[] data) {
                email = (String) data[0];
                role = (String) data[1];
                medecin = (data[2] instanceof MedecinResDTO) ? (MedecinResDTO) data[2] : null;
                secretaire = (data[3] instanceof SecretaireResDTO) ? (SecretaireResDTO) data[3] : null;
                idM = (Integer) data[4];
                if (data.length > 5 && data[5] instanceof PatientResDTO) {
                    patientResDTO = (PatientResDTO) data[5];
                } else {
                    patientResDTO = null; // or handle the case when data[5] does not exist or is not of type PatientResDTO
                }
            }
            getData();
            setData();
            enregistrerFichier();
            save();
            annuler();
            changeMedicament();
        });
    }

    private void enregistrerFichier() {
        chargerFichierButton.setOnAction(event -> handleChargerFichier());
    }

    private void setData() {
        CINField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.length() > 8) {
                CINField.setText(newValue.substring(0, 8));
            }
            if (!newValue.matches("\\d*")) {
                CINField.setText(newValue.replaceAll("[^\\d]", ""));
            }
        });

        dobField.valueProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                try {
                    ageField.setText(getAge(newValue));

                } catch (UserException e) {
                    throw new RuntimeException(e);
                }
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

    }

    private void getData() {
        if (patientResDTO != null) {
            nomField.setText(patientResDTO.getNom());
            prenomField.setText(patientResDTO.getPrenom());
            CINField.setText(patientResDTO.getCIN());
            dobField.setValue(patientResDTO.getDateDeNaissance());
            emailField.setText(patientResDTO.getEmail());
            TelField.setText(patientResDTO.getTel());
            villeField.setText(patientResDTO.getAdresse());
            batimentComboBox.setValue(patientResDTO.getRemboursement());
            codeField.setText(patientResDTO.getIdUnique());
            notesField.setText(patientResDTO.getNote());
            try {
                ageField.setText(String.valueOf(patientResDTO.getAge()));
            } catch (UserException e) {
                throw new RuntimeException(e);
            }

            Boolean maladie=patientResDTO.getMaladie();
            if(maladie){
                maladieComboBox.setValue("oui");
            }else {
                maladieComboBox.setValue("non");
            }


            Sexe sexeEnum = patientResDTO.getSexe();
            if (sexeEnum != null) {
                RadioButton selectedSexeButton = (sexeEnum == Sexe.HOMME) ? (RadioButton) sexe.getToggles().get(0) : (RadioButton) sexe.getToggles().get(1);
                selectedSexeButton.setSelected(true);
            }
            //List<DossierMedicalResDTO> dossierMedicalResDTOS= dossierMedicalController.findDossierMedicalByIdPatientAfterDelete(patientResDTO.getIdPatient());
            List<DossierMedicalResDTO> dossierMedicalResDTOS= null;
            try {
                dossierMedicalResDTOS = ResAPI.findAllByIdPatient("dossiermedical", DossierMedicalResDTO.class,patientResDTO.getIdPatient());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            if (dossierMedicalResDTOS != null) {
                for (DossierMedicalResDTO dossier : dossierMedicalResDTOS) {
                    HBox fileHBox = new HBox(10);
                    fileHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

                    Label fileNameLabel = new Label(dossier.getFichier()); // Display file name
                    fileHBox.getChildren().add(fileNameLabel);

                    Button deleteButton = new Button();
                    deleteButton.getStyleClass().add("delete-button"); // Ajouter la classe de style
                    ImageView deleteIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/static/icons/supprimer.png")).toExternalForm()));
                    deleteIcon.setFitWidth(16);
                    deleteIcon.setFitHeight(16);
                    deleteButton.setGraphic(deleteIcon);
                    deleteButton.setOnAction(event -> {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Confirmation de suppression");
                        alert.setHeaderText("Supprimer le fichier");
                        alert.setContentText("Êtes-vous sûr de vouloir supprimer ce fichier ?");

                        // Customize the buttons in the alert
                        ButtonType buttonYes = new ButtonType("Oui");
                        ButtonType buttonNo = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
                        alert.getButtonTypes().setAll(buttonYes, buttonNo);
                        alert.showAndWait().ifPresent(response -> {
                            if (response == buttonYes) {
                                // If the user clicked "Oui", proceed with deletion
                                fileListContainer.getChildren().remove(fileHBox);
                                try {
                                    ResAPI.deleteById("dossiermedical", dossier.getIdDossierMedical());
                                    ResAPI.findAfterDelete("dossiermedical", dossier.getIdDossierMedical(), DossierMedicalResDTO.class);
                                } catch (Exception e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        });
                    });
                    fileHBox.getChildren().add(deleteButton);

                    Button openButton = new Button("Ouvrir");
                    openButton.getStyleClass().add("open-button"); // Ajouter la classe de style
                    openButton.setOnAction(event -> {
                        // Chemin pour stocker temporairement le fichier
                        Path path = Paths.get(System.getProperty("java.io.tmpdir"), dossier.getFichier());

                        try {
                            // Récupère le contenu du fichier (LONG BLOB) depuis la base de données
                            byte[] fileContent = dossier.getContenue();

                            // Écrit le contenu du fichier dans un fichier temporaire
                            Files.write(path, fileContent);

                            // Vérifie si le fichier a été correctement créé
                            if (!Files.exists(path)) {
                                showAlert(Alert.AlertType.ERROR, "Erreur", "Le fichier n'a pas pu être créé.");
                                return;
                            }

                            // Ouvrir le fichier
                            if (Desktop.isDesktopSupported()) {
                                Desktop.getDesktop().open(path.toFile());
                            } else {
                                String os = System.getProperty("os.name").toLowerCase();
                                if (os.contains("win")) {
                                    Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "start", "\"\"", "\"" + path.toFile().getAbsolutePath() + "\""});
                                } else if (os.contains("mac")) {
                                    Runtime.getRuntime().exec(new String[]{"open", path.toFile().getAbsolutePath()});
                                } else if (os.contains("nix") || os.contains("nux")) {
                                    Runtime.getRuntime().exec(new String[]{"xdg-open", path.toFile().getAbsolutePath()});
                                } else {
                                    showAlert(Alert.AlertType.ERROR, "Erreur", "L'ouverture de fichier n'est pas supportée sur cette plateforme.");
                                }
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                            showAlert(Alert.AlertType.ERROR, "Erreur", "Une erreur s'est produite lors de l'ouverture du fichier : " + e.getMessage());
                        }
                    });
                    fileHBox.getChildren().add(openButton);
                    fileListContainer.getChildren().add(fileHBox);


                }
            }
        }
    }
    private void save() {
        saveButton.setOnAction(event -> {
            try {
                handleSave();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void annuler() {
        annulerButton.setOnAction(event -> {
            try {
                stage.close();
                changeFenetre("patient",email,role,medecin,secretaire,idM);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void changeMedicament() {

        if(patientResDTO==null){
            chargerMedicamentsButton.setVisible(false);
            labelMedicament.setVisible(false);
        }
        chargerMedicamentsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    changeFenetre("medicament",patientResDTO.getIdPatient());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
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
            // Ajout du fichier à la liste des fichiers affichés
            HBox fileHBox = new HBox(10);
            fileHBox.setAlignment(javafx.geometry.Pos.CENTER_LEFT);

            Label fileNameLabel = new Label(selectedFile.getAbsolutePath()); // Store full path
            fileHBox.getChildren().add(fileNameLabel);

            Button deleteButton = new Button();
            ImageView deleteIcon = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/static/icons/supprimer.png")).toExternalForm()));
            deleteIcon.setFitWidth(16);
            deleteIcon.setFitHeight(16);
            deleteButton.setGraphic(deleteIcon);

            deleteButton.setOnAction(event ->
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de suppression");
                alert.setHeaderText("Supprimer le fichier");
                alert.setContentText("Êtes-vous sûr de vouloir supprimer ce fichier ?");
                ButtonType buttonYes = new ButtonType("Oui");
                ButtonType buttonNo = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(buttonYes, buttonNo);
                alert.showAndWait().ifPresent(response -> {
                    if (response == buttonYes) {
                        fileListContainer.getChildren().remove(fileHBox);
                    }
                    });
            });
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
                            String os = System.getProperty("os.name").toLowerCase();
                            if (os.contains("win")) {
                                // Ouvrir le fichier sous Windows
                                Runtime.getRuntime().exec(new String[]{"cmd.exe", "/c", "start", selectedFile.getAbsolutePath()});
                            } else if (os.contains("mac")) {
                                // Ouvrir le fichier sous macOS
                                Runtime.getRuntime().exec(new String[]{"open", selectedFile.getAbsolutePath()});
                            } else if (os.contains("nix") || os.contains("nux")) {
                                // Ouvrir le fichier sous Linux/Unix
                                Runtime.getRuntime().exec(new String[]{"xdg-open", selectedFile.getAbsolutePath()});
                            } else {
                                showAlert(Alert.AlertType.ERROR, "Erreur", "L'ouverture de fichier n'est pas supportée sur cette plateforme.");
                            }
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
    private void handleSave() throws Exception {
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
        String email2 = emailField.getText().trim();
        String telephone = TelField.getText().trim();
        String batiment = (batimentComboBox.getValue() != null) ? batimentComboBox.getValue().trim() : "aucun"; // Avoid NullPointerException
        String code = codeField.getText().trim();
        String notes = notesField.getText().trim();
        String ville = villeField.getText().trim();
        String maladie = (maladieComboBox.getValue() != null) ? maladieComboBox.getValue().trim() : "non"; // Avoid NullPointerException

        String sexeValue = ((RadioButton) sexe.getSelectedToggle()).getText();
        Sexe sexee = sexeValue.equals("Homme") ? Sexe.HOMME : Sexe.FEMME;



        // Validation
        if (nom.isEmpty() || prenom.isEmpty()  || dateNaissance == null || telephone.isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Erreur de Validation", "Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Validation des autres champs (email, CIN, téléphone, etc.)
        if(!email2.isEmpty()) {
            if (!email2.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {
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



        PatientReqDTO patientReqDTO;
        if(patientResDTO!=null) {
            patientReqDTO=new PatientReqDTO(nom,prenom,telephone,email2,dateNaissance,notes,batiment,code,cin,ville,sexee,idM,patientResDTO.getIdPatient());
            //patientResDTO=patientController.updatePatient(patientReqDTO);
            patientReqDTO.setMaladie(maladie.equals("oui"));
            ResAPI.update("patient",patientReqDTO);

        }else {
            patientReqDTO=new PatientReqDTO(nom,prenom,telephone,email2,dateNaissance,notes,batiment,code,cin,ville,sexee,idM);
            patientReqDTO.setMaladie(maladie.equals("oui"));
           // patientResDTO=patientController.savePatient(patientReqDTO);
            ResAPI.save("patient",patientReqDTO);

        }

        saveFichier();
        stage.close();
        try {
            changeFenetre("patient",email,role,medecin,secretaire,idM);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    private void saveFichier() throws Exception {
        if (fileListContainer != null) {
            for (var node : fileListContainer.getChildren()) {
                if (node instanceof HBox fileHBox) {
                    Label fileNameLabel = (Label) fileHBox.getChildren().get(0);
                    String filePath = fileNameLabel.getText(); // Utiliser le chemin complet
                    Path path = Paths.get(filePath);
                    String fileName = path.getFileName().toString();

                    // Imprimer le chemin du fichier pour déboguer
                    System.out.println("Chemin du fichier : " + filePath);

                    File file = new File(filePath);
                    byte[] fileContent;

                    try {
                        fileContent = Files.readAllBytes(file.toPath());
                    } catch (IOException e) {
                        //showAlert(Alert.AlertType.ERROR, "Erreur de Lecture de Fichier", "Erreur lors de la lecture du fichier: " + e.getMessage());
                        continue; // Passer au fichier suivant en cas d'erreur
                    }

                    String fileExtension = getFileExtension(file.getName());

                    DossierMedicalReqDTO dossierMedicalReqDTO = new DossierMedicalReqDTO(
                            fileExtension,
                            fileName,
                            fileContent,
                            patientResDTO.getIdPatient() // Utiliser l'ID du patient si nécessaire
                    );

                    // Appeler dossierMedicalController pour enregistrer le fichier
                    //dossierMedicalController.saveDossierMedical(dossierMedicalReqDTO);
                    ResAPI.save("dossiermedical",dossierMedicalReqDTO);

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

    public String getAge(LocalDate date) throws UserException {
        return String.valueOf(Period.between(date, LocalDate.now()).getYears());
    }


}
