package com.medical.medical.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medical.medical.controller.UIController.autre.LoginController;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;

public class ResAPI {

    private static final String URL_API = "http://192.168.163.66:8081/";
    private static final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper

    static {
        objectMapper.registerModule(new JavaTimeModule());
    }


    private static <T> String sendRequest(String endpoint, String method, T data) throws Exception {

        String email = LoginController.getSavedEmail();
        String password = LoginController.getSavedPassword();

        URL url = new URL(URL_API + endpoint);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod(method);

        // Add Basic Authentication header
        String auth = email + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

        // Handle request body for POST, PUT requests
        if ("POST".equalsIgnoreCase(method) || "PUT".equalsIgnoreCase(method)) {
            if (data != null) {
                connection.setDoOutput(true);
                connection.setRequestProperty("Content-Type", "application/json"); // assuming JSON data
                String jsonData = objectMapper.writeValueAsString(data); // Convert data to JSON
                try (OutputStream os = connection.getOutputStream()) {
                    byte[] input = jsonData.getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
            }
        }

        int responseCode = connection.getResponseCode();
        StringBuilder response = new StringBuilder();
        try (BufferedReader in = new BufferedReader(new InputStreamReader(
                responseCode < 400 ? connection.getInputStream() : connection.getErrorStream()))) {
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
        }

        if (responseCode >= 400) {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode + " - " + response.toString());
        }

        return response.toString();
    }

    public static <T> List<T> findAll(String role, Class<T> clazz) throws Exception {
        String endpoint = role.toLowerCase() + "s/" + role.toLowerCase() + "/find-all";
        try {
            String jsonResponse = sendRequest(endpoint, "GET", null);
            return objectMapper.readValue(jsonResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("HTTP error code : 404")) {
                // Handle the 404 error specifically
                System.out.println("Resource not found for role: " + role);
                return null; // or handle as needed
            }
            throw e; // rethrow other exceptions
        }
    }

    public static <T> T findById(String role, Integer id, Class<T> clazz) throws Exception {
        String endpoint = role.toLowerCase() + "s/" + role.toLowerCase() + "/find-by-id/" + id;
        try {
            String jsonResponse = sendRequest(endpoint, "GET", null);
            return objectMapper.readValue(jsonResponse, clazz);
        } catch (RuntimeException e) {
            if (e.getMessage().contains("HTTP error code : 404")) {
                // Handle the 404 error specifically
                System.out.println("Resource not found for role: " + role + " with ID: " + id);
                return null; // or handle as needed
            }
            throw e; // rethrow other exceptions
        }
    }


    public static <T> List<T> findByIdMedecin(String role, Integer id, Class<T> clazz) throws Exception {
        String endpoint = role + "s/" + role + "/find-all-by-medecin-id?id=" + id;
        try {
            String jsonResponse = sendRequest(endpoint, "GET", null);
            return objectMapper.readValue(jsonResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("HTTP error code : 404")) {
                // Handle the 404 error specifically
                System.out.println("Resource not found for role: " + role + " with ID: " + id);
                return null; // or handle as needed
            }
            throw e; // rethrow other exceptions
        }
    }

    public static <T> List<T> findByIdMedecinAndJOur(String role, Integer id, LocalDate jour, Class<T> clazz) throws Exception {
        String endpoint = role + "s/" + role + "/find-all-by-medecin-id-and-date?id=" + id + "&jour=" + jour;

        try {
            String jsonResponse = sendRequest(endpoint, "GET", null);
            return objectMapper.readValue(jsonResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("HTTP error code : 404")) {
                // Handle the 404 error specifically
                System.out.println("Resource not found for role: " + role + " with ID: " + id);
                return null; // or handle as needed
            }
            throw e; // rethrow other exceptions
        }
    }


    public static <T> String save(String role, T data) throws Exception {
        String endpoint = role.toLowerCase() + "s/" + role.toLowerCase() + "/save";
        return sendRequest(endpoint, "POST", data);
    }

    public static <T> String update(String role, T data) throws Exception {
        String endpoint = role.toLowerCase() + "s/" + role.toLowerCase() + "/update";
        return sendRequest(endpoint, "PUT", data);
    }

    public static String deleteById(String role, Integer id) throws Exception {
        String endpoint = role.toLowerCase() + "s/" + role.toLowerCase() + "/delete-by-id/" + id;
        return sendRequest(endpoint, "DELETE", null);
    }

    public static <T> String delete(String role, T data) throws Exception {
        String endpoint = role.toLowerCase() + "s/" + role.toLowerCase() + "/delete";
        return sendRequest(endpoint, "DELETE", data);
    }
}
