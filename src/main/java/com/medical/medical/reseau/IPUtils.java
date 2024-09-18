//package com.medical.medical.reseau;
//
//import java.net.InetAddress;
//import java.net.NetworkInterface;
//import java.net.SocketException;
//import java.util.Enumeration;
//
//public class IPUtils {
//
//    public static String getLocalIP() {
//        try {
//            StringBuilder ipAddresses = new StringBuilder();
//            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
//
//            while (networkInterfaces.hasMoreElements()) {
//                NetworkInterface networkInterface = networkInterfaces.nextElement();
//                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
//
//                while (inetAddresses.hasMoreElements()) {
//                    InetAddress inetAddress = inetAddresses.nextElement();
//                    if (!inetAddress.isLoopbackAddress() && !inetAddress.isLinkLocalAddress() && inetAddress.isSiteLocalAddress()) {
//                        ipAddresses.append(inetAddress.getHostAddress()).append("\n");
//                    }
//                }
//            }
//
//            if (ipAddresses.length() == 0) {
//                return "127.0.0.1"; // Retourne l'adresse IP par défaut si aucune adresse locale n'est trouvée
//            }
//
//            return ipAddresses.toString().trim();
//
//        } catch (SocketException e) {
//            e.printStackTrace();
//            return "127.0.0.1"; // Retourne l'adresse IP par défaut en cas d'erreur
//        }
//    }
//}
