package com.medical.medical.controller.UIController.autre;

import com.medical.medical.controller.API.ConsultationController;
import com.medical.medical.controller.API.PatientController;
import com.medical.medical.controller.UIController.JavaFXApp;
import com.medical.medical.controller.UIController.ajouter.AjoutPatientAttController;
import com.medical.medical.models.dto.res.*;
import com.medical.medical.utils.PatientItem;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component
@Slf4j
public class SalleAttenteController {

    @FXML
    private TableView<PatientItem> patientTable;

    @FXML
    private TableColumn<PatientItem, String> nameColumn;

    @FXML
    private TableColumn<PatientItem, String> timeColumn;

    @FXML
    private Label patientCountLabel;

    @FXML
    private Button addPatientButton;

    @FXML
    private Button removePatientButton;

    private ObservableList<PatientItem> patientList = FXCollections.observableArrayList();

    private int patientCount = 0;

    public static  Stage stagee;
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

    @Autowired
    private ConsultationController controller;

    @Autowired
    private PatientController patientController;


    @FXML
    public void initialize() {

        Platform.runLater(() -> {
            stagee = (Stage) patientCountLabel.getScene().getWindow();
            if (stagee != null) {
                userData = stagee.getUserData();
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
            // Initialisation des colonnes de la TableView
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
            timeColumn.setCellValueFactory(new PropertyValueFactory<>("arrivalTime"));

            // Liaison des données à la TableView
            patientTable.setItems(patientList);

            // Gestion de l'événement pour ajouter un patient
            addPatientButton.setOnAction(event -> openAddPatientWindow());

            // Gestion de l'événement pour supprimer un patient
            removePatientButton.setOnAction(event -> removePatient());

            updatePatientCount();

        });



    }

    private void openAddPatientWindow() {
        try {
            ConfigurableApplicationContext springContext = JavaFXApp.getSpringContext();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/templates/ajouter_patient_att.fxml"));
            loader.setControllerFactory(springContext::getBean);
            Parent root = loader.load();


            AjoutPatientAttController controller = loader.getController();
            controller.setSalleAttenteController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setUserData(new Object[]{email, role, medecin, secretaire,idM});
            stage.showAndWait();
        } catch (IOException e) {
            log.error("Erreur lors de l'ouverture de la fenêtre d'ajout de patient", e);
        }
    }
    public void addPatient(String name, String time) {
        PatientItem newPatient = new PatientItem(name, time);
        patientList.add(newPatient);
        log.info("Added new patient: {} at {}", newPatient.getName(), time);
        updatePatientCount();
    }
    private void removePatient() {
        PatientItem selectedPatient = patientTable.getSelectionModel().getSelectedItem();
        if (selectedPatient != null) {
            patientList.remove(selectedPatient);
            controller.deleteConsultationById(selectedPatient.getId());
            updatePatientCount();
        } else {
            log.warn("No patient selected for removal.");
        }
    }

    private void updatePatientCount() {
        getDate();
    }

    private void getDate() {
        // Vide la liste actuelle de patients
        patientList.clear();

        // Récupère la date actuelle
        LocalDate jour = LocalDate.now();

        // Récupère les consultations et les patients pour le médecin et la date spécifiés
        List<ConsultationResDTO> exampleAppointments = controller.findConsultationsByIdMedecinAndJour(idM, jour);
        List<PatientResDTO> patients = patientController.findPatientsByMedecinId(idM);

        // Crée une Map pour associer les ID des patients avec leurs noms
        Map<Integer, String> patientIdToNameMap = new HashMap<>();
        for (PatientResDTO patient : patients) {
            try {
                patientIdToNameMap.put(patient.getIdPatient(), patient.getFullName()); // Associe l'ID du patient avec son nom
            } catch (Exception e) {
                log.error("Erreur lors de l'ajout du patient à la Map", e);
            }
        }

        // Liste temporaire pour les éléments patients
        List<PatientItem> patientItems = new ArrayList<>();

        // Ajoute les patients à la liste des éléments en fonction des rendez-vous
        for (ConsultationResDTO consultation : exampleAppointments) {
            Integer patientId = consultation.getIdPatient(); // Récupère l'ID du patient à partir de la DTO
            String patientName = patientIdToNameMap.get(patientId); // Récupère le nom du patient à partir de la Map
            String arrivalTime = consultation.getHeure().toString(); // Obtient l'heure d'arrivée à partir de la DTO
            int consultationId = consultation.getIdConsultation(); // Obtient l'ID de consultation à partir de la DTO

            if (patientName != null) {
                // Crée un nouvel élément PatientItem avec l'ID de consultation et ajoute-le à la liste

                PatientItem patientItem = new PatientItem(patientName, arrivalTime, consultationId);
                patientItems.add(patientItem);
            } else {
                log.warn("Nom du patient non trouvé pour l'ID: {}", patientId);
            }
        }

        // Ajoute les éléments patients à la liste de la TableView
        patientList.addAll(patientItems);

        // Met à jour le compteur de patients
        patientCount = patientList.size();
        patientCountLabel.setText(String.valueOf(patientCount));
    }



    @FXML
    private void handleCancel(ActionEvent actionEvent) {
        stagee.close();
        if(medecin==null)
        {

            try {
                changeFenetre("acceuil",secretaire.getEmail(),"secretaire",medecin,secretaire,idM);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        else {
            try {
                changeFenetre("acceuil",medecin.getEmail(),"medecin",medecin,secretaire,idM);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @FXML
    private void handleHistorique(ActionEvent actionEvent) {
        if(medecin==null)
        {

            try {
                changeFenetre("historique",secretaire.getEmail(),"secretaire",medecin,secretaire,idM);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        else {
            try {
                changeFenetre("historique",medecin.getEmail(),"medecin",medecin,secretaire,idM);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    // Classe interne pour représenter un patient

}
