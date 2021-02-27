package com.example.mymoviememoir.entity;

import java.util.Date;

public class Memoir {
    private int memoirid;
    private String moviename;
    private Date moviereleasedate;
    private String comment;
    private Double rating;
    private Date watcheddatetime;
    private Cinema cinemaid;
    private Person personid;

    public Memoir (){
        this.memoirid= 1001;
        this.moviename= "sheridan";
        this.moviereleasedate= null;
        this.comment= "";
        this.rating= 2.0;
        this.watcheddatetime= null;
    }
    public Memoir (int memoirid, String moviename, Date moviereleasedate, String comment, Double rating, Date watcheddatetime){
        this.memoirid=memoirid;
        this.moviename=moviename;
        this.moviereleasedate=moviereleasedate;
        this.comment=comment;
        this.rating=rating;
        this.watcheddatetime=watcheddatetime;
    }

    public int getMemoirid() {
        return memoirid;
    }
    public void setMemoirid(int memoirid) {
        this.memoirid = memoirid;
    }
    public String getMoviename() {
        return moviename;
    }
    public void setMoviename (String moviename) {
        this.moviename = moviename;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    public Date getMoviereleasedate() {
        return moviereleasedate;
    }
    public void setMoviereleasedate(Date moviereleasedate) {
        this.moviereleasedate = moviereleasedate;
    }
    public Double getRating() {
        return rating;
    }
    public void setRating(Double rating) {
        this.rating = rating;
    }
    public Date getWatcheddatetime() {
        return watcheddatetime;
    }
    public void setWatcheddatetime(Date watcheddatetime) {
        this.watcheddatetime = watcheddatetime;
    }

//    public void setPersonid(int id){
//        personid =new Person(id);
//    }
//    public int getPersonid(){
//        return personid.getPersonid();
//    }
//
//    public void setCinemaid(int id){
//        cinemaid =new Cinema(id);
//    }
//    public int getCinemaid(){
//        return cinemaid.getCinemaid();
//    }
}
