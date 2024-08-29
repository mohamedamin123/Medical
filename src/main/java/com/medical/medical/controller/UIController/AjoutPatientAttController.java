package com.medical.medical.controller.UIController;

import com.medical.medical.controller.API.ConsultationController;
import com.medical.medical.controller.API.PatientController;
import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.req.ConsultationReqDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.utils.PatientItem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static com.medical.medical.controller.UIController.SalleAttenteController.stagee;
import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class AjoutPatientAttController {

    @FXML
    private ComboBox<PatientItem>  name;

    @FXML
    private TextField timeField;

    @FXML
    private Button cancelButton;

    @FXML
    private Button confirmButton;

    @Autowired
    @Setter
    private SalleAttenteController salleAttenteController;

    @Autowired
    private ConsultationController controller;

    @Autowired
    private PatientController patientController;
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



    Object userData;

    @FXML
    public void initialize() {
        // Ensure patientController is properly injected
        if (patientController == null) {
            log.error("PatientController is not injected.");
            return;
        }

        Platform.runLater(() -> {
            try {
                stage = (Stage) timeField.getScene().getWindow();  // Ensure timeField is part of the scene graph
                if (stage != null) {
                    userData = stage.getUserData();
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

                List<PatientItem> patientList = getNomPatient(idM);
                name.setItems(FXCollections.observableArrayList(patientList));

                // Customize how the items are displayed in the dropdown
                name.setCellFactory(cell -> new javafx.scene.control.ListCell<PatientItem>() {
                    @Override
                    protected void updateItem(PatientItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getName()); // Display only the name
                        }
                    }
                });

                // Customize how the selected item is displayed
                name.setButtonCell(new javafx.scene.control.ListCell<PatientItem>() {
                    @Override
                    protected void updateItem(PatientItem item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item == null || empty) {
                            setText(null);
                        } else {
                            setText(item.getName()); // Display only the name
                        }
                    }
                });

                // Initialize other UI components
                String currentTime = LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
                timeField.setText(currentTime);

                cancelButton.setOnAction(event -> closeWindow());

                confirmButton.setOnAction(event -> {
                    PatientItem selectedPatient = name.getValue(); // Get the selected PatientItem
                    String patientName = (selectedPatient != null) ? selectedPatient.getName() : null;
                    Integer idPatient = (selectedPatient != null) ? selectedPatient.getId() : null;

                    if (patientName != null && !patientName.isEmpty()) {
                        salleAttenteController.addPatient(patientName, currentTime);
                        closeWindow();
                        stagee.close();
                        ConsultationReqDTO consultationReqDTO = new ConsultationReqDTO(idM, idPatient, LocalDate.now(), LocalTime.now());
                        controller.saveConsultation(consultationReqDTO);
                        try {
                            if(medecin==null)
                            {

                                changeFenetre("salle_attente",secretaire.getEmail(),"secretaire",medecin,secretaire,idM);

                            }

                            else {
                                changeFenetre("salle_attente",medecin.getEmail(),"medecin",medecin,secretaire,idM);
                            }
                            stage.close();
                        } catch (IOException e) {
                            log.error("Error changing window", e);
                        }




                    } else {
                        log.warn("Le nom du patient est vide.");
                    }
                });
            } catch (Exception e) {
                log.error("Erreur lors de l'initialisation du contrôleur d'ajout de patient", e);
            }
        });
    }




    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    private List<PatientItem> getNomPatient(Integer id) {


        // Fetch the list of patients associated with the given Medecin ID
        List<PatientResDTO> patients = patientController.findPatientsByMedecinId(id);

        // Create a list to store the FullName and ID pairs
        List<PatientItem> listNom = new ArrayList<>();

        // Iterate over the list of PatientResDTO objects
        for (PatientResDTO pt : patients) {
            try {
                // Add a pair (FullName, ID) to the list
                listNom.add(new PatientItem(pt.getFullName(),pt.getIdPatient()));
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
        }
        return listNom;
    }

}
