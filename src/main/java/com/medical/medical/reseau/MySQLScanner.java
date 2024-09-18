//package com.medical.medical.reseau;
//
//import java.net.InetSocketAddress;
//import java.net.Socket;
//import java.net.SocketAddress;
//import java.sql.Connection;
//import java.sql.DriverManager;
//import java.sql.SQLException;
//import java.util.ArrayList;
//import java.util.List;
//import java.util.concurrent.*;
//
//public class MySQLScanner {
//    private static final int START_IP = 1;
//    private static final int END_IP = 254;
//    private static final int THREAD_POOL_SIZE = 20;
//    private static final int DB_PORT = 3306; // Default MySQL port
//    private static final String DB_NAME = "medical_db"; // Move to config file
//
//    private final UpdateDatabaseConfig updateDatabaseConfig;
//    private final String dbUsername;
//    private final String dbPassword;
//
//    public MySQLScanner(UpdateDatabaseConfig updateDatabaseConfig, String dbUsername, String dbPassword) {
//        this.updateDatabaseConfig = updateDatabaseConfig;
//        this.dbUsername = dbUsername;
//        this.dbPassword = dbPassword;
//    }
//
//    public String updateDatabaseConfigIfAvailable() {
//        try {
//            String localIP = IPUtils.getLocalIP();
//            String baseIp = getBaseIP(localIP);
//            List<String> ipAddresses = generateIPList(baseIp, localIP);
//
//            String foundIp = scanNetworkForDatabase(ipAddresses);
//
//            if (foundIp != null) {
//                System.out.println("Database found at: " + foundIp);
//                updateDatabaseConfig.updateDatabaseProperties(foundIp, dbUsername, dbPassword);
//                return foundIp;
//            } else {
//                System.out.println("No MySQL server with the database " + DB_NAME + " found.");
//            }
//        } catch (Exception e) {
//            System.err.println("Error updating database IP: " + e.getMessage());
//        }
//        return null;
//    }
//
//    private String getBaseIP(String localIP) {
//        String[] parts = localIP.split("\\.");
//        if (parts.length == 4) {
//            return parts[0] + "." + parts[1] + "." + parts[2];
//        }
//        return "192.168.1"; // Default base IP, adjust based on your network
//    }
//
//    private String scanNetworkForDatabase(List<String> ipAddresses) {
//        ExecutorService executor = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
//        List<Future<String>> futures = new ArrayList<>();
//
//        for (String ip : ipAddresses) {
//            Future<String> future = executor.submit(new DatabaseCheckerTask(ip));
//            futures.add(future);
//        }
//
//        executor.shutdown();
//
//        try {
//            for (Future<String> future : futures) {
//                String result = future.get();
//                if (result != null) {
//                    return result;
//                }
//            }
//        } catch (InterruptedException | ExecutionException e) {
//            System.err.println("Error during network scan: " + e.getMessage());
//        }
//
//        return null;
//    }
//
//    private List<String> generateIPList(String baseIp, String localIP) {
//        List<String> ipAddresses = new ArrayList<>();
//        ipAddresses.add(localIP); // Add the local IP first
//
//        for (int i = START_IP; i <= END_IP; i++) {
//            String ip = baseIp + "." + i;
//            if (!ip.equals(localIP)) {
//                ipAddresses.add(ip); // Add other IP addresses
//            }
//        }
//        return ipAddresses;
//    }
//
//    private class DatabaseCheckerTask implements Callable<String> {
//        private final String ip;
//
//        public DatabaseCheckerTask(String ip) {
//            this.ip = ip;
//        }
//
//        @Override
//        public String call() {
//            System.out.println("Checking IP: " + ip);
//            if (isPortOpen(ip, DB_PORT)) {
//                String url = "jdbc:mysql://" + ip + ":" + DB_PORT + "/" + DB_NAME;
//                try (Connection connection = DriverManager.getConnection(url, dbUsername, dbPassword)) {
//                    if (connection.isValid(2)) {
//                        System.out.println("Database found at IP: " + ip);
//                        return ip;
//                    }
//                } catch (SQLException e) {
//                    System.err.println("Failed to connect to " + ip + ": " + e.getMessage());
//                }
//            }
//            return null;
//        }
//
//        private boolean isPortOpen(String ipAddress, int port) {
//            try (Socket socket = new Socket()) {
//                SocketAddress socketAddress = new InetSocketAddress(ipAddress, port);
//                socket.connect(socketAddress, 1000); // 1 second timeout
//                return true;
//            } catch (Exception e) {
//                System.err.println("Port not open on " + ipAddress + ": " + e.getMessage());
//                return false;
//            }
//        }
//    }
//}
