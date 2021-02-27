package com.example.mymoviememoir;

import android.util.Log;

import com.example.mymoviememoir.entity.MovieSearchEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MovieDbAPI {
    private OkHttpClient client = null;
    private String results;

    public MovieDbAPI() {
        client = new OkHttpClient();
    }

    private static final String BASE_URL =
            "https://api.themoviedb.org/3/search/movie?api_key=41e9662bed64d7dccb1e328a0d19e123&language=en-US&query=";

    public String searchMovie(String movie) {
        final String methodPath = movie;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
            Log.i("json ", results);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return results;
    }

    public String getMovieDetails(String movieId) {

        String url = "https://api.themoviedb.org/3/movie/" + movieId + "?api_key=41e9662bed64d7dccb1e328a0d19e123";
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
            Log.i("MovieDetails ", results);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return results;
    }

    public String getCastDetails(String movieId) {
        String url = "https://api.themoviedb.org/3/movie/" + movieId + "/credits?api_key=41e9662bed64d7dccb1e328a0d19e123";
        Request.Builder builder = new Request.Builder();
        builder.url(url);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
            Log.i("movieCast ", results);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return results;
    }
}
