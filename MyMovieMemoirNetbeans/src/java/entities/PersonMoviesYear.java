/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;
import javax.xml.bind.annotation.XmlRootElement;
@XmlRootElement
/**
 *
 * @author sheri
 */
public class PersonMoviesYear {

String movieName;
int releaseDate;

public String getMovieName() {
return movieName;
}
public void setMovieName(String movieName) {
this.movieName = movieName;
}

public int getReleaseDate() {
return releaseDate;
}
public void setReleaseDate(int releaseDate) {
this.releaseDate = releaseDate;
}

public PersonMoviesYear() {
}

public PersonMoviesYear(String movieName, int releaseDate) {
this.movieName = movieName;
this.releaseDate = releaseDate;
}
}
