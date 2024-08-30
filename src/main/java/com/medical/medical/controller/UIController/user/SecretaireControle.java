package com.medical.medical.controller.UIController.user;

import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO
;
import com.medical.medical.utils.PagedDataSource;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
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
import java.util.List;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component("uiSecretaireController")
@Slf4j
public class SecretaireControle {
    @FXML
    private TableColumn<SecretaireResDTO, LocalDate>  dobColumn;
    @FXML
    private Button cancelButton;
    @FXML
    private TableColumn<SecretaireResDTO, Number> idColumn;
    @FXML
    private TableColumn<SecretaireResDTO, String> phoneColumn;
    @FXML
    private TableView<SecretaireResDTO> secretaireTable;
    @FXML
    private TableColumn<SecretaireResDTO, String> nomColumn;
    @FXML
    private TextField searchField;
    @FXML
    private Pagination pagination;
    @FXML
    private Button addPatientButton;

    private ObservableList<SecretaireResDTO>  secretaires
;
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
    private com.medical.medical.controller.API.SecretaireController secretaireController;



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
                idColumn.setCellFactory(column -> new TableCell<SecretaireResDTO, Number>() {
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
                   secretaires = FXCollections.observableArrayList(
                        getDate()
                );

                // Initialiser PagedDataSource
                pagedDataSource = new PagedDataSource(secretaires, PAGE_SIZE);

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
                secretaireTable.setOnMouseClicked((MouseEvent event) -> {
                    if (event.getClickCount() == 2) {
                        showPatientDetails(secretaireTable.getSelectionModel().getSelectedItem());
                    }
                });

                addPatientButton.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        ajouterSecretaire();
                    }
                });

                cancelButton.setOnAction(actionEvent -> annuler());
            }

        });


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

    @FXML
    private void handleDeleteSecretaire() {
        // Récupérer le patient sélectionné
        SecretaireResDTO selectedPatient = secretaireTable.getSelectionModel().getSelectedItem();

        if (selectedPatient != null) {
            // Supprimer le patient de la liste
            secretaires.remove(selectedPatient);

            // Supprimer le patient du contrôleur API
            secretaireController.deleteSecretaireById(selectedPatient.getIdSecretaire());

            // Réinitialiser les données de la table
            pagedDataSource = new PagedDataSource(secretaires, PAGE_SIZE);
            pagination.setPageCount(pagedDataSource.getPageCount());
            pagination.setCurrentPageIndex(0); // Reset to the first page
            secretaireTable.setItems(pagedDataSource.getPage(0)); // Mettre à jour la table
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

    private void ajouterSecretaire() {
        try {
            stage.close();
            changeFenetre("addSecretaire",email,role,medecin,secretaire,idM);
        } catch (IOException e) {
            log.error("Error changing window", e);
        }
    }

    private void filterTable(String query) throws UserException {
        ObservableList<SecretaireResDTO> filteredList = FXCollections.observableArrayList();


        for (SecretaireResDTO patient : secretaires) {
            boolean matches = patient.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                    patient.getTel().contains(query);

            if (matches ) {
                filteredList.add(patient);
            }
        }

        // Réinitialiser la pagination avec la liste filtrée
        pagedDataSource = new PagedDataSource(filteredList, PAGE_SIZE);
        pagination.setPageCount(pagedDataSource.getPageCount());
        pagination.setCurrentPageIndex(0); // Reset to the first page
        secretaireTable.setItems(pagedDataSource.getPage(0));
    }

    private void showPatientDetails(SecretaireResDTO secretaire) {
        // Logique pour afficher les détails du patient
        try {
            stage.close();
            changeFenetre("addSecretaire",email,role,medecin,secretaire,idM,secretaire);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private VBox createPage(int pageIndex) {
        VBox box = new VBox();
        box.getChildren().add(secretaireTable);
        secretaireTable.setItems(pagedDataSource.getPage(pageIndex));
        return box;
    }

    private List<SecretaireResDTO> getDate() {

        List<SecretaireResDTO> SecretaireResDTOS;
        SecretaireResDTOS= secretaireController.findSecretairesByIdMedecin(idM);
        return SecretaireResDTOS;
    }

}
