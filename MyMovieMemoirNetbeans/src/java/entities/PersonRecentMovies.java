/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.util.Date;

/**
 *
 * @author sheri
 */
public class PersonRecentMovies {

String movieName;
Date releaseDate;
Double rating;

public String getMovieName() {
return movieName;
}
public void setMovieName(String movieName) {
this.movieName = movieName;
}

public Date getReleaseDate() {
return releaseDate;
}
public void setReleaseDate(Date releaseDate) {
this.releaseDate = releaseDate;
}

public Double getRating()
{
 return rating;
}

public void setRating(Double rating)
{
    this.rating = rating;
}

public PersonRecentMovies() {
}

public PersonRecentMovies(String movieName, Date releaseDate, Double rating) {
this.movieName = movieName;
this.releaseDate = releaseDate;
this.rating = rating;
}
    
}
