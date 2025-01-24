package com.medical.medical.controller.UIController.autre;

import com.medical.medical.models.dto.res.DrugResDTO;
import com.medical.medical.services.impl.DrugServiceImpl;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ListeDrugController {

    @Autowired
    private DrugServiceImpl drugService;

    @FXML
    private TextField searchBar;

    @FXML
    private Label resultLabel;

    @FXML
    private VBox drugInfoContainer;

    // Handle search button click
    @FXML
    public void onSearchClicked() {
        String activeIngredient = searchBar.getText();

        if (activeIngredient.isEmpty()) {
            resultLabel.setText("Please enter an active ingredient.");
            drugInfoContainer.getChildren().clear();
            resultLabel.setText("No information available for this ingredient.");

            return;
        }

        // Clear previous results
        drugInfoContainer.getChildren().clear();

        // Call the service to get drug information
        DrugResDTO drugData = drugService.getDrugInfo(activeIngredient);

        // Check if no information is available
        if ("Aucune information disponible pour cet ingrédient.".equals(drugData.getAvertissements())) {
            resultLabel.setText("No information available for this ingredient.");
        } else {
            displayDrugInfo(drugData);
        }
    }


    // Helper method to display drug information
// Helper method to display drug information
// Helper method to display drug information
    private void displayDrugInfo(DrugResDTO drugData) {
        drugInfoContainer.getChildren().clear();

        Label nameLabel = createLabelWithTooltip("Drug Name: " + searchBar.getText(), searchBar.getText());
        Label ingredientLabel = createLabelWithTooltip("Active Ingredient: " + formatList(drugData.getIngredientActif()), formatList(drugData.getIngredientActif()));
        Label usageLabel = createLabelWithTooltip("Usage: " + formatList(drugData.getUsage()), formatList(drugData.getUsage()));

        String avertissements = drugData.getAvertissements();
        Label warningsLabel = createLabelWithTooltip("Warnings: " + avertissements, avertissements);

        Label inactiveIngredientsLabel = createLabelWithTooltip("Inactive Ingredients: " + formatList(drugData.getIngredientsInactifs()), formatList(drugData.getIngredientsInactifs()));
        Label dosageLabel = createLabelWithTooltip("Dosage & Administration: " + formatList(drugData.getPosologieEtAdministration()), formatList(drugData.getPosologieEtAdministration()));

        // Ajouter tous les labels au conteneur
        drugInfoContainer.getChildren().addAll(nameLabel, ingredientLabel, usageLabel, warningsLabel, inactiveIngredientsLabel, dosageLabel);
    }

    // Helper method to create a Label with a Tooltip
    // Helper method to create a Label with a Tooltip
    private Label createLabelWithTooltip(String displayText, String tooltipText) {
        Label label = new Label(displayText);

        // Créer un Tooltip avec le texte complet
        Tooltip tooltip = new Tooltip(formatTooltipText(tooltipText)); // Formater le texte pour retour à la ligne
        tooltip.setShowDelay(Duration.millis(0)); // Pas de délai avant l'affichage
        tooltip.setMaxWidth(700); // Largeur maximale du Tooltip
        tooltip.setWrapText(true); // Activer le retour à la ligne automatique

        label.setWrapText(true); // Permettre le retour à la ligne dans le Label
        label.setTooltip(tooltip);



        tooltip.setShowDelay(Duration.millis(0)); // No delay before showing
        tooltip.setHideDelay(Duration.seconds(5)); // Optional: Hide after 5 seconds


        // Si le texte est trop long, tronquer et ajouter "..."
        if (displayText.length() > 150) { // Par exemple, 150 caractères
            String truncatedText = displayText.substring(0, 150) + "..."; // Tronquer le texte
            label.setText(truncatedText);
        }

        label.setOnMouseEntered(event -> {
            Tooltip.install(label, tooltip); // Afficher le Tooltip lorsqu'on entre dans le Label
        });

        label.setOnMouseExited(event -> {
            tooltip.hide(); // Cacher le Tooltip lorsque la souris quitte le Label
        });

        return label;
    }

    // Méthode pour formater le texte avec des retours à la ligne
    private String formatTooltipText(String text) {
        // Ajouter un retour à la ligne tous les 80 caractères par exemple
        StringBuilder formattedText = new StringBuilder();
        int lineLength = 360; // Nombre de caractères avant un retour à la ligne

        for (int i = 0; i < text.length(); i += lineLength) {
            int end = Math.min(text.length(), i + lineLength);
            formattedText.append(text, i, end).append("\n");
        }

        return formattedText.toString();
    }



    // Helper method to format lists for display
    private String formatList(List<String> list) {
        if (list == null || list.isEmpty()) {
            return "N/A";
        }
        return String.join(", ", list); // Les crochets ne seront pas présents
    }


}
