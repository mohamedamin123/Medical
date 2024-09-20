package com.medical.medical.controller.UIController.autre;

import com.medical.medical.models.dto.res.MedicamentResDTO;
import com.medical.medical.utils.ResAPI;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component("medicamentUI")
public class MedicamentController {

    @FXML
    private Button ajouter;
    @FXML
    private Button supprimer; // Add button for deleting
    @FXML
    private Button miseAJour; // Add button for updating
    @FXML
    private TextArea medicamentDescripAjouter;
    @FXML
    private TextArea medicamentDescrip;
    @FXML
    private TextField medicamentAjouter;
    @FXML
    private ComboBox<MedicamentResDTO> medicamentComboBox;

    private List<MedicamentResDTO> medicamentResDTOList = new ArrayList<>();
    private Integer idPatient;
    private Stage stage;

    @FXML
    private void initialize() {
        Platform.runLater(() -> {
            stage = (Stage) medicamentComboBox.getScene().getWindow();

            if (stage != null) {
                Object userData = stage.getUserData();
                if (userData instanceof Object[] data) {
                    idPatient = (Integer) data[0];
                    try {
                        getData();
                        populateComboBox();
                        replirCobox();
                        ajouter.setOnAction(this::handleAjouter);
                        supprimer.setOnAction(this::handleSupprimer); // Event handler for delete
                        miseAJour.setOnAction(this::handleMiseAJour); // Event handler for update

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    private void handleAjouter(ActionEvent event) {
        ajouterMedicament();
    }

    private void handleSupprimer(ActionEvent event) {
        MedicamentResDTO selectedMedicament = medicamentComboBox.getSelectionModel().getSelectedItem();
        if (selectedMedicament != null) {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de suppression de "+selectedMedicament.getNom());
            alert.setHeaderText("Êtes-vous sûr de vouloir supprimer ce médicament ?");
            alert.setContentText("Cette action ne peut pas être annulée.");

            if (alert.showAndWait().filter(ButtonType.OK::equals).isPresent()) {
                try {
                    ResAPI.deleteById("medicament", selectedMedicament.getIdMedicament()); // Assume there is a delete method
                    medicamentResDTOList.remove(selectedMedicament);
                    medicamentComboBox.getItems().remove(selectedMedicament);
                    if(medicamentResDTOList.isEmpty())
                        medicamentDescrip.clear();
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Erreur lors de la suppression du médicament");
                }
            }
        }
    }

    private void handleMiseAJour(ActionEvent event) {
        MedicamentResDTO selectedMedicament = medicamentComboBox.getSelectionModel().getSelectedItem();
        if (selectedMedicament != null) {
            String newDescription = medicamentDescrip.getText();
            if (newDescription.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Erreur de saisie");
                alert.setHeaderText("Description manquante");
                alert.setContentText("La description ne peut pas être vide.");
                alert.showAndWait();
                return;
            }

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation de mise à jour de "+selectedMedicament.getNom() );
            alert.setHeaderText("Êtes-vous sûr de vouloir mettre à jour la description ?");
            alert.setContentText("La mise à jour remplacera l'ancienne description.");

            if (alert.showAndWait().filter(ButtonType.OK::equals).isPresent()) {
                try {
                    selectedMedicament.setDescription(newDescription);
                    ResAPI.update("medicament", selectedMedicament); // Assume there is an update method
                    medicamentComboBox.getItems().set(medicamentComboBox.getItems().indexOf(selectedMedicament), selectedMedicament);
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Erreur lors de la mise à jour du médicament");
                }
            }
        }
    }

    public void replirCobox() {
        medicamentComboBox.setOnAction(event -> {
            MedicamentResDTO selectedMedicament = medicamentComboBox.getSelectionModel().getSelectedItem();
            if (selectedMedicament != null) {
                medicamentDescrip.setText(selectedMedicament.getDescription());
            }
        });
    }

    public void getData() throws Exception {
        medicamentResDTOList = ResAPI.findAllByIdPatient("medicament", MedicamentResDTO.class, idPatient);
        System.out.println(medicamentResDTOList.toString());

        if (medicamentResDTOList == null) {
            medicamentResDTOList = new ArrayList<>();
        }
    }

    private void populateComboBox() {
        medicamentComboBox.getItems().setAll(medicamentResDTOList);

        medicamentComboBox.setCellFactory(comboBox -> new ListCell<MedicamentResDTO>() {
            @Override
            protected void updateItem(MedicamentResDTO medicament, boolean empty) {
                super.updateItem(medicament, empty);
                setText(empty || medicament == null ? null : medicament.getNom());
            }
        });

        medicamentComboBox.setButtonCell(new ListCell<MedicamentResDTO>() {
            @Override
            protected void updateItem(MedicamentResDTO medicament, boolean empty) {
                super.updateItem(medicament, empty);
                setText(empty || medicament == null ? null : medicament.getNom());
            }
        });
    }

    private void ajouterMedicament() {
        String nomMedicament = medicamentAjouter.getText();
        String descriptionMedicament = medicamentDescripAjouter.getText();

        if (nomMedicament.isEmpty() || descriptionMedicament.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erreur de saisie");
            alert.setHeaderText("Champs requis manquants");
            alert.setContentText("Le nom et la description ne peuvent pas être vides.");
            alert.showAndWait();
            return;
        }

        MedicamentResDTO nouveauMedicament = MedicamentResDTO.builder()
                .nom(nomMedicament)
                .description(descriptionMedicament)
                .idPatient(idPatient)
                .build();

        try {
           ResAPI.save("medicament", nouveauMedicament);

           medicamentResDTOList.clear();
            medicamentAjouter.clear();
            medicamentDescripAjouter.clear();
            stage.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout du médicament");
        }
    }
}
