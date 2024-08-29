package com.medical.medical.controller.UIController;

import com.medical.medical.controller.API.PatientController;
import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.PatientResDTO;
import com.medical.medical.models.dto.res.RendezVousResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;

@Component("uiRendezVousController")
public class RendezVousController {

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

    @Autowired
    private com.medical.medical.controller.API.RendezVousController rendezVousController;

    @Autowired
    private PatientController patientController;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String role;

    private MedecinResDTO medecin;
    private SecretaireResDTO secretaire;

    private Integer idM;

    public static   Stage stageR;

    Object userData;

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
            // Assurez-vous que chaque colonne a un cellValueFactory correctement configuré
            jourColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getJour() != null ? cellData.getValue().getJour().toString() : ""));

            heureColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getHeure() != null ? cellData.getValue().getHeure().toString() : ""));



            patientColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getIdPatient() != null ? namePatient(cellData.getValue().getIdPatient()) : ""));

            motifColumn.setCellValueFactory(cellData ->
                    new SimpleStringProperty(cellData.getValue().getMotif() != null ? cellData.getValue().getMotif() : ""));



            getData();
            loadAppointments(LocalDate.now());

            // Configurer l'écouteur pour le sélecteur de date
            datePicker.setValue(LocalDate.now());
            datePicker.valueProperty().addListener((observable, oldValue, newValue) -> loadAppointments(newValue));
        });
    }

    private String namePatient(Integer idPatient) {
        Optional<PatientResDTO> p1=patientController.findPatientById(idPatient);
        if(p1.isPresent()) {
            try {
                return p1.get().getFullName();
            } catch (UserException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }

    public void loadAppointments(LocalDate date) {
        // Effacer le tableau actuel
        appointmentTable.getItems().clear();

        // Filtrer les rendez-vous par date sélectionnée
        List<RendezVousResDTO> filteredAppointments = new ArrayList<>();
        for (RendezVousResDTO rendezVous : rendezVousList) {
            if (rendezVous.getJour().equals(date)) {
                filteredAppointments.add(rendezVous);
            }
        }

        // Mettre à jour le tableau avec les rendez-vous filtrés
        appointmentTable.setItems(FXCollections.observableList(filteredAppointments));
    }

    @FXML
    private void handleAddRendezVous() {
        // Logique pour ajouter un nouveau rendez-vous

        try {
            changeFenetre("add_rendezvous",email,role,medecin,secretaire,idM);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleEditRendezVous() {
        // Logique pour modifier un rendez-vous sélectionné
        RendezVousResDTO selectedRendezVous = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedRendezVous != null) {
            try {
                changeFenetre("add_rendezvous",email,role,medecin,secretaire,idM,selectedRendezVous);


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        } else {
            showAlert("Aucune sélection", "Veuillez sélectionner un rendez-vous à modifier.");
        }
    }

    @FXML
    private void handleDeleteRendezVous() {
        // Logique pour supprimer un rendez-vous sélectionné
        RendezVousResDTO selectedRendezVous = appointmentTable.getSelectionModel().getSelectedItem();
        if (selectedRendezVous != null) {
            rendezVousList.remove(selectedRendezVous);
            loadAppointments(datePicker.getValue());
            rendezVousController.deleteRendezVousById(selectedRendezVous.getIdRendezVous());
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

    private void getData() {
        // Exemple de données fictives pour les rendez-vous
        rendezVousList.clear();

       List<RendezVousResDTO> exampleAppointments= rendezVousController.findRendezVousByIdMedecin(idM);



        // Ajouter les rendez-vous fictifs à la liste
        rendezVousList.addAll(exampleAppointments);

        // Charger les rendez-vous pour la date actuelle
        loadAppointments(datePicker.getValue());
    }


    public void retour(ActionEvent actionEvent) {
        stageR.close();
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
}
