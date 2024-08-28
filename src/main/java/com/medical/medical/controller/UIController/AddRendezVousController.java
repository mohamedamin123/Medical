package com.medical.medical.controller.UIController;

import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.controller.API.PatientController;
import com.medical.medical.controller.API.RendezVousController;
import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.req.RendezVousReqDTO;

import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.RendezVousResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import com.medical.medical.utils.PatientItem;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.medical.medical.controller.UIController.RendezVousController.stageR;
import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class AddRendezVousController {

    @FXML
    private DatePicker datePicker;

    @FXML
    private ComboBox<LocalTime> heureComboBox;

    @FXML
    private TextField medecinComboBox;

    @FXML
    private ComboBox<PatientItem> patientComboBox;

    @FXML
    private TextField motifTextField;

    @Autowired
    private RendezVousController rendezVousController;

    @Autowired
    private PatientController patientController;

    @Autowired
    private MedecinController medecinController;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String role;

    @Autowired
    private com.medical.medical.controller.UIController.RendezVousController rendezVousControllerUI;

    private MedecinResDTO medecin;
    private SecretaireResDTO secretaire;

    private Integer idM;

    private Stage stage;
    Object userData;
    RendezVousResDTO resDTO;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) datePicker.getScene().getWindow();
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

            heureComboBox.setItems(FXCollections.observableArrayList(
                    LocalTime.of(8, 0), LocalTime.of(9, 0), LocalTime.of(10, 0),
                    LocalTime.of(11, 0), LocalTime.of(13, 0), LocalTime.of(14, 0),
                    LocalTime.of(15, 0), LocalTime.of(16, 0), LocalTime.of(17, 0)
            ));

            medecinComboBox.setText(getNomMedecin());
            List<PatientItem> patientList = getNomPatient(idM);

            // Set the items of the ComboBox with PatientItem objects
            patientComboBox.setItems(FXCollections.observableArrayList(patientList));

            // Customize how the items are displayed
            patientComboBox.setCellFactory(cell -> new javafx.scene.control.ListCell<PatientItem>() {
                @Override
                protected void updateItem(PatientItem item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            });

            patientComboBox.setButtonCell(new javafx.scene.control.ListCell<PatientItem>() {
                @Override
                protected void updateItem(PatientItem item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item.getName());
                    }
                }
            });
            if (userData instanceof Object[] data) {
                if (data.length > 5 ){
                    resDTO = (data[5] instanceof RendezVousResDTO) ? (RendezVousResDTO) data[5] : null;

                    datePicker.setValue(resDTO.getJour());
                    heureComboBox.setValue(resDTO.getHeure());
                    motifTextField.setText(resDTO.getMotif());
                    PatientItem p=new PatientItem(getNomPatientS(resDTO.getIdPatient()),resDTO.getIdPatient());
                    patientComboBox.setValue(p);
                }
            }
        });
    }


    @FXML
    private void handleAddRendezVous() {
        // Récupérer les valeurs saisies
        LocalDate date = datePicker.getValue();
        LocalTime heure = heureComboBox.getValue();
        PatientItem selectedPatient = patientComboBox.getValue(); // Use PatientItem instead of String

        Integer idPatient = (selectedPatient != null) ? selectedPatient.getId() : null; // Get ID from PatientItem
        String motif = motifTextField.getText();

        // Validation des champs
        if (date == null || heure == null || idPatient == null || motif.isEmpty()) {
            showAlert("Champs manquants", "Veuillez remplir tous les champs.");
            return;
        }
        List<PatientItem> patientList = getNomPatient(idM);
        List<Integer> fullNameList = patientList.stream()
                .map(PatientItem::getId) // Assuming PatientItem has a method getFullName() that returns the full name
                .toList();
        // Créer un nouveau rendez-vous
        RendezVousReqDTO newRendezVous = RendezVousReqDTO.builder()
                .jour(date)
                .heure(heure)
                .idMedecin(idM)
                .idPatient(idPatient)
                .motif(motif)
                .build();

        rendezVousControllerUI.loadAppointments(date);

        if(resDTO==null) {
            rendezVousController.saveRendezVous(newRendezVous);
        }else {
            newRendezVous.setIdRendezVous(resDTO.getIdRendezVous());
            rendezVousController.updateRendezVous(newRendezVous);
        }



        stage.close();
        stageR.close();
        try {
            if(medecin==null)
            {

                changeFenetre("rendez_vous",secretaire.getEmail(),"secretaire",medecin,secretaire,idM);
            }

            else {
                changeFenetre("rendez_vous",medecin.getEmail(),"medecin",medecin,secretaire,idM);
            }

        } catch (IOException e) {
            log.error("Error changing window", e);
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
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

    private String getNomPatientS(Integer id) {


        // Fetch the list of patients associated with the given Medecin ID
        Optional<PatientResDTO> patients = patientController.findPatientById(id);

        String name="";
        if(patients.isPresent()) {
            try {
                name=patients.get().getFullName();
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
        }

        return name;
    }

    private String getNomMedecin() {
        Optional<MedecinResDTO> medecinResDTO=medecinController.findMedecinById(idM);
        String name="";

        if(medecinResDTO.isPresent()) {
            try {
                name=medecinResDTO.get().getFullName();
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
        }else {
            try {
                changeFenetre("login");
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return name;
    }

}
