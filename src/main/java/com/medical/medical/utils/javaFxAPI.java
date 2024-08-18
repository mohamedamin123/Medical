package com.medical.medical.utils;

import com.medical.medical.controller.UIController.AccueilController;
import com.medical.medical.ennum.Utilisateurs;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Base64;

public class javaFxAPI {


    public static String  login(String email, String password, String role) throws Exception {
        String urlString = "http://localhost:8081/"+role.toLowerCase()+"s"+"/"+role.toLowerCase()+"/find-by-email/" + email;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        // Ajouter l'en-tête d'authentification Basic
        String auth = email + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } else {
            return "Failed : HTTP error code : " + responseCode;
        }
    }


}
