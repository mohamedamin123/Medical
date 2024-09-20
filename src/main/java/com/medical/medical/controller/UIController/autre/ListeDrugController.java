package com.medical.medical.controller.UIController.autre;

import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.req.MedecinReqDTO;
import com.medical.medical.models.dto.res.AdminResDTO;
import com.medical.medical.models.dto.res.DrugResDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.IOException;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
public class ListeDrugController {

    ObservableList<DrugResDTO> dtos;

    @FXML
    private TextField searchField;

    @FXML
    private TableView<DrugResDTO> drugTable;

    @FXML
    private TableColumn<DrugResDTO,String> ingredientActif;

    @FXML
    private TableColumn<DrugResDTO, String> usage;

    @FXML
    private TableColumn<DrugResDTO, String> avertissements;

    @FXML
    private TableColumn<DrugResDTO, String> ingredientsInactifs;

    @FXML
    private TableColumn<DrugResDTO, String> posologieEtAdministration;

    @FXML
    private Pagination pagination;

    private final int PAGE_SIZE = 7;
    private PagedDataSource pagedDataSource;
    private DrugResDTO selectedDrug;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            Stage stage = (Stage) searchField.getScene().getWindow();
            if (stage != null) {
                Object userData = stage.getUserData();
                    // Initialiser les colonnes du tableau
                ingredientActif.setCellValueFactory(new PropertyValueFactory<>("ingredientActif"));
                usage.setCellValueFactory(new PropertyValueFactory<>("usage"));
                avertissements.setCellValueFactory(new PropertyValueFactory<>("avertissements"));
                ingredientsInactifs.setCellValueFactory(new PropertyValueFactory<>("ingredientsInactifs"));
                posologieEtAdministration.setCellValueFactory(new PropertyValueFactory<>("posologieEtAdministration"));

                    // Charger les données dans le tableau
                    try {
                        dtos = FXCollections.observableArrayList(
                                ResAPI.findAllJson("drug", DrugResDTO.class)
                        );
                    } catch (Exception e) {
                        showErrorDialog("Erreur de chargement", "Impossible de charger la liste des médicaments.");
                        return;
                    }

                    drugTable.setItems(dtos);

                    // Initialiser la source de données paginée
                    pagedDataSource = new PagedDataSource(dtos, PAGE_SIZE);
                    pagination.setPageCount(pagedDataSource.getPageCount());
                    pagination.setCurrentPageIndex(0);
                    pagination.setPageFactory(this::createPage);

                    drugTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                        selectedDrug = newValue;
                    });

                    // Ajouter le filtrage de recherche
                    searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                        try {
                            filterTable(newValue);
                        } catch (UserException e) {
                            showErrorDialog("Erreur", "Une erreur s'est produite lors du filtrage.");
                        }
                    });

            }else {
                // Handle the case when stage is null
                System.out.println("Stage is null. Cannot retrieve user data.");
            }
        });




    }

    private VBox createPage(int pageIndex) {
        VBox box = new VBox();
        ObservableList<DrugResDTO> pageItems = pagedDataSource.getPage(pageIndex);
        drugTable.setItems(pageItems);
        box.getChildren().add(drugTable);
        return box;
    }


    private void filterTable(String query) throws UserException {
        ObservableList<DrugResDTO> filteredList = FXCollections.observableArrayList();

        for (DrugResDTO drug : dtos) {
            boolean matches = drug.getAvertissements().toLowerCase().contains(query.toLowerCase()) ||
                    drug.getUsage().toLowerCase().contains(query.toLowerCase()) ||
                    drug.getIngredientActif().toLowerCase().contains(query.toLowerCase()) ||
                    drug.getIngredientsInactifs().toLowerCase().contains(query.toLowerCase()) ||
                    drug.getPosologieEtAdministration().toLowerCase().contains(query.toLowerCase());

            if (matches) {
                filteredList.add(drug);
            }
        }

        pagedDataSource = new PagedDataSource(filteredList, PAGE_SIZE);
        pagination.setPageCount(pagedDataSource.getPageCount());
        pagination.setCurrentPageIndex(0);
        drugTable.setItems(pagedDataSource.getPage(0));
    }

    private void showErrorDialog(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
