package com.medical.medical.controller.UIController.admin;


import com.medical.medical.exceptions.UserException;
import com.medical.medical.models.dto.res.AdminResDTO;
import com.medical.medical.models.dto.res.MedecinResDTO;
import com.medical.medical.models.dto.res.SecretaireResDTO;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Objects;

import static com.medical.medical.utils.javaFxAPI.changeFenetre;


@Slf4j
@Component
public class HomeAdminController {
    @FXML
    private ImageView profile;
    @FXML
    private ImageView secretaireI;
    @FXML
    private ImageView deconnecter;
    @FXML
    private ImageView medecin;
    @FXML
    private Label name;

    @Setter
    @Getter
    private String email;

    @Setter
    @Getter
    private String role;


    private Stage stage;
    private AdminResDTO adminResDTO;


    @FXML
    public void initialize() {
        Platform.runLater(() -> {
                    stage = (Stage) name.getScene().getWindow();
                    if (stage != null) {
                        Object userData = stage.getUserData();
                        if (userData instanceof Object[] data) {
                            if (data.length >= 2) {
                                email = (String) data[0];
                                role = (String) data[1];
                                adminResDTO = (data[2] instanceof AdminResDTO) ? (AdminResDTO) data[2] : null;

                            } else if(data.length == 1) {
                                adminResDTO = (data[0] instanceof AdminResDTO) ? (AdminResDTO) data[0] : null;
                                assert adminResDTO != null;
                                email = (adminResDTO.getEmail());
                                role = "admin";
                            }
                            getFullNames(adminResDTO,email, role);
                            profile.setOnMouseClicked(event -> {
                                try {
                                    changeFenetre("profileAdmin", adminResDTO);
                                    stage.close();
                                } catch (IOException e) {
                                    log.error("Error changing window", e);
                                }
                            });

                            deconnecter.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    deconnecterMethode();
                                }
                            });
                            medecin.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    try {
                                        changeFenetre("liste_medecin", adminResDTO);
                                        stage.close();
                                    } catch (IOException e) {
                                        log.error("Error changing window", e);
                                    }
                                }
                            });
                            secretaireI.setOnMouseClicked(new EventHandler<MouseEvent>() {
                                @Override
                                public void handle(MouseEvent mouseEvent) {
                                    try {
                                        changeFenetre("liste_secretaires", adminResDTO);
                                        stage.close();
                                    } catch (IOException e) {
                                        log.error("Error changing window", e);
                                    }
                                }
                            });
                        }
                    }
                }
        );
    }

    private void getFullNames(AdminResDTO adminResDTO, String email, String role) {
        if (email != null && role != null) {


            if (role.equals("admin")) {
                if (adminResDTO != null) {
                    try {
                        name.setText("Bonjour Mr. " + adminResDTO.getFullName());
                    } catch (UserException e) {
                        log.error("Error retrieving Medecin details", e);
                        name.setText("Error retrieving Medecin details");
                    }
                } else {
                    log.error("Medecin data is null for email: " + email);
                    name.setText("Medecin data is not available");
                }
            }else {
                log.error("Unknown role: " + role);
                name.setText("Unknown role");
            }
        } else {
            log.error("Email or Role is null");
            name.setText("Email or Role is null");
        }
    }

    private void deconnecterMethode() {
        try {
            changeFenetre("login");
            stage.close();
        } catch (IOException e) {
            log.error("Error changing window", e);
        }
    }

}
