package com.medical.medical.controller.UIController.user;

import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.RendezVousResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.utils.PagedDataSource;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component("uiPatientController")
@Slf4j
public class PatientController {
    @FXML
    private Button cancelButton;
    @FXML
    private TableColumn<PatientResDTO, Number> idColumn;
    @FXML
    private TableColumn<PatientResDTO, String> phoneColumn;
    @FXML
    private TableView<PatientResDTO> patientTable;
    @FXML
    private TableColumn<PatientResDTO, String> cinColumn;
    @FXML
    private TableColumn<PatientResDTO, String> nomColumn;
    @FXML
    private TableColumn<PatientResDTO, LocalDate> dobColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Pagination pagination;
    @FXML
    private Button addPatientButton;

    private ObservableList<PatientResDTO> patients;
    private PagedDataSource pagedDataSource;
    
    private final int PAGE_SIZE=12;
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

    @Autowired
    private com.medical.medical.controller.API.PatientController patientController;



    @FXML
    public void initialize() {

        Platform.runLater(() -> {
            stage = (Stage) pagination.getScene().getWindow();
            Object userData = stage.getUserData();
            if (userData instanceof Object[] data) {
                email = (String) data[0];
                role = (String) data[1];
                medecin = (data[2] instanceof MedecinResDTO) ? (MedecinResDTO) data[2] : null;
                secretaire = (data[3] instanceof SecretaireResDTO) ? (SecretaireResDTO) data[3] : null;
                idM = (Integer) data[4];

                // Initialiser les colonnes
                idColumn.setCellFactory(column -> new TableCell<PatientResDTO, Number>() {
                    @Override
                    protected void updateItem(Number item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                        } else {
                            setText(String.valueOf(getIndex() + 1 + (pagination.getCurrentPageIndex() * PAGE_SIZE)));
                        }
                    }
                });

                // Configurer les colonnes
                cinColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCIN()));
                nomColumn.setCellValueFactory(cellData -> {
                    try {
                        return new ReadOnlyStringWrapper(cellData.getValue().getFullName());
                    } catch (UserException e) {
                        throw new RuntimeException(e);
                    }
                });
                dobColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDateDeNaissance()));
                phoneColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTel()));

                // Données d'exemple
                patients = FXCollections.observableArrayList(
                        getDate()
                );

                // Initialiser PagedDataSource
                pagedDataSource = new PagedDataSource(patients, PAGE_SIZE);

                // Configurer la pagination
                pagination.setPageCount(pagedDataSource.getPageCount());
                pagination.setCurrentPageIndex(0);
                pagination.setPageFactory(this::createPage);

                // Filtrage de recherche
                searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        filterTable(newValue);
                    } catch (UserException e) {
                        throw new RuntimeException(e);
                    }
                });

                // Gestion du clic sur les lignes
                patientTable.setOnMouseClicked((MouseEvent event) -> {
                    if (event.getClickCount() == 2) {
                        showPatientDetails(patientTable.getSelectionModel().getSelectedItem());
                    }
                });

                addPatientButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        ajouterPatient();
                    }
                });

                cancelButton.setOnAction(actionEvent -> annuler());
            }

        });


    }

    @FXML
    private void handleDeletePatient() {
        // Récupérer le patient sélectionné
        PatientResDTO selectedPatient = patientTable.getSelectionModel().getSelectedItem();

        if (selectedPatient != null) {
            // Supprimer le patient de la liste
            patients.remove(selectedPatient);

            // Supprimer le patient du contrôleur API
            patientController.deletePatientById(selectedPatient.getIdPatient());

            // Réinitialiser les données de la table
            pagedDataSource = new PagedDataSource(patients, PAGE_SIZE);
            pagination.setPageCount(pagedDataSource.getPageCount());
            pagination.setCurrentPageIndex(0); // Reset to the first page
            patientTable.setItems(pagedDataSource.getPage(0)); // Mettre à jour la table
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un patient à supprimer.");
        }
    }


    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void annuler() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
        if(medecin==null)
        {

            try {
                changeFenetre("acceuil",secretaire.getEmail(),"secretaire",medecin,secretaire,idM);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        else {
            try {
                changeFenetre("acceuil",medecin.getEmail(),"medecin",medecin,secretaire,idM);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private void ajouterPatient() {
        try {
            stage.close();
            changeFenetre("addPatient",email,role,medecin,secretaire,idM);
        } catch (IOException e) {
            log.error("Error changing window", e);
        }
    }

    private void filterTable(String query) throws UserException {
        ObservableList<PatientResDTO> filteredList = FXCollections.observableArrayList();

        String normalizedQuery = query.replace("-", "").trim();
        String[] queryParts = query.split("-");

        for (PatientResDTO patient : patients) {
            boolean matches = patient.getCIN().contains(query) ||
                    patient.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                    patient.getTel().contains(query);

            LocalDate dob = patient.getDateDeNaissance();
            String dobString = dob.toString(); // Format: YYYY-MM-DD
            String year = dobString.substring(0, 4);
            String month = dobString.substring(5, 7);
            String day = dobString.substring(8, 10);

            boolean dateMatches = false;
            if (queryParts.length == 1) {
                dateMatches = year.startsWith(normalizedQuery) ||
                        month.startsWith(normalizedQuery) ||
                        day.startsWith(normalizedQuery);
            } else if (queryParts.length == 2) {
                if (queryParts[0].length() >= 4) {
                    dateMatches = year.startsWith(queryParts[0]) &&
                            month.startsWith(queryParts[1]);
                } else {
                    dateMatches = month.startsWith(queryParts[0]) &&
                            day.startsWith(queryParts[1]);
                }
            } else if (queryParts.length == 3) {
                dateMatches = year.startsWith(queryParts[0]) &&
                        month.startsWith(queryParts[1]) &&
                        day.startsWith(queryParts[2]);
            }

            if (matches || dateMatches) {
                filteredList.add(patient);
            }
        }

        // Réinitialiser la pagination avec la liste filtrée
        pagedDataSource = new PagedDataSource(filteredList, PAGE_SIZE);
        pagination.setPageCount(pagedDataSource.getPageCount());
        pagination.setCurrentPageIndex(0); // Reset to the first page
        patientTable.setItems(pagedDataSource.getPage(0));
    }

    private void showPatientDetails(PatientResDTO patient) {
        // Logique pour afficher les détails du patient
        try {
            stage.close();
            changeFenetre("addPatient",email,role,medecin,secretaire,idM,patient);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private VBox createPage(int pageIndex) {
        VBox box = new VBox();
        box.getChildren().add(patientTable);
        patientTable.setItems(pagedDataSource.getPage(pageIndex));
        return box;
    }

    private List<PatientResDTO> getDate() {

        List<PatientResDTO> patientResDTOS;
        patientResDTOS=patientController.findPatientsByMedecinId(idM);
         return patientResDTOS;
    }

}
