package com.medical.medical.controller.UIController.admin;

import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.res.AdminResDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
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
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class ListeMedecinController {

    ObservableList<MedecinResDTO> medecinResDTOList;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private VBox vBox;

    @FXML
    private HBox hBox;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<MedecinResDTO> medecinTable;

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

    @Autowired
    private MedecinController medecinController;

    private final int PAGE_SIZE=7;

    private PagedDataSource pagedDataSource;

    private AdminResDTO adminResDTO;


    @FXML
    public void initialize() {

        Platform.runLater(() -> {
             Stage stage = (Stage) searchField.getScene().getWindow();
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

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("fullName"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("tel"));

        // Configurer les colonnes pour les boutons
        secretairesColumn.setCellValueFactory(cellData -> {
            Button button = new Button("Voir Secrétaires");
            button.setOnAction(event -> handleShowSecretaires(cellData.getValue()));
            return new ReadOnlyObjectWrapper<>(button);
        });

        detailsColumn.setCellValueFactory(cellData -> {
            Button button = new Button("Voir Détails");
            button.setOnAction(event -> handleShowDetails(cellData.getValue()));
            return new ReadOnlyObjectWrapper<>(button);
        });

        medecinResDTOList= FXCollections.observableArrayList(
                medecinController.findAllMedecin()
        );

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

            }
        });
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

            }
        });

    }

    // Méthode pour afficher les secrétaires du médecin
    private void handleShowSecretaires(MedecinResDTO medecinResDTO) {
        try {
            log.info("Afficher la liste des secrétaires pour le médecin: " + medecinResDTO.getFullName());
            // Implémentez ici l'ouverture de l'interface secrétaires
        } catch (UserException e) {
            log.error("Erreur lors de l'affichage des secrétaires", e);
        }
    }

    // Méthode pour voir les détails du médecin
    private void handleShowDetails(MedecinResDTO medecinResDTO) {
        try {
            log.info("Afficher les détails du médecin: " + medecinResDTO.getFullName());
            // Implémentez ici l'affichage des détails du médecin
        } catch (UserException e) {
            log.error("Erreur lors de l'affichage des détails du médecin", e);
        }
    }
    private VBox createPage(int pageIndex) {
        VBox box = new VBox();
        box.getChildren().add(medecinTable);
        medecinTable.setItems(pagedDataSource.getPage(pageIndex));
        return box;
    }
}
