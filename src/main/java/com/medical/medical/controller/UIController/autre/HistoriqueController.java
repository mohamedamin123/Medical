package com.medical.medical.controller.UIController.autre;

import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.utils.Historique;
import com.medical.medical.utils.ResAPI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

@Slf4j
@Component
public class HistoriqueController {

    @FXML
    private TableView<Historique> historyTable;
    @FXML
    private TableColumn<Historique, LocalDate> dateColumn;
    @FXML
    private TableColumn<Historique, Integer> patientCountColumn;
    @FXML
    private DatePicker searchField;
    @FXML
    private Label totalPatientsLabel;
    @FXML
    private Button closeButton;

    private ObservableList<Historique> fullData = FXCollections.observableArrayList();

    @Setter
    @Getter
    private String email;
    @Setter
    @Getter
    private String role;

    private MedecinResDTO medecin;
    private SecretaireResDTO secretaire;
    private Integer idM;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            setupStageData();
            configureTableColumns();
            loadTableData();
            setupEventHandlers();
        });
    }

    private void setupStageData() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        if (stage != null) {
            Object userData = stage.getUserData();
            if (userData instanceof Object[] data && data.length >= 4) {
                email = (String) data[0];
                role = (String) data[1];
                medecin = (data[2] instanceof MedecinResDTO) ? (MedecinResDTO) data[2] : null;
                secretaire = (data[3] instanceof SecretaireResDTO) ? (SecretaireResDTO) data[3] : null;
                idM = (Integer) data[4];
            }
        }
    }

    private void configureTableColumns() {
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
        patientCountColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    }

    private void setupEventHandlers() {
        closeButton.setOnAction(event -> handleClose());

        searchField.setOnAction(event -> {
            LocalDate selectedDate = searchField.getValue();
            if (selectedDate != null) {
                filterByDate(selectedDate);
            } else {
                historyTable.setItems(fullData);
            }
        });
    }


    private void loadTableData() {
        try {
            List<ConsultationResDTO> consultations = ResAPI.findByIdMedecinDesc("consultation", idM, ConsultationResDTO.class);

            // Organiser les consultations par date (trié par ordre décroissant)
            Map<LocalDate, Integer> datePatientCountMap = new TreeMap<>((d1, d2) -> d2.compareTo(d1));
            for (ConsultationResDTO consultation : consultations) {
                LocalDate date = consultation.getJour();
                datePatientCountMap.put(date, datePatientCountMap.getOrDefault(date, 0) + 1);
            }

            fullData.setAll(datePatientCountMap.entrySet().stream()
                    .map(entry -> new Historique(entry.getKey(), entry.getValue()))
                    .collect(Collectors.toList()));

            historyTable.setItems(fullData);
            updateTotalPatients();
        } catch (Exception e) {
            log.error("Erreur lors du chargement des données", e);
            throw new RuntimeException(e);
        }
    }

    private void filterByDate(LocalDate queryDate) {
        if (queryDate == null) {
            historyTable.setItems(fullData);
        } else {
            ObservableList<Historique> filteredData = FXCollections.observableArrayList(
                    fullData.stream()
                            .filter(historique -> historique.getDate().equals(queryDate))
                            .collect(Collectors.toList())
            );
            historyTable.setItems(filteredData);
        }
        updateTotalPatients();
    }

    private void updateTotalPatients() {
        int total = historyTable.getItems().stream().mapToInt(Historique::getNombre).sum();
        totalPatientsLabel.setText(String.valueOf(total));
    }

    private void handleClose() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        if (stage != null) {
            stage.close();
        }
    }
}
