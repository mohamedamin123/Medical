package com.medical.medical.controller.UIController.user;

import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.res.*;
import com.medical.medical.utils.PagedDataSource;
import com.medical.medical.utils.ResAPI;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component("uiPatientController")
@Slf4j
public class PatientController {
    @FXML private Button cancelButton, modifierButton, addPatientButton;
    @FXML private TableView<PatientResDTO> patientTable;
    @FXML private TableColumn<PatientResDTO, Number> idColumn;
    @FXML private TableColumn<PatientResDTO, String> cinColumn, nomColumn, phoneColumn;
    @FXML private TableColumn<PatientResDTO, LocalDate> dobColumn;
    @FXML private TextField searchField;
    @FXML private Pagination pagination;

    private ObservableList<PatientResDTO> patients;
    private PagedDataSource pagedDataSource;
    private final int PAGE_SIZE = 12;
    private Stage stage;
    private Integer idM;

    @Setter @Getter private String email;
    @Setter @Getter private String role;
    private MedecinResDTO medecin;
    private SecretaireResDTO secretaire;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) pagination.getScene().getWindow();
            Object userData = stage.getUserData();
            if (userData instanceof Object[] data) {
                email = (String) data[0];
                role = (String) data[1];
                medecin = (MedecinResDTO) data[2];
                secretaire = (SecretaireResDTO) data[3];
                idM = (Integer) data[4];
                initializeTable();
            }
        });
    }

    private void initializeTable() {
        idColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Number item, boolean empty) {
                super.updateItem(item, empty);
                setText(empty ? null : String.valueOf(getIndex() + 1 + (pagination.getCurrentPageIndex() * PAGE_SIZE)));
            }
        });
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

        try {
            patients=FXCollections.observableArrayList(new ArrayList<>());
            getPatients();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        pagedDataSource = new PagedDataSource(patients, PAGE_SIZE);
        pagination.setPageCount(pagedDataSource.getPageCount());
        pagination.setPageFactory(this::createPage);
        searchField.textProperty().addListener((observable, oldValue, newValue) -> filterTable(newValue));

        patientTable.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                handleModifierPatient();
            }
        });

        cancelButton.setOnAction(e->{
            annuler();
        });
        addPatientButton.setOnAction(e->{
            ajouterPatient();
        });
        patientTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                showPatientDetails(patientTable.getSelectionModel().getSelectedItem());
            }
        });

    }

    @FXML
    private void handleModifierPatient() {
        PatientResDTO selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            try {
                stage.close();
                changeFenetre("addPatient", email, role, medecin, secretaire, idM, selectedPatient);
            } catch (IOException e) {
                log.error("Erreur lors du changement de fenêtre", e);
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un patient à modifier.");
        }
    }

    @FXML
    private void handleDeletePatient() throws Exception {
        PatientResDTO selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            boolean confirmation = showConfirmationAlert("Supprimer patient : " + selectedPatient.getFullName(),
                    "Voulez-vous supprimer ce patient ?");
            if (confirmation) {
                ResAPI.deleteById("patient", selectedPatient.getIdPatient()); // Supprime du backend
                patients.remove(selectedPatient); // Supprime du tableau

                // Rafraîchir l'affichage
                pagedDataSource = new PagedDataSource(patients, PAGE_SIZE);
                updatePagination();
            } else {
                System.out.println("L'utilisateur a cliqué sur Non ou a fermé la boîte de dialogue");
            }
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

    private boolean showConfirmationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        ButtonType buttonYes = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType buttonNo = new ButtonType("Non", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonYes, buttonNo);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonYes;
    }


    private void filterTable(String query) {
        ObservableList<PatientResDTO> filteredList = FXCollections.observableArrayList();
        for (PatientResDTO patient : patients) {
            try {
                if (patient.getCIN().contains(query) || patient.getFullName().toLowerCase().contains(query.toLowerCase()) || patient.getTel().contains(query)) {
                    filteredList.add(patient);
                }
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
        }
        pagedDataSource = new PagedDataSource(filteredList, PAGE_SIZE);
        updatePagination();
    }

    private void updatePagination() {
        pagination.setPageCount(pagedDataSource.getPageCount());
        pagination.setCurrentPageIndex(0);
        patientTable.setItems(pagedDataSource.getPage(0));
    }

    private VBox createPage(int pageIndex) {
        VBox box = new VBox();
        box.getChildren().add(patientTable);
        patientTable.setItems(pagedDataSource.getPage(pageIndex));
        return box;
    }

    private void getPatients() throws Exception {
        patients.clear();
        patients= FXCollections.observableArrayList(ResAPI.findByIdMedecin("patient", idM, PatientResDTO.class));
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
    private void showPatientDetails(PatientResDTO patient) {
        // Logique pour afficher les détails du patient
        try {
            stage.close();
            changeFenetre("addPatient",email,role,medecin,secretaire,idM,patient);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
