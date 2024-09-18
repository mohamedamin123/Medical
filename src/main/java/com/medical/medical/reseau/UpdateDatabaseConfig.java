//package com.medical.medical.reseau;
//
//import org.springframework.stereotype.Component;
//
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.nio.file.Files;
//import java.nio.file.Paths;
//import java.util.Properties;
//
//@Component
//public class UpdateDatabaseConfig {
//
//    private static final String PROPERTIES_FILE_PATH = "src/main/resources/application.properties";
//    private static final String DATABASE_URL_KEY = "spring.datasource.url";
//    private static final String DATABASE_USERNAME_KEY = "spring.datasource.username";
//    private static final String DATABASE_PASSWORD_KEY = "spring.datasource.password";
//
//    public void updateDatabaseProperties(String ipAddress, String username, String password) {
//        Properties properties = new Properties();
//
//        try (InputStream inputStream = Files.newInputStream(Paths.get(PROPERTIES_FILE_PATH))) {
//            // Load existing properties
//            properties.load(inputStream);
//
//            // Update the database URL, username, and password
//            properties.setProperty(DATABASE_URL_KEY, "jdbc:mysql://" + ipAddress + "/" + "medical_db");
//            properties.setProperty(DATABASE_USERNAME_KEY, username);
//            properties.setProperty(DATABASE_PASSWORD_KEY, password);
//
//            // Save the updated properties
//            try (FileOutputStream outputStream = new FileOutputStream(PROPERTIES_FILE_PATH)) {
//                properties.store(outputStream, "Updated database configuration");
//                System.out.println("Updated application.properties with the new IP address, username, and password.");
//            }
//        } catch (IOException e) {
//            System.err.println("Failed to update application.properties: " + e.getMessage());
//        }
//    }
//}
