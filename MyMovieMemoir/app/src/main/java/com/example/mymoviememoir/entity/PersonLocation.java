package com.example.mymoviememoir.entity;

public class PersonLocation {
    private static String latitude;
    private static String longitude;

    public PersonLocation (){

    }

    public PersonLocation (String latitude, String longitude) {
        PersonLocation.latitude =latitude;
        PersonLocation.longitude =longitude;
    }

    public static String getLatitude() {
        return latitude;
    }
    public void setLatitude (String latitude) {
        PersonLocation.latitude = latitude;
    }
    public static String getLongitude() {
        return longitude;
    }
    public void setLongitude(String longitude) {
        PersonLocation.longitude = longitude;
    }
}
