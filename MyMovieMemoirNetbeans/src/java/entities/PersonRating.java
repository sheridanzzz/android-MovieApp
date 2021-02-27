/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import java.util.Date;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
/**
 *
 * @author sheri
 */
public class PersonRating {

String movieName;
Double rating;
Date releaseDate;

public String getMovieName() {
return movieName;
}
public void setMovieName(String movieName) {
this.movieName = movieName;
}

public Double getRating() {
return rating;
}
public void setRating(Double rating) {
this.rating = rating;
}

public Date getReleaseDate() {
return releaseDate;
}
public void setReleaseDate(Date releaseDate) {
this.releaseDate = releaseDate;
}

public PersonRating() {
}

public PersonRating(String movieName, Double rating, Date releaseDate) {
this.movieName = movieName;
this.rating = rating;
this.releaseDate = releaseDate;
}
}
