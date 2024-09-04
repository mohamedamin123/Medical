package com.medical.medical.controller.UIController.admin;

import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.req.MedecinReqDTO;
import com.medical.medical.models.dto.res.AdminResDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.utils.PagedDataSource;
import com.medical.medical.utils.ResAPI;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class ListeMedecinController {


    @FXML
    private Button activateButton;
    ObservableList<MedecinResDTO> medecinResDTOList;


    @FXML
    private TextField searchField;

    @FXML
    private TableView<MedecinResDTO> medecinTable;

    @FXML
    private TableColumn <MedecinResDTO,String> statutColumn;

    @FXML
    private TableColumn<MedecinResDTO, Number> idColumn;

    @FXML
    private TableColumn<MedecinResDTO, String> nameColumn;

    @FXML
    private TableColumn<MedecinResDTO, String> phoneColumn;

    @FXML
    private TableColumn<MedecinResDTO, Button> secretairesColumn;

    @FXML
    private TableColumn<MedecinResDTO, Button> detailsColumn;

    @FXML
    private Pagination pagination;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addMedecinButton;

    @FXML
    private Button deleteButton;

//    @Autowired
//    private MedecinController medecinController;

    private final int PAGE_SIZE=7;

    private PagedDataSource pagedDataSource;

    private AdminResDTO adminResDTO;

    private MedecinResDTO selectedMedecin;

    @FXML
    public void initialize() {

        Platform.runLater(() -> {
            Stage stage;
            stage = (Stage) searchField.getScene().getWindow();
            if (stage != null) {
                Object userData = stage.getUserData();
                if (userData instanceof Object[] data) {
                    adminResDTO = (AdminResDTO) data[0];
                }
            }
        });
        // Initialiser les colonnes du tableau
        idColumn.setCellFactory(column -> new TableCell<MedecinResDTO, Number>() {
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

        statutColumn.setCellFactory(column -> new TableCell<MedecinResDTO, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                    setText(null);
                } else {
                    MedecinResDTO medecin = getTableRow().getItem();
                    if (medecin.getStatut() == Boolean.FALSE) {
                        setText("Supprimer");
                    } else {
                        setText("Activer");
                    }
                }
            }
        });
        // Filtrage de recherche
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterTable(newValue);
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
        });

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));


        // Configurer les colonnes pour les boutons
        secretairesColumn.setCellValueFactory(cellData -> {
            Button button = new Button("Voir Secrétaires");
            button.setOnAction(event -> handleShowSecretaires(cellData.getValue()));
            button.getStyleClass().add("button-voir");
            return new ReadOnlyObjectWrapper<>(button);
        });

        detailsColumn.setCellValueFactory(cellData -> {
            Button button = new Button("Voir Détails");
            button.setOnAction(event -> handleShowDetails(cellData.getValue()));
            button.getStyleClass().add("button-secretaire");
            return new ReadOnlyObjectWrapper<>(button);
        });

        try {
            medecinResDTOList= FXCollections.observableArrayList(
                    ResAPI.findAll("medecin", MedecinResDTO.class)
            );
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        // Charger les données dans le tableau
        medecinTable.getItems().addAll(medecinResDTOList);

        // Initialiser PagedDataSource
        pagedDataSource = new PagedDataSource(medecinResDTOList, PAGE_SIZE);

        // Configurer la pagination
        pagination.setPageCount(pagedDataSource.getPageCount());
        pagination.setCurrentPageIndex(0);
        pagination.setPageFactory(this::createPage);


        cancelButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) deleteButton.getScene().getWindow();
                stage.close();
                try {
                    changeFenetre("home_admin",adminResDTO);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        addMedecinButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage stage = (Stage) searchField.getScene().getWindow();
                stage.close();
                try {
                    changeFenetre("addMedecin",adminResDTO);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        medecinTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            selectedMedecin = newValue;
        });
        deleteButton.setOnAction(event -> handleDeleteButtonAction());
        activateButton.setOnAction(event -> handleActiverButtonAction());


    }

    // Méthode pour afficher les secrétaires du médecin
    private void handleShowSecretaires(MedecinResDTO medecinResDTO) {
        try {
            changeFenetre("liste_secretaires_detaille",adminResDTO,medecinResDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    // Méthode pour voir les détails du médecin
    private void handleShowDetails(MedecinResDTO medecinResDTO) {
        try {
            Stage stage = (Stage) searchField.getScene().getWindow();
            stage.close();
            changeFenetre("addMedecin",adminResDTO,medecinResDTO);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private VBox createPage(int pageIndex) {
        VBox box = new VBox();
        box.getChildren().add(medecinTable);
        medecinTable.setItems(pagedDataSource.getPage(pageIndex));
        return box;
    }

    private void filterTable(String query) throws UserException {
        ObservableList<MedecinResDTO> filteredList = FXCollections.observableArrayList();

        for (MedecinResDTO patient : medecinResDTOList) {
            boolean matches =
                    patient.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                    patient.getTel().contains(query);
            if (matches) {
                filteredList.add(patient);
            }
        }

        // Réinitialiser la pagination avec la liste filtrée
        pagedDataSource = new PagedDataSource(filteredList, PAGE_SIZE);
        pagination.setPageCount(pagedDataSource.getPageCount());
        pagination.setCurrentPageIndex(0); // Reset to the first page
        medecinTable.setItems(pagedDataSource.getPage(0));
    }
    private void handleDeleteButtonAction() {
        if (selectedMedecin == null) {
            // Alerte si aucune ligne n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerte");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un médecin à supprimer.");
            alert.showAndWait();
            return;
        }

        // Demander confirmation avant suppression
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir supprimer le médecin sélectionné ?");
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Supprimer le médecin
                try {
                    Stage stage;
                    stage = (Stage) searchField.getScene().getWindow();
                    ResAPI.deleteById("medecin",selectedMedecin.getIdMedecin());
                    //medecinController.deleteMedecinById(selectedMedecin.getIdMedecin());
                    selectedMedecin.setStatut(Boolean.FALSE);
                    filterTable(searchField.getText()); // Réapplique le filtre
                    stage.close();
                    changeFenetre("liste_medecin", adminResDTO);

                } catch (UserException e) {
                    // Gestion des erreurs de suppression
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Une erreur est survenue lors de la suppression du médecin.");
                    errorAlert.showAndWait();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    private void handleActiverButtonAction() {
        if (selectedMedecin == null) {
            // Alerte si aucune ligne n'est sélectionnée
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Alerte");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez sélectionner un médecin à activer.");
            alert.showAndWait();
            return;
        }

        // Demander confirmation avant suppression
        Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmationAlert.setTitle("Confirmation");
        confirmationAlert.setHeaderText(null);
        confirmationAlert.setContentText("Êtes-vous sûr de vouloir activer le médecin sélectionné ?");
        confirmationAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // Supprimer le médecin
                try {

                    selectedMedecin.setStatut(Boolean.TRUE);
                    selectedMedecin.setDeletedAt(null);
                    MedecinReqDTO medecinReqDTO = MedecinReqDTO.builder()
                            .idMedecin(selectedMedecin.getIdMedecin())
                            .nom(selectedMedecin.getNom())
                            .prenom(selectedMedecin.getPrenom())
                            .tel(selectedMedecin.getTel())
                            .email(selectedMedecin.getEmail())
                            .dateDeNaissance(selectedMedecin.getDateDeNaissance())
                            .specialite(selectedMedecin.getSpecialite())
                            .statut(Boolean.TRUE)
                            .deletedAt(null)
                            .build();
                    ResAPI.update("medecin",medecinReqDTO);
                    //medecinController.updateMedecin(medecinReqDTO);
                    Stage stage;
                    stage = (Stage) searchField.getScene().getWindow();
                    filterTable(searchField.getText()); // Réapplique le filtre
                    stage.close();
                    changeFenetre("liste_medecin", adminResDTO);

                } catch (UserException e) {
                    // Gestion des erreurs de suppression
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setTitle("Erreur");
                    errorAlert.setHeaderText(null);
                    errorAlert.setContentText("Une erreur est survenue lors de la activation du médecin.");
                    errorAlert.showAndWait();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }


}
