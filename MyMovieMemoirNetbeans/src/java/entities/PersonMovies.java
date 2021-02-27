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
public class PersonMovies {
    
int month;
Long totalMoviesWatched;

public int getMonth() {
return month;
}
public void setMonth(int month) {
this.month = month;
}

public Long getTotalMoviesWatched() {
return totalMoviesWatched;
}
public void setTotalMoviesWatched(Long totalMoviesWatched) {
this.totalMoviesWatched = totalMoviesWatched;
}


public PersonMovies() {
}

public PersonMovies(int month, Long totalMoviesWatched) {
this.month = month;
this.totalMoviesWatched = totalMoviesWatched;
}
}
