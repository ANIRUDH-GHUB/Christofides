package com.sahiti.usf.model;

public class Node {
    String crimeId;
    double longitude;
    double latitude;

    public Node(String crimeId, double longitude, double latitude) {
        this.crimeId = crimeId;
        this.longitude = longitude;
        this.latitude = latitude;
    }

    public String getCrimeId() {
        return crimeId;
    }

    public void setCrimeId(String crimeId) {
        this.crimeId = crimeId;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

}
