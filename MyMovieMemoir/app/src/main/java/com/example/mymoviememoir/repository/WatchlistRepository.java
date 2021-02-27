package com.example.mymoviememoir.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.mymoviememoir.dao.WatchlistDAO;
import com.example.mymoviememoir.database.WatchlistDatabase;
import com.example.mymoviememoir.entity.Watchlist;

import java.util.Date;
import java.util.List;

public class WatchlistRepository {
    private WatchlistDAO dao;
    private LiveData<List<Watchlist>> allWatchlist;
    private Watchlist watchlist;

    public WatchlistRepository(Application application) {
        WatchlistDatabase db = WatchlistDatabase.getInstance(application);
        dao = db.watchlistDAO();
    }

    public LiveData<List<Watchlist>> getAllWatchlist() {
        allWatchlist = dao.getAll();
        return allWatchlist;
    }

    public void insert(final Watchlist watchlist) {
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insert(watchlist);
            }
        });
    }

    public void deleteAll() {
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.deleteAll();
            }
        });
    }

    public void delete(final Watchlist watchlist) {
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.delete(watchlist);
            }
        });
    }

    public void insertAll(final Watchlist... watchlists) {
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.insertAll(watchlist);
            }
        });
    }

    public void updateWatchlists(final Watchlist... watchlists) {
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updateWatchlists(watchlists);
            }
        });
    }

    public void updateWatchlistByID(final int watchlistId, final String movieName, final String releaseDate, final String dateAdded) {
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                dao.updatebyID(watchlistId, movieName, releaseDate, dateAdded);
            }
        });
    }
    public Watchlist findByID(final int watchlistId){
        WatchlistDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                Watchlist runWatchlist= dao.findByID(watchlistId);
                setWatchlist(runWatchlist);
            }
        });
        return watchlist;
    }
    public void setWatchlist(Watchlist watchlist){
        this.watchlist=watchlist;
    }
}

