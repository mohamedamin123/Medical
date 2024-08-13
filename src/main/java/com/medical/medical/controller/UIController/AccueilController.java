package com.medical.medical.controller.UIController;

import com.medical.medical.utils.javaFxAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Slf4j
public class AccueilController {

    @FXML
    private ImageView profile;
    @FXML
    private ImageView patient;
    @FXML
    private ImageView rendez_vous;
    @FXML
    private ImageView secretaires;

    @FXML
    public void initialize() {

    }
}
