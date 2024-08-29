package com.medical.medical.controller.UIController;

import com.medical.medical.controller.API.ConsultationController;
import com.medical.medical.models.dto.res.ConsultationResDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.utils.Historique;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private Button closeButton;

    @Autowired
    private ConsultationController controller;

    Stage stage;
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
            stage = (Stage) closeButton.getScene().getWindow();
            if (stage != null) {
                Object userData = stage.getUserData();
                if (userData instanceof Object[] data) {
                    if (data.length >= 4) {
                        email = (String) data[0];
                        role = (String) data[1];
                        medecin = (data[2] instanceof MedecinResDTO) ? (MedecinResDTO) data[2] : null;
                        secretaire = (data[3] instanceof SecretaireResDTO) ? (SecretaireResDTO) data[3] : null;
                        idM = (Integer) data[4];
                    }
                }
            }

            // Configure les colonnes du TableView
            dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
            patientCountColumn.setCellValueFactory(new PropertyValueFactory<>("nombre"));

            // Remplir le TableView avec des données
            loadTableData();

            // Gestion de l'événement pour le bouton "Fermer"
            closeButton.setOnAction(event -> handleClose());
        });
    }

    private void loadTableData() {
        LocalDate jour = LocalDate.now();
        List<ConsultationResDTO> consultations = controller.findConsultationsByIdMedecinAndJour(idM, jour);

        // Créer une Map pour compter les patients par date
        Map<LocalDate, Integer> datePatientCountMap = new HashMap<>();
        for (ConsultationResDTO consultation : consultations) {
            LocalDate date = consultation.getJour(); // Assurez-vous que la méthode getDate() renvoie LocalDate
            datePatientCountMap.put(date, datePatientCountMap.getOrDefault(date, 0) + 1);
        }

        // Convertir la Map en liste observable pour TableView
        ObservableList<Historique> data = FXCollections.observableArrayList();
        for (Map.Entry<LocalDate, Integer> entry : datePatientCountMap.entrySet()) {
            LocalDate date = entry.getKey();
            int patientCount = entry.getValue();
            data.add(new Historique(date, patientCount));
        }


        // Remplir le TableView avec les données
        historyTable.setItems(data);
    }

    private void handleClose() {
        if (historyTable.getScene() != null) {
            historyTable.getScene().getWindow().hide();
        }
    }
}
