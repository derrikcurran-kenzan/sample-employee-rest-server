package com.derrikcurran.sample.employeerestserver.admin;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class NetworkDetails {

    public static NetworkDetails build() {
        try {
            InetAddress localhost = InetAddress.getLocalHost();
            NetworkDetails details = new NetworkDetails();
            details.ip = localhost.toString();
            details.hostname = localhost.getHostName();
            return details;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String ip;
    private String hostname;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
