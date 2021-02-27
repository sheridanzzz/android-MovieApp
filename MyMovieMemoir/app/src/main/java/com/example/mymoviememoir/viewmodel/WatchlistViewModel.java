package com.example.mymoviememoir.viewmodel;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mymoviememoir.entity.Watchlist;
import com.example.mymoviememoir.repository.WatchlistRepository;

import java.util.Date;
import java.util.List;

public class WatchlistViewModel  extends ViewModel {
    private WatchlistRepository wRepository;
    private MutableLiveData<List<Watchlist>> allWatchlists;
    public WatchlistViewModel () {
        allWatchlists=new MutableLiveData<>();
    }
    public void setWatchlists(List<Watchlist> watchlists) {
        allWatchlists.setValue(watchlists);
    }
    public LiveData<List<Watchlist>> getAlls() {
        return wRepository.getAllWatchlist();
    }
    public void initalizeVars(Application application){
        wRepository = new WatchlistRepository(application);
    }
    public void insert(Watchlist watchlist) {
        wRepository.insert(watchlist);
    }
    public void insertAll(Watchlist... watchlists) {
        wRepository.insertAll(watchlists);
    }
    public void deleteAll() {
        wRepository.deleteAll();
    }
    public void update(Watchlist... watchlists) {
        wRepository.updateWatchlists(watchlists);
    }
    public void updateByID(int watchlistId, String movieName, String releaseDate,
                           String dateadded) {
        wRepository.updateWatchlistByID(watchlistId,movieName, releaseDate,
                dateadded);
    }
    public Watchlist findByID(int watchlistId){
        return wRepository.findByID(watchlistId);
    }
}
