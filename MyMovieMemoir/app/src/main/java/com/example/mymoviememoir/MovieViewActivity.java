package com.example.mymoviememoir;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.mymoviememoir.database.WatchlistDatabase;
import com.example.mymoviememoir.entity.MovieSearchEntity;
import com.example.mymoviememoir.entity.Watchlist;
import com.example.mymoviememoir.viewmodel.WatchlistViewModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MovieViewActivity extends AppCompatActivity {
    WatchlistDatabase db = null;
    WatchlistViewModel watchlistViewModel;
    private MovieDbAPI movieDbAPI = null;
    private String releaseDate;
    private String genre;
    private String cast;
    private String country;
    private String director;
    private String plot;
    private String rating;
    private String movieName;
    private String posterPath;

    public TextView movieDateTxt;
    public TextView genreTxt;
    public TextView castTxt;
    public TextView countryTxt;
    public TextView directorTxt;
    public TextView plotTxt;
    public TextView ratingTxt;
    public RatingBar starBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_view);
        Button btnWatchlist = findViewById(R.id.btn_watchlistMV);
        Button btnMemoir = findViewById(R.id.btn_memoirMV);
        movieDbAPI = new MovieDbAPI();
        String movieId = "";
        Intent intent = getIntent();
        boolean check = intent.getBooleanExtra("fromMemoir", false);
        boolean check2 = intent.getBooleanExtra("fromWatchlist", false);
        if (check) {
            movieId = intent.getExtras().getString("memid");
            posterPath = intent.getExtras().getString("mempath");
            movieName = intent.getExtras().getString("memname");
            btnWatchlist.setVisibility(View.GONE);
            btnMemoir.setVisibility(View.GONE);
        } else if (check2) {
            movieId = intent.getExtras().getString("memid2");
            posterPath = intent.getExtras().getString("mempath2");
            movieName = intent.getExtras().getString("memname2");
            btnWatchlist.setVisibility(View.GONE);
        } else {
            movieId = intent.getExtras().getString("id");
            posterPath = intent.getExtras().getString("path");
            movieName = intent.getExtras().getString("name");
        }


        final TextView movieNameTxt = findViewById(R.id.movieNameMV);
        movieDateTxt = findViewById(R.id.movieReleaseDateMV);
        genreTxt = findViewById(R.id.genreMV);
        castTxt = findViewById(R.id.castMV);
        countryTxt = findViewById(R.id.countryMV);
        directorTxt = findViewById(R.id.directorMV);
        plotTxt = findViewById(R.id.plotMV);
        starBar = findViewById(R.id.starBarMV);
        final ImageView imageView = findViewById(R.id.imageViewMV);

        movieNameTxt.setText(movieName);

        Picasso.with(this).load(posterPath).into(imageView);

        GetMovieDetailsTask getMovieDetailsTask = new GetMovieDetailsTask();
        GetCastDetailsTask getCastDetailsTask = new GetCastDetailsTask();
        getCastDetailsTask.execute(movieId);
        getMovieDetailsTask.execute(movieId);


        btnMemoir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MovieViewActivity.this, AddToMemoir.class);
                intent.putExtra("movieName", movieName);
                intent.putExtra("releaseDate", releaseDate);
                intent.putExtra("posterPath", posterPath);
                startActivity(intent);
//                Fragment fragment = new MovieMemoirFragment();
//                FragmentManager fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
            }
        });

        btnWatchlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(MovieViewActivity.this, WatchlistFragment.class);
//                startActivity(intent);
                Fragment fragment = new WatchlistFragment();
                Bundle bundle = new Bundle();
                bundle.putString("name", movieName);
                bundle.putString("date", releaseDate);
                fragment.setArguments(bundle);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();

            }
        });


    }

    public static boolean isBetween(int x, int lower, int upper) {
        return lower <= x && x <= upper;
    }


    private class GetMovieDetailsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String id = params[0].toString();
            return movieDbAPI.getMovieDetails(id);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                JSONArray jArray = object.getJSONArray("genres");
                JSONArray jArray2 = object.getJSONArray("production_countries");


                for (int i = 0; i < 1; i++) {
                    genre = jArray.getJSONObject(i).getString("name");
                    Log.i("id", genre);
                }

                for (int i = 0; i < 1; i++) {
                    country = jArray2.getJSONObject(i).getString("name");
                    Log.i("id ", country);
                }


                String rating = object.getString("vote_average");
                releaseDate = object.getString("release_date");
                String overview = object.getString("overview");

                Log.i("id ", rating);
                Log.i("name ", releaseDate);
                Log.i("date ", overview);

                movieDateTxt.setText(releaseDate);
                genreTxt.setText(genre);
                countryTxt.setText(country);
                plotTxt.setText(overview);

                float input2 = Float.parseFloat(rating);
                input2 = input2 * 10;
                int num = (int) input2;
                float star = 0;

                if (isBetween(num, 1, 9)) {
                    star = 0f;
                } else if (isBetween(num, 10, 18)) {
                    star = 0.5f;
                } else if (isBetween(num, 19, 27)) {
                    star = 1.0f;
                } else if (isBetween(num, 28, 36)) {
                    star = 1.5f;
                } else if (isBetween(num, 37, 45)) {
                    star = 2.0f;
                } else if (isBetween(num, 46, 54)) {
                    star = 2.5f;
                } else if (isBetween(num, 55, 63)) {
                    star = 3.0f;
                } else if (isBetween(num, 64, 72)) {
                    star = 3.5f;
                } else if (isBetween(num, 73, 81)) {
                    star = 4.0f;
                } else if (isBetween(num, 82, 90)) {
                    star = 4.5f;
                } else if (isBetween(num, 91, 99)) {
                    star = 5.0f;
                }

                starBar.setRating(star);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetCastDetailsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String id = params[0].toString();
            return movieDbAPI.getCastDetails(id);
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                JSONArray jArray = object.getJSONArray("cast");
                JSONArray jArray2 = object.getJSONArray("crew");

                cast = jArray.getJSONObject(0).getString("name")
                        + "," + jArray.getJSONObject(1).getString("name")
                        + "," + jArray.getJSONObject(2).getString("name")
                        + "," + jArray.getJSONObject(3).getString("name");

                Log.i("id ", cast);
                ArrayList<String> directors = new ArrayList<String>();
                for (int i = 0; i < jArray2.length(); i++) {
                    String job = jArray2.getJSONObject(i).getString("job");
                    if (job.equals("Director"))
                        directors.add(jArray2.getJSONObject(i).getString("name"));
                }

                director = String.join(", ", directors);
                Log.i("id ", director);
                castTxt.setText(cast);
                directorTxt.setText(director);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
