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
public class PersonCinema {
int postcode;
Long totalMoviesWatched;

public int getPostcode() {
return postcode;
}
public void setPostcode(int postcode) {
this.postcode = postcode;
}

public Long getTotalMoviesWatched() {
return totalMoviesWatched;
}
public void setTotalMoviesWatched(Long totalMoviesWatched) {
this.totalMoviesWatched = totalMoviesWatched;
}


public PersonCinema() {
}

public PersonCinema(int postcode, Long totalMoviesWatched) {
this.postcode = postcode;
this.totalMoviesWatched = totalMoviesWatched;
}
}
