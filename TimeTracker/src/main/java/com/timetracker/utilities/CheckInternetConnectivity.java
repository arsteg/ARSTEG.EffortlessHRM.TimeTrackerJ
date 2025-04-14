package com.timetracker.utilities;

import java.net.InetAddress;

public class CheckInternetConnectivity {
    public static boolean isConnectedToInternet() {
        try {
            InetAddress address = InetAddress.getByName("www.google.com");
            return address.isReachable(5000); // 5-second timeout
        } catch (Exception e) {
            return false;
        }
    }
}