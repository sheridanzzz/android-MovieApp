package com.example.mymoviememoir.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.mymoviememoir.DateConverter;

import java.util.Date;

@Entity
public class Watchlist {

    @PrimaryKey(autoGenerate = true)
    public int uid;
    @ColumnInfo(name = "movie_name")
    public String movieName;
    @ColumnInfo(name = "release_date")
    public String releaseDate;
    @ColumnInfo(name = "date_added")
    public String dateAdded;

    @Ignore
    public Watchlist()
    {
    }
    public Watchlist(String movieName, String releaseDate, String dateAdded) {
        this.movieName=movieName;
        this.releaseDate=releaseDate;
        this.dateAdded = dateAdded;
    }
    public int getId() {
        return uid;
    }
    public String getMovieName() {
        return movieName;
    }
    public void setMovieName(String movieName) {
        this.movieName = movieName;
    }
    public String getReleaseDate() {
        return releaseDate;
    }
    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }
    public String getDateAdded() {
        return dateAdded;
    }
    public void setDateAdded (String dateAdded) {
        this.dateAdded = dateAdded;
    }
}

