package com.medical.medical.controller.UIController;

import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.utils.PagedDataSource;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.time.LocalDate;

@Component("uiPatientController")
@Slf4j
public class PatientController {

    @FXML
    private TableColumn<PatientResDTO, String> phoneColumn;   // New Column
    @FXML
    private TableColumn<PatientResDTO, Number> idColumn;
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

    @FXML
    public void initialize() {
        // Initialize columns
        idColumn.setCellValueFactory(cellData -> new ReadOnlyIntegerWrapper(patientTable.getItems().indexOf(cellData.getValue()) + 1));

        // Configure the CIN column
        cinColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getCIN()));

        // Configure the Nom column
        nomColumn.setCellValueFactory(cellData -> {
            try {
                return new ReadOnlyStringWrapper(cellData.getValue().getFullName());
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
        });

        // Configure the Date de Naissance column
        dobColumn.setCellValueFactory(cellData -> new ReadOnlyObjectWrapper<>(cellData.getValue().getDateDeNaissance()));


        // Configure the Phone column
        phoneColumn.setCellValueFactory(cellData -> new ReadOnlyStringWrapper(cellData.getValue().getTel()));

        // Configure the Actions column (you can add buttons or other elements here if needed)


        // Sample data
        patients = FXCollections.observableArrayList(
                new PatientResDTO("gana","amin","95147455","mohamedaming146@æmail.com",LocalDate.of(1985, 5, 15),"","555","14028423"),
                new PatientResDTO("sas","amieen","95147455","mohamedaming146@æmail.com",LocalDate.of(1985, 5, 15),"","7894","14028423")
                // Add more sample data here
        );

        // Initialize PagedDataSource
        pagedDataSource = new PagedDataSource(patients, 10);

        // Setup pagination
        pagination.setPageCount(pagedDataSource.getPageCount());
        pagination.setCurrentPageIndex(0);
        pagination.setPageFactory(this::createPage);

        // Filter search
        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                filterTable(newValue);
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
        });

        // Handle row click to open patient details
        patientTable.setOnMouseClicked((MouseEvent event) -> {
            if (event.getClickCount() == 2) {
                showPatientDetails(patientTable.getSelectionModel().getSelectedItem());
            }
        });
    }

    private void filterTable(String query) throws UserException {
        ObservableList<PatientResDTO> filteredList = FXCollections.observableArrayList();

        // Normalize the query by removing any dashes and trimming it
        String normalizedQuery = query.replace("-", "").trim();

        // Split the query into parts based on dashes
        String[] queryParts = query.split("-");

        for (PatientResDTO patient : patients) {
            boolean matches = patient.getCIN().contains(query) ||
                    patient.getFullName().toLowerCase().contains(query.toLowerCase()) ||
                    patient.getTel().contains(query);

            // Extract the year, month, and day from the LocalDate
            LocalDate dob = patient.getDateDeNaissance();
            String dobString = dob.toString(); // Format: YYYY-MM-DD
            String year = dobString.substring(0, 4);
            String month = dobString.substring(5, 7);
            String day = dobString.substring(8, 10);

            // Initialize match flag
            boolean dateMatches = false;

            // Check if the normalized query matches any part of the date
            if (queryParts.length == 1) {
                // Match year, month, or day
                dateMatches = year.startsWith(normalizedQuery) ||
                        month.startsWith(normalizedQuery) ||
                        day.startsWith(normalizedQuery);
            } else if (queryParts.length == 2) {
                // Match year and month or month and day
                if (queryParts[0].length() >= 4) { // If the first part is long enough to be a year
                    dateMatches = year.startsWith(queryParts[0]) &&
                            month.startsWith(queryParts[1]);
                } else { // Handle cases where the first part might be a month
                    dateMatches = month.startsWith(queryParts[0]) &&
                            day.startsWith(queryParts[1]);
                }
            } else if (queryParts.length == 3) {
                // Match year, month, and day
                dateMatches = year.startsWith(queryParts[0]) &&
                        month.startsWith(queryParts[1]) &&
                        day.startsWith(queryParts[2]);
            }

            // Check if the query is empty or matches any of the date components
            if (query.isEmpty() || dateMatches) {
                matches = true;
            }

            if (matches) {
                filteredList.add(patient);
            }
        }

        // Update the paged data source with the filtered list
        pagedDataSource = new PagedDataSource(filteredList, 10);
        pagination.setPageCount(pagedDataSource.getPageCount());
        pagination.setCurrentPageIndex(0);
        patientTable.setItems(pagedDataSource.getPage(0));
    }



    private TableView<PatientResDTO> createPage(int pageIndex) {
        patientTable.setItems(pagedDataSource.getPage(pageIndex));
        return patientTable;
    }

    @FXML
    void handleAddPatient(ActionEvent event) {
        // Logic to open add patient form
    }

    private void showPatientDetails(PatientResDTO patient) {
        // Logic to open a new interface showing the details of the selected patient
        System.out.println("hh");
    }
}
