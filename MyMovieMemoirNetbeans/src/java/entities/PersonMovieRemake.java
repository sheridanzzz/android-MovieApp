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
public class PersonMovieRemake {

String movieName;
int releaseYear;

public String getMovieName() {
return movieName;
}
public void setMovieName(String movieName) {
this.movieName = movieName;
}

public int getReleaseYear() {
return releaseYear;
}
public void setReleaseYear(int releaseYear) {
this.releaseYear = releaseYear;
}

public PersonMovieRemake() {
}

public PersonMovieRemake(String movieName, int releaseYear) {
this.movieName = movieName;
this.releaseYear = releaseYear;
}
}
