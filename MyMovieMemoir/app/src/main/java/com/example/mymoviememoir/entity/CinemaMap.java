package com.example.mymoviememoir.entity;

public class CinemaMap {
    public String cinemaname;
    public  double lat;
    public  double lng;

    public CinemaMap (){
    }
    public CinemaMap (String cinemaname, double lat, double lng){
        this.cinemaname =cinemaname;
        this.lat =lat;
        this.lng =lng;
    }

    public  String getCinemaname() {
        return cinemaname;
    }
    public void setCinemaname (String cinemaname) {
        this.cinemaname = cinemaname;
    }

    public  double getLat() {
        return lat;
    }
    public void setLat(double lat) {
        this.lat = lat;
    }
    public  double getLng() {
        return lng;
    }
    public void setLng(double lng) {
        this.lng = lng;
    }
}
