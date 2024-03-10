package com.bitlords.disasterapp.model;

public class Addresss {
    String uid;
    double longitude;
     double latitude;
    String address;

    public Addresss() {
    }

    public Addresss(String uid, double longitude, double latitude, String address, String city, String postelCode) {
        this.uid = uid;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.city = city;
        this.postelCode = postelCode;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostelCode() {
        return postelCode;
    }

    public void setPostelCode(String postelCode) {
        this.postelCode = postelCode;
    }

    String city;

     String postelCode;
}
