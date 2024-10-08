package com.medical.medical.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.medical.medical.controller.UIController.autre.LoginController;
import com.medical.medical.models.dto.res.DrugResDTO;
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

    private static final String IP_ADDRESS = "localhost"; // Obtenir l'IP de la machine locale
    private static final String URL = "http://" + IP_ADDRESS; // URL base avec l'IP récupérée
    private static final String URL_API = URL+":8081/";
    private static final ObjectMapper objectMapper = new ObjectMapper(); // Jackson ObjectMapper

    static {
        objectMapper.registerModule(new JavaTimeModule());
        System.out.println("ip : "+ IP_ADDRESS);
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

    public static  <T> List<T> findAllByIdPatient(String role, Class<T> clazz,int id) throws Exception {
        String endpoint = role.toLowerCase() + "s/" + role.toLowerCase() + "/find-by-id-pation-after-delete/"+id;
        try {
            String jsonResponse = sendRequest(endpoint, "GET", null);
            return objectMapper.readValue(jsonResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            if (e.getMessage().contains("HTTP error code : 404")) {
                // Handle the 404 error specifically
                System.out.println(endpoint);
                return null; // or handle as needed
            }
            throw e; // rethrow other exceptions
        }
    }


    public static  <T> List<T> getDrugData(String role, Class<T> clazz,int id) throws Exception {
        String endpoint = role.toLowerCase() + "s/" + role.toLowerCase() + "/"+id;
        try {
            String jsonResponse = sendRequest(endpoint, "GET", null);
            return objectMapper.readValue(jsonResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, clazz));
        } catch (Exception e) {
            if (e.getMessage().contains("HTTP error code : 404")) {
                // Handle the 404 error specifically
                System.out.println(endpoint);
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

    public static <T> T findAfterDelete(String role, Integer id, Class<T> clazz) throws Exception {
        String endpoint = role.toLowerCase() + "s/" + role.toLowerCase() + "/after-delete/find-by-id/" + id;
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


    public static <T> T login(String role,String email, String password,  Class<T> clazz) throws Exception {
        // Build the URL endpoint to find the user by email
        String urlString = URL_API + role.toLowerCase() + "s/" + role.toLowerCase() + "/find-by-email/" + email;
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        System.out.println("role ;"+role);
        System.out.println("email ;"+email);
        System.out.println("password ;"+password);


        // Add Basic Authentication header
        String auth = email + ":" + password;
        String encodedAuth = Base64.getEncoder().encodeToString(auth.getBytes());
        connection.setRequestProperty("Authorization", "Basic " + encodedAuth);

        // Get the response code and handle the connection
        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Map the response JSON to the provided class (T)
            T user = objectMapper.readValue(response.toString(), clazz);

            // Use BCrypt to verify the password with the stored hash
            PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
                return user;
        } else if (responseCode == HttpURLConnection.HTTP_NOT_FOUND) {
            throw new RuntimeException("User not found with email: " + email);
        } else {
            throw new RuntimeException("Failed : HTTP error code : " + responseCode);
        }
    }




    public static String  findByPassword( String role,String email, String password) throws Exception {
        String urlString = URL_API+role.toLowerCase()+"s"+"/"+role.toLowerCase()+"/find-password-by-email/" + email;
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


    public static List<DrugResDTO> findAllJson(String drug, Class<DrugResDTO> drugResDTOClass) {
        ObjectMapper objectMapper = new ObjectMapper();
        String endpoint = drug.toLowerCase() + "s/find-all"; // Adjusted endpoint

        try {
            String jsonResponse = sendRequest(endpoint, "GET", null);
            return objectMapper.readValue(jsonResponse, objectMapper.getTypeFactory().constructCollectionType(List.class, drugResDTOClass));
        } catch (JsonMappingException e) {
            throw new RuntimeException("Error mapping JSON to DrugResDTO", e);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error processing JSON", e);
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error occurred", e);
        }
    }

}
