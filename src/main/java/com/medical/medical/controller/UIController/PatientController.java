package com.medical.medical.controller.UIController;

import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.utils.PagedDataSource;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component("uiPatientController")
@Slf4j
public class PatientController {

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




    @FXML
    public void initialize() {
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
                new PatientResDTO("Dupont", "Jean", "1234567890", "jean.dupont@example.com", LocalDate.of(1980, 1, 15), "Note 1", "ID001", "CIN001"),
                new PatientResDTO("Curie", "Marie", "0987654321", "marie.curie@example.com", LocalDate.of(1990, 5, 25), "Note 2", "ID002", "CIN002"),
                new PatientResDTO("Martin", "Paul", "1122334455", "paul.martin@example.com", LocalDate.of(1985, 8, 30), "Note 3", "ID003", "CIN003"),
                new PatientResDTO("Leblanc", "Lucie", "5566778899", "lucie.leblanc@example.com", LocalDate.of(1992, 12, 5), "Note 4", "ID004", "CIN004"),
                new PatientResDTO("Dubois", "Pierre", "6677889900", "pierre.dubois@example.com", LocalDate.of(1983, 11, 20), "Note 5", "ID005", "CIN005"),
                new PatientResDTO("Laurent", "Sophie", "7788990011", "sophie.laurent@example.com", LocalDate.of(1995, 7, 10), "Note 6", "ID006", "CIN006"),
                new PatientResDTO("Dupuis", "François", "8899001122", "francois.dupuis@example.com", LocalDate.of(1988, 6, 18), "Note 7", "ID007", "CIN007"),
                new PatientResDTO("Robert", "Claire", "9900112233", "claire.robert@example.com", LocalDate.of(1993, 2, 14), "Note 8", "ID008", "CIN008"),
                new PatientResDTO("Lefèvre", "Michel", "0011223344", "michel.lefevre@example.com", LocalDate.of(1981, 9, 22), "Note 9", "ID009", "CIN009"),
                new PatientResDTO("Garcia", "Nathalie", "1122334455", "nathalie.garcia@example.com", LocalDate.of(1987, 3, 9), "Note PAGE_SIZE", "ID0PAGE_SIZE", "CIN0PAGE_SIZE"),
                new PatientResDTO("Dubois", "Marc", "2233445566", "marc.dubois@example.com", LocalDate.of(1990, 10, 30), "Note 11", "ID011", "CIN011"),
                new PatientResDTO("Petit", "Julie", "3344556677", "julie.petit@example.com", LocalDate.of(1996, 4, 11), "Note 12", "ID012", "CIN012"),
                new PatientResDTO("Martin", "David", "4455667788", "david.martin@example.com", LocalDate.of(1984, 12, 15), "Note 13", "ID013", "CIN013"),
                new PatientResDTO("Lefebvre", "Alice", "5566778899", "alice.lefebvre@example.com", LocalDate.of(1991, 8, 25), "Note 14", "ID014", "CIN014"),
                new PatientResDTO("Moreau", "Robert", "6677889900", "robert.moreau@example.com", LocalDate.of(1986, 5, 5), "Note 15", "ID015", "CIN015")
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
    }

    private void ajouterPatient() {
        try {
            changeFenetre("addPatient");
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
        log.info(patient.toString());
    }

    private VBox createPage(int pageIndex) {
        VBox box = new VBox();
        box.getChildren().add(patientTable);
        patientTable.setItems(pagedDataSource.getPage(pageIndex));
        return box;
    }

    private List<PatientResDTO> getDate() {

        return null;
    }

}
