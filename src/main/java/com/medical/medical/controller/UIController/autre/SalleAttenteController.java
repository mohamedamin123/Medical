package com.medical.medical.controller.UIController.autre;

import com.medical.medical.controller.UIController.JavaFXApp;
import com.medical.medical.controller.UIController.ajouter.AjoutPatientAttController;
import com.medical.medical.models.dto.res.*;
import com.medical.medical.utils.PatientItem;
import com.medical.medical.utils.ResAPI;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class SalleAttenteController {

    @FXML private TableView<PatientItem> patientTable;
    @FXML private TableColumn<PatientItem, String> nameColumn;
    @FXML private TableColumn<PatientItem, String> timeColumn;
    @FXML private Label patientCountLabel;
    @FXML private Button addPatientButton;
    @FXML private Button removePatientButton;

    private ObservableList<PatientItem> patientList = FXCollections.observableArrayList();
    private int patientCount = 0;
    public static Stage stagee;

    @Setter @Getter private String email;
    @Setter @Getter private String role;
    private MedecinResDTO medecin;
    private SecretaireResDTO secretaire;
    private Integer idM;
    private Object userData;

    private static final DateTimeFormatter TIME_FORMATTER = DateTimeFormatter.ofPattern("HH:mm");

    @FXML
    public void initialize() {
        Platform.runLater(() -> {
            stagee = (Stage) patientCountLabel.getScene().getWindow();
            if (stagee != null) {
                userData = stagee.getUserData();
                if (userData instanceof Object[] data && data.length >= 4) {
                    email = (String) data[0];
                    role = (String) data[1];
                    medecin = (MedecinResDTO) data[2];
                    secretaire = (SecretaireResDTO) data[3];
                    idM = (Integer) data[4];
                }
            }

            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));
            patientTable.setItems(patientList);
            addPatientButton.setOnAction(event -> openAddPatientWindow());
            removePatientButton.setOnAction(event -> removePatient());
            updatePatientCount();
        });
    }

    private void openAddPatientWindow() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/ajouter_patient_att.fxml"));
            loader.setControllerFactory(JavaFXApp.getSpringContext()::getBean);
            Parent root = loader.load();

            AjoutPatientAttController controller = loader.getController();
            controller.setSalleAttenteController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setUserData(new Object[]{email, role, medecin, secretaire, idM});
            stage.showAndWait();
        } catch (IOException e) {
            log.error("Erreur lors de l'ouverture de la fenêtre d'ajout de patient", e);
        }
    }

    public void addPatient(String name, String time) {
        PatientItem newPatient = new PatientItem(name, time);
        patientList.add(newPatient);
        log.info("Added new patient: {} at {}", name, time);
        updatePatientCount();
    }

    private void removePatient() {
        PatientItem selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            patientList.remove(selectedPatient);
            try {
                ResAPI.deleteById("consultation", selectedPatient.getId());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            updatePatientCount();
        } else {
            log.warn("No patient selected for removal.");
        }
    }

    private void updatePatientCount() {
        try {
            getDate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void getDate() throws Exception {
        patientList.clear();
        LocalDate jour = LocalDate.now();

        List<ConsultationResDTO> consultations = ResAPI.findByIdMedecinAndJOur("consultation", idM, jour, ConsultationResDTO.class);
        List<PatientResDTO> patients = ResAPI.findByIdMedecin("patient", idM, PatientResDTO.class);

        Map<Integer, String> patientIdToNameMap = new HashMap<>();
        for (PatientResDTO patient : patients) {
            patientIdToNameMap.put(patient.getIdPatient(), patient.getFullName());
        }

        List<PatientItem> patientItems = new ArrayList<>();
        for (ConsultationResDTO consultation : consultations) {
            Integer patientId = consultation.getIdPatient();
            String patientName = patientIdToNameMap.get(patientId);
            String arrivalTime = consultation.getHeure().format(TIME_FORMATTER);

            if (patientName != null) {
                patientItems.add(new PatientItem(patientName, arrivalTime, consultation.getIdConsultation()));
            }
        }

        patientList.addAll(patientItems);
        patientCount = patientList.size();
        patientCountLabel.setText(String.valueOf(patientCount));
    }

    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        stagee.close();
        navigateTo("acceuil");
    }

    @FXML
    private void handleHistorique(ActionEvent actionEvent) {
        navigateTo("historique");
    }

    private void navigateTo(String destination) {
        try {
            if (medecin == null) {
                changeFenetre(destination, secretaire.getEmail(), "secretaire", medecin, secretaire, idM);
            } else {
                changeFenetre(destination, medecin.getEmail(), "medecin", medecin, secretaire, idM);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
