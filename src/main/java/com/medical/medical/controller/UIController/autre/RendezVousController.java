package com.medical.medical.controller.UIController.autre;

import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.RendezVousResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.utils.ResAPI;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;
@Component("rendez_vousUI")
public class RendezVousController {

    @FXML
    private TextField searchField;

    @FXML
    private DatePicker datePicker;

    @FXML
    private TableView<RendezVousResDTO> appointmentTable;

    @FXML
    private TableColumn<RendezVousResDTO, String> heureColumn;

    @FXML
    private TableColumn<RendezVousResDTO, String> patientColumn;

    @FXML
    private TableColumn<RendezVousResDTO, String> motifColumn;

    @FXML
    private TableColumn<RendezVousResDTO, String> jourColumn;

    @FXML
    private VBox rootLayout;

    private List<RendezVousResDTO> rendezVousList = new ArrayList<>();
    private List<PatientResDTO> patients = new ArrayList<>();

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String role;

    private MedecinResDTO medecin;
    private SecretaireResDTO secretaire;
    private Integer idM;

    public static Stage stageR;

    Object userData;

    // Debounce variables
    private Timer debounceTimer;
    private static final int DEBOUNCE_DELAY_MS = 300;

    @FXML
    public void initialize() {
        Platform.runLater(() -> {

            stageR = (Stage) datePicker.getScene().getWindow();

            if (stageR != null) {
                userData = stageR.getUserData();
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

            jourColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getJour() != null ? cellData.getValue().getJour().toString() : ""));

            heureColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getHeure() != null ? cellData.getValue().getHeure().toString() : ""));

            patientColumn.setCellValueFactory(cellData -> {
                try {
                    return new SimpleStringProperty(cellData.getValue().getIdPatient() != null ? namePatient(cellData.getValue().getIdPatient()) : "");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            });

            motifColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getMotif() != null ? cellData.getValue().getMotif() : ""));

            try {
                getData();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

            loadAppointments(LocalDate.now());

            // Configure listeners for date and search fields
            datePicker.setValue(LocalDate.now());
            datePicker.valueProperty().addListener((observable, oldValue, newValue) -> applyFilters());

            // Implement debounce for search field
            searchField.textProperty().addListener((observable, oldValue, newValue) -> {
                if (debounceTimer != null) {
                    debounceTimer.cancel();
                }
                debounceTimer = new Timer();
                debounceTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        Platform.runLater(() -> applyFilters());
                    }
                }, DEBOUNCE_DELAY_MS);
            });
        });
    }

    private String namePatient(Integer idPatient) throws Exception {
        Optional<PatientResDTO> p1 = Optional.ofNullable(ResAPI.findById("patient", idPatient, PatientResDTO.class));
        if (p1.isPresent()) {
            try {
                return p1.get().getFullName();
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    private void applyFilters() {
        LocalDate selectedDate = datePicker.getValue();
        String searchName = searchField.getText().trim().toLowerCase();

        List<RendezVousResDTO> filteredAppointments = rendezVousList.stream()
                .filter(rendezVous -> {
                    try {
                        return (selectedDate == null || rendezVous.getJour().equals(selectedDate))
                                && (searchName.isEmpty() || namePatient(rendezVous.getIdPatient()).toLowerCase().contains(searchName));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        appointmentTable.setItems(FXCollections.observableList(filteredAppointments));
    }

    public void loadAppointments(LocalDate date) {
        String searchName = searchField.getText().trim().toLowerCase();

        List<RendezVousResDTO> filteredAppointments = rendezVousList.stream()
                .filter(rendezVous -> {
                    try {
                        return (date == null || rendezVous.getJour().equals(date))
                                && (searchName.isEmpty() || namePatient(rendezVous.getIdPatient()).toLowerCase().contains(searchName));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        appointmentTable.setItems(FXCollections.observableList(filteredAppointments));
    }

    @FXML
    private void handleAddRendezVous() {
        try {
            changeFenetre("add_rendezvous", email, role, medecin, secretaire, idM);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEditRendezVous() {
        RendezVousResDTO selectedRendezVous = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedRendezVous != null) {
            try {
                changeFenetre("add_rendezvous", email, role, medecin, secretaire, idM, selectedRendezVous);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un rendez-vous à modifier.");
        }
    }

    @FXML
    private void handleDeleteRendezVous() throws Exception {
        RendezVousResDTO selectedRendezVous = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedRendezVous != null) {
            // Remove the selected appointment from the displayed list directly
            appointmentTable.getItems().remove(selectedRendezVous);

            // Remove the appointment from the full appointment list
            rendezVousList.remove(selectedRendezVous);

            // Delete the appointment from the API or database
            ResAPI.deleteById("rendezvous", selectedRendezVous.getIdRendezVous());

            // Optional: Refresh the table view
            appointmentTable.refresh();
        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un rendez-vous à supprimer.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void getData() throws Exception {
        rendezVousList.clear();
        List<RendezVousResDTO> exampleAppointments = ResAPI.findByIdMedecin("rendezvous", idM, RendezVousResDTO.class);
        if (exampleAppointments != null) {
            rendezVousList.addAll(exampleAppointments);
            Set<Integer> uniquePatientIds = new HashSet<>();

            for (RendezVousResDTO rendezVousResDTO : exampleAppointments) {
                Integer patientId = rendezVousResDTO.getIdPatient();
                if (!uniquePatientIds.contains(patientId)) {
                    PatientResDTO patient = ResAPI.findById("patient", patientId, PatientResDTO.class);
                    if (patient != null) {
                        patients.add(patient);
                        uniquePatientIds.add(patientId);
                    }
                }
            }
        } else {
            System.out.println("No appointments found for this Medecin.");
        }
        applyFilters(); // Apply filters to update the table view
    }

    public void retour(ActionEvent actionEvent) {
        stageR.close();
        if (medecin == null) {
            try {
                changeFenetre("acceuil", secretaire.getEmail(), "secretaire", medecin, secretaire, idM);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            try {
                changeFenetre("acceuil", medecin.getEmail(), "medecin", medecin, secretaire, idM);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
