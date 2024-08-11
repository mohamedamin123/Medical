package com.medical.medical.controller.UIController;

import com.medical.medical.controller.API.MedecinController;
import com.medical.medical.controller.API.SecretaireController;
import com.medical.medical.ennum.Utilisateurs;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.services.impl.MedecinServiceImpl;
import com.medical.medical.utils.javaFxAPI;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

@Component
@Slf4j
public class LoginController {

    @Autowired
    private MedecinController medecinController;

    @Autowired
    private SecretaireController secretaireController;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @FXML
    private Button myButton;
    @FXML
    private TextField emailConnecter;
    @FXML
    private PasswordField passwordConnecter;

    @FXML
    public void initialize() {
        myButton.setOnAction(event -> {
            String email = emailConnecter.getText().trim();
            String password = passwordConnecter.getText().trim();

            try {
                // Appel au service pour obtenir le mot de passe encodé
                String passwordM = medecinController.findPasswordByEmail(email);
                String passwordS = secretaireController.findPasswordByEmail(email);


                // Comparer le mot de passe fourni avec le mot de passe encodé stocké
                if (passwordEncoder.matches(password, passwordM)) {
                    // Appeler le point de terminaison sécurisé avec HTTP Basic Authentication
                    String response = javaFxAPI.login(email, password,"medecin");
                    log.info(response);
                } else  if(passwordEncoder.matches(password, passwordS)){
                    String response = javaFxAPI.login(email, password, "secretaire");
                    log.info(response);
                } else {
                    log.info("non");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}
