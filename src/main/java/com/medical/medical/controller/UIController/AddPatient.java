package com.medical.medical.controller.UIController;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

import java.io.File;

@Component
public class AddPatient {

    @FXML
    private Button chargerFichierButton;

    @FXML
    private void handleChargerFichier() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Charger un fichier");

        // Set file type filters
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Documents", "*.pdf", "*.docx", "*.doc"),
                new FileChooser.ExtensionFilter("Images", "*.png", "*.jpg", "*.gif"),
                new FileChooser.ExtensionFilter("All Files", "*.*")
        );

        // Show open file dialog
        File selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            // Handle the file (e.g., display the file name, process the file, etc.)
            System.out.println("Fichier sélectionné: " + selectedFile.getAbsolutePath());
        }
    }
}
