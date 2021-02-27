package com.example.mymoviememoir.entity;

import java.util.Date;

public class Cinema {
    private int cinemaid;
    private String cinemaname;
    private int postcode;

    public Cinema (){
    }
    public Cinema (int cinemaid, String cinemaname, int postcode){
        this.cinemaid=cinemaid;
        this.cinemaname=cinemaname;
        this.postcode=postcode;
    }


    public Cinema(Cinema cinema) {
        this.cinemaid=cinema.getCinemaid();
        this.cinemaname=cinema.getCinemaname();
        this.postcode=cinema.getPostcode();
    }

    public int getCinemaid() {
        return cinemaid;
    }
    public void setCinemaid(int cinemaid) {
        cinemaid = cinemaid;
    }
    public String getCinemaname() {
        return cinemaname;
    }
    public void setCinemaname (String cinemaname) {
        this.cinemaname = cinemaname;
    }

    public int getPostcode() {
        return postcode;
    }
    public void setPostcode(int postcode) {
        this.postcode= postcode;
    }
}
