//package com.medical.medical.reseau;
//
//import com.zaxxer.hikari.HikariConfig;
//import com.zaxxer.hikari.HikariDataSource;
//import org.springframework.boot.jdbc.DataSourceBuilder;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import javax.sql.DataSource;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Configuration
//public class DataSourceConfig {
//
//    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);
//
//    private final UpdateDatabaseConfig updateDatabaseConfig;
//    private final MySQLScanner mySQLScanner;
//
//    // Constructor injection
//    public DataSourceConfig(UpdateDatabaseConfig updateDatabaseConfig, MySQLScanner mySQLScanner) {
//        this.updateDatabaseConfig = updateDatabaseConfig;
//        this.mySQLScanner = mySQLScanner;
//    }
//
//    @Bean
//    public DataSource dataSource() {
//        // Discover the IP address of the MySQL server
//        String ipAddress = mySQLScanner.updateDatabaseConfigIfAvailable();
//        if (ipAddress != null) {
//            String url = "jdbc:mysql://" + ipAddress + ":3306/medical_db";
//
//            DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
//            System.out.println("ccccc :"+url);
//            dataSourceBuilder.url(url);
//            dataSourceBuilder.username("amin");
//            dataSourceBuilder.password("amin");
//            return dataSourceBuilder.build();
//
//        } else {
//            logger.error("Failed to discover MySQL server IP address.");
//            throw new RuntimeException("Failed to discover MySQL server IP address.");
//        }
//
//    }
//
//}
