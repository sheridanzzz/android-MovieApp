/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author sheri
 */
public class PersonMovies2 {
   
        
String month;
Long totalMoviesWatched;

public String getMonth() {
return month;
}
public void setMonth(String month) {
this.month = month;
}

public Long getTotalMoviesWatched() {
return totalMoviesWatched;
}
public void setTotalMoviesWatched(Long totalMoviesWatched) {
this.totalMoviesWatched = totalMoviesWatched;
}


public PersonMovies2() {
}

public PersonMovies2(String month, Long totalMoviesWatched) {
this.month = month;
this.totalMoviesWatched = totalMoviesWatched;
}
}
