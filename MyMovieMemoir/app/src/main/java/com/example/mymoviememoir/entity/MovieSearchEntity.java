package com.example.mymoviememoir.entity;

import com.example.mymoviememoir.HomeActivity;

public class MovieSearchEntity {
    private  String movieName;
    private  String releaseYear;
    private  String posterPath;
    private  int movieID;

    public MovieSearchEntity(){

    }

    public MovieSearchEntity (String movieName, String releaseYear, String posterPath, int movieID) {
        this.movieName =movieName;
        this.releaseYear =releaseYear;
        this.posterPath =posterPath;
        this.movieID=movieID;
    }

    public String getMovieName() {
        return movieName;
    }
    public void setMovieName (String movieName) {
        this.movieName =movieName;
    }

    public  String getReleaseYear() {
        return releaseYear;
    }
    public void setReleaseYear (String releaseYear) {
        this.releaseYear =releaseYear;
    }

    public  String getPosterPath() {
        return posterPath;
    }
    public void setPosterPath (String posterPath) {
        this.posterPath =posterPath;
    }

    public  int getMovieID() {
        return movieID;
    }
    public void setMovieID (int movieID) {
        this.movieID =movieID;
    }

}
