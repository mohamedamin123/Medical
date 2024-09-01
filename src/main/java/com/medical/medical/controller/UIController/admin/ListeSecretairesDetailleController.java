package com.medical.medical.controller.UIController.admin;


import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.controller.API.SecretaireController;
import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.req.SecretaireReqDTO;
import com.medical.medical.models.dto.res.AdminResDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.utils.PagedDataSource;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Optional;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class ListeSecretairesDetailleController {

    @FXML
    private TableColumn<SecretaireResDTO,String>  medecinColumn;

    ObservableList<SecretaireResDTO> SecretaireResDTOList;


    @FXML
    private TextField searchField;

    @FXML
    private TableView<SecretaireResDTO> secretaireTable;

    @FXML
    private TableColumn<SecretaireResDTO,String> statutColumn;

    @FXML
    private TableColumn<SecretaireResDTO, Number> idColumn;

    @FXML
    private TableColumn<SecretaireResDTO, String> nameColumn;

    @FXML
    private TableColumn<SecretaireResDTO, String> phoneColumn;
    

    @FXML
    private TableColumn<SecretaireResDTO, Button> detailsColumn;

    @FXML
    private Pagination pagination;


    @Autowired
    private SecretaireController secretaireController;

    @Autowired
    private MedecinController medecinController;

    private final int PAGE_SIZE=7;

    private PagedDataSource pagedDataSource;

    private AdminResDTO adminResDTO;

    private SecretaireResDTO selectedsecretaire;

    private MedecinResDTO medecinResDTO;

    @FXML
    public void initialize() {

        Platform.runLater(() -> {
            Stage  stage = (Stage) searchField.getScene().getWindow();
            if (stage != null) {
                Object userData = stage.getUserData();
                if (userData instanceof Object[] data) {
                    adminResDTO = (AdminResDTO) data[0];
                    medecinResDTO = (MedecinResDTO) data[1];
                }
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

                statutColumn.setCellFactory(column -> new TableCell<SecretaireResDTO, String>() {
                    @Override
                    protected void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);

                        if (empty || getTableRow() == null || getTableRow().getItem() == null) {
                            setText(null);
                        } else {
                            SecretaireResDTO secretaire = getTableRow().getItem();
                            if (secretaire.getStatut() == Boolean.FALSE) {
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



                medecinColumn.setCellValueFactory(cellData -> {
                    SecretaireResDTO secretaire = cellData.getValue();
                    String medecinName = "Non assigné"; // Valeur par défaut si aucun médecin n'est trouvé

                    try {
                        Optional<MedecinResDTO> medecin = medecinController.findMedecinById(secretaire.getIdMedecin());
                        if (medecin != null) {
                            medecinName = medecin.get().getFullName();
                        }
                    } catch (Exception e) {
                        log.error("Erreur lors de la récupération du médecin pour le secrétaire {} : {}", secretaire.getIdSecretaire(), e.getMessage());
                    }

                    return new ReadOnlyObjectWrapper<>(medecinName);
                });

                // Configurer les colonnes pour les boutons


                detailsColumn.setCellValueFactory(cellData -> {
                    Button button = new Button("Voir Détails");
                    button.setOnAction(event -> handleShowDetails(cellData.getValue()));
                    button.getStyleClass().add("button-secretaire");
                    return new ReadOnlyObjectWrapper<>(button);
                });

                SecretaireResDTOList= FXCollections.observableArrayList(
                        secretaireController.findSecretairesByIdMedecin(medecinResDTO.getIdMedecin())
                );

                // Charger les données dans le tableau
                secretaireTable.getItems().addAll(SecretaireResDTOList);

                // Initialiser PagedDataSource
                pagedDataSource = new PagedDataSource(SecretaireResDTOList, PAGE_SIZE);

                // Configurer la pagination
                pagination.setPageCount(pagedDataSource.getPageCount());
                pagination.setCurrentPageIndex(0);
                pagination.setPageFactory(this::createPage);





                secretaireTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    selectedsecretaire = newValue;
                });

            }
        });
        // Initialiser les colonnes du tableau



    }


    // Méthode pour voir les détails du secretaire
    private void handleShowDetails(SecretaireResDTO SecretaireResDTO) {
        try {
            Stage stage = (Stage) searchField.getScene().getWindow();
            stage.close();
            changeFenetre("addSecretaires",adminResDTO,SecretaireResDTO,1);
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

    private void filterTable(String query) throws UserException {
        ObservableList<SecretaireResDTO> filteredList = FXCollections.observableArrayList();

        for (SecretaireResDTO secretaire : SecretaireResDTOList) {
            boolean matches = false;
            try {
                Optional<MedecinResDTO> medecin = medecinController.findMedecinById(secretaire.getIdMedecin());
                String medecinName = medecin.isPresent() ? medecin.get().getFullName() : "Non assigné";

                matches = secretaire.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                        secretaire.getTel().contains(query) ||
                        medecinName.toLowerCase().contains(query.toLowerCase());
            } catch (Exception e) {
                log.error("Erreur lors de la récupération du médecin pour le secrétaire {} : {}", secretaire.getIdSecretaire(), e.getMessage());
            }

            if (matches) {
                filteredList.add(secretaire);
            }
        }

        // Réinitialiser la pagination avec la liste filtrée
        pagedDataSource = new PagedDataSource(filteredList, PAGE_SIZE);
        pagination.setPageCount(pagedDataSource.getPageCount());
        pagination.setCurrentPageIndex(0); // Reset to the first page
        secretaireTable.setItems(pagedDataSource.getPage(0));
    }



}
