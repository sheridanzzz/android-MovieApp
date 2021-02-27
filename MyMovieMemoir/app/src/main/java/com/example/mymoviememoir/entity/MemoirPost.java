package com.example.mymoviememoir.entity;

import java.util.Date;

public class MemoirPost {
    private String memoirid;
    private String moviename;
    private String moviereleasedate;
    private String comment;
    private String rating;
    private String watcheddatetime;
    private Cinema cinemaid;
    private PersonReg personid;

    public MemoirPost () {

    }
    public MemoirPost (String memoirid, String moviename, String moviereleasedate, String comment, String rating, String watcheddatetime){
        this.memoirid=memoirid;
        this.moviename=moviename;
        this.moviereleasedate=moviereleasedate;
        this.comment=comment;
        this.rating=rating;
        this.watcheddatetime=watcheddatetime;
    }

    public void setPersonid(PersonReg personReg){
        personid =new PersonReg(personReg);
    }
    public PersonReg getPersonid(){
        return personid;
    }

    public void setCinemaid(Cinema cinema){
        cinemaid =new Cinema(cinema);
    }
    public Cinema getCinemaid(){
        return cinemaid;
    }

    public String getMemoirid() {
        return memoirid;
    }
    public void setMemoirid(String memoirid) {
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
    public String getMoviereleasedate() {
        return moviereleasedate;
    }
    public void setMoviereleasedate(String moviereleasedate) {
        this.moviereleasedate = moviereleasedate;
    }
    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        this.rating = rating;
    }
    public String getWatcheddatetime() {
        return watcheddatetime;
    }
    public void setWatcheddatetime(String watcheddatetime) {
        this.watcheddatetime = watcheddatetime;
    }
}
