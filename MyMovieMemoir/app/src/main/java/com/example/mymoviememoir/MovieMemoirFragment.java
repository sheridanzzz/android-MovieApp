package com.example.mymoviememoir;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.entity.Memoir;
import com.example.mymoviememoir.entity.MovieSearchEntity;
import com.example.mymoviememoir.entity.Person;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.github.mikephil.charting.renderer.CandleStickChartRenderer;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class MovieMemoirFragment extends Fragment {
    String path;
    MoviesMemoir moviesMemoir;
    ArrayList<MoviesMemoir> memoirsList;
    NetworkConnection networkConnection = null;
    ArrayList<MoviesMemoir> arrayOfMemoirs;
    private MovieDbAPI movieDbAPI = null;
    ArrayList<String> pathList;
    ListView listView;
    MemoirAdapter adapter;
    private ArrayList<MovieSearchEntity> movieSearchList = new ArrayList<MovieSearchEntity>();

    @SuppressLint("StaticFieldLeak")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_moviememoir, container, false);
        networkConnection = new NetworkConnection();
        memoirsList = new ArrayList<MoviesMemoir>();
        pathList = new ArrayList<>();
        movieDbAPI = new MovieDbAPI();
        moviesMemoir = new MoviesMemoir();

        GetMemoirsTask getMemoirsTask = new GetMemoirsTask();
        getMemoirsTask.execute(String.valueOf(Person.getPersonid()));

        // Construct the data source
        arrayOfMemoirs = new ArrayList<MoviesMemoir>();
        // Create the adapter to convert the array to views
        adapter = new MemoirAdapter(getActivity(), arrayOfMemoirs);
        // Attach the adapter to a ListView
        listView = view.findViewById(R.id.lvMovies);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GetMovieIdTask getMovieIdTask = new GetMovieIdTask();
                getMovieIdTask.execute(arrayOfMemoirs.get(position).getMovieName());
                Toast.makeText(getActivity(), "Loading.....", Toast.LENGTH_LONG).show();
            }
        });

        Button buttonRatingSort = view.findViewById(R.id.btn_sortRatingML);
        buttonRatingSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortRating();
            }
        });

        Button buttonPublicRatingSort = view.findViewById(R.id.btn_sortPublicML);
        buttonPublicRatingSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortPublicRating();
            }
        });

        Button buttonDateSort = view.findViewById(R.id.btn_sortDateML);
        buttonDateSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortDate();
            }
        });

        return view;
    }

    private void sortDate()
    {
        Collections.sort(arrayOfMemoirs, new Comparator<MoviesMemoir>() {
            @Override
            public int compare(MoviesMemoir o1, MoviesMemoir o2) {
                return o1.getWatchedDate().compareTo(o2.getWatchedDate());
            }
        });

        adapter.notifyDataSetChanged();
    }


    private void sortRating()
    {
        Collections.sort(arrayOfMemoirs, new Comparator<MoviesMemoir>() {
            @Override
            public int compare(MoviesMemoir o1, MoviesMemoir o2) {
                return o2.getRating().compareTo(o1.getRating());
            }
        });

        adapter.notifyDataSetChanged();
    }

    private void sortPublicRating()
    {
        //get public stars in the list and then sort
        Collections.sort(arrayOfMemoirs, new Comparator<MoviesMemoir>() {
            @Override
            public int compare(MoviesMemoir o1, MoviesMemoir o2) {
                return o1.getRating().compareTo(o2.getRating());
            }
        });

        adapter.notifyDataSetChanged();
    }

    private class GetMovieIdTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String user = params[0];
            return movieDbAPI.searchMovie(user);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject object = new JSONObject(result);
                JSONArray jArray = object.getJSONArray("results");

                for (int i = 0; i < 1; i++) {
                    String movieIdString = jArray.getJSONObject(i).getString("id");
                    String title = jArray.getJSONObject(i).getString("title");
                    String year = jArray.getJSONObject(i).getString("release_date");
                    String posterPath = jArray.getJSONObject(i).getString("poster_path");

                    String yearString = year.split("-")[0];

                    posterPath = "http://image.tmdb.org/t/p/w185/" + posterPath;

                    Log.i("id ", movieIdString);
                    Log.i("name ", title);
                    Log.i("date ", year);
                    Log.i("path ", posterPath);

                    movieSearchList.add(new MovieSearchEntity(title, yearString, posterPath, Integer.parseInt(movieIdString)));
                }

                int movieId = movieSearchList.get(0).getMovieID();
                String movieIDString = String.valueOf(movieId);
                String posterPath = movieSearchList.get(0).getPosterPath();
                String movieName = movieSearchList.get(0).getMovieName();
                Intent intent = new Intent(getActivity(), MovieViewActivity.class);
                intent.putExtra("memid", movieIDString);
                intent.putExtra("memname", movieName);
                intent.putExtra("mempath", posterPath);
                intent.putExtra("fromMemoir", true);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private class GetMemoirsTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String user = params[0];
            return networkConnection.getMemoirs(user);
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JsonElement je = new JsonParser().parse(result);
                JsonArray myArray = je.getAsJsonArray();

                for (JsonElement e : myArray) {
                    // Access the element as a JsonObject
                    JsonObject jo = e.getAsJsonObject();

                    String movieName = jo.get("moviename").toString();
                    String rating = jo.get("rating").toString();
                    String releaseDate = jo.get("moviereleasedate").toString();
                    //path = jo.getAsJsonObject("personid").get("dob").toString();
                    String watchedDate = jo.get("watcheddatetime").toString();
                    String cinemaPostcode = jo.getAsJsonObject("cinemaid").get("postcode").toString();
                    String comment = jo.get("comment").toString();

                    movieName = movieName.replace("\"", "");
                    rating = rating.replace("\"", "");
                    releaseDate = releaseDate.replace("\"", "");
                    watchedDate = watchedDate.replace("\"", "");
                    cinemaPostcode = cinemaPostcode.replace("\"", "");
                    comment = comment.replace("\"", "");

                    GetImagePath imagePath = new GetImagePath();
                    imagePath.execute(movieName);
                    moviesMemoir = new MoviesMemoir();
                    moviesMemoir.setMovieName(movieName);
                    moviesMemoir.setRating(rating);
                    moviesMemoir.setReleaseDate(releaseDate);
                    moviesMemoir.setWatchedDate(watchedDate);
                    moviesMemoir.setCinemaPostcode(cinemaPostcode);
                    moviesMemoir.setComment(comment);
                    moviesMemoir.setImgPath(path);
                    arrayOfMemoirs.add(moviesMemoir);
                }
                listView.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class GetImagePath extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String word = params[0];
            return SearchGoogleAPI.search(word);
        }

        @Override
        protected void onPostExecute(String result) {
            path = SearchGoogleAPI.getSnippet(result);
            pathList.add(path);
        }
    }


    public class MemoirAdapter extends ArrayAdapter<MoviesMemoir> {
        public MemoirAdapter(Context context, ArrayList<MoviesMemoir> MoviesMemoirs) {
            super(context, 0, MoviesMemoirs);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            MoviesMemoir moviesMemoir = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.memoirlist_view, parent, false);
            }
            // Lookup view for data population
            TextView tvMovieName = convertView.findViewById(R.id.MovieNameML);
            RatingBar starBar2 = convertView.findViewById(R.id.starBarML);
            TextView tvReleaseDate = convertView.findViewById(R.id.releaseDateML);
            TextView tvWatchedDate = convertView.findViewById(R.id.watchedDateML);
            TextView tvCinemaPostcode = convertView.findViewById(R.id.cinemaPostcodeML);
            TextView tvComment = convertView.findViewById(R.id.commentML);
            ImageView imgMovie = convertView.findViewById(R.id.imageViewML);
            // Populate the data into the template view using the data object
            //assert moviesMemoir != null;
            tvMovieName.setText(moviesMemoir.movieName);
            double d=Double.parseDouble(moviesMemoir.rating);
            starBar2.setRating((float) d);
            String rDate = moviesMemoir.releaseDate;
            String rDateString = rDate.substring(0, 9);
//            String wDate = moviesMemoir.watchedDate;
//            String wDateString = rDate.substring(0, 15);
            tvReleaseDate.setText(rDateString);
            tvWatchedDate.setText(moviesMemoir.watchedDate);
            tvCinemaPostcode.setText(moviesMemoir.cinemaPostcode);
            tvComment.setText(moviesMemoir.comment);
            //Picasso.with(getContext()).load(pathList.get(position)).into(imgMovie);
            // Return the completed view to render on screen
            return convertView;
        }
    }

    public class MoviesMemoir {
        private String movieName;
        private String rating;
        private String releaseDate;
        private String watchedDate;
        private String cinemaPostcode;
        private String comment;
        private String imgPath;

        public MoviesMemoir() {

        }

        public MoviesMemoir(String movieName, String rating, String releaseDate, String watchedDate, String cinemaPostcode, String comment, String imgPath) {
            this.movieName = movieName;
            this.rating = rating;
            this.releaseDate = releaseDate;
            this.watchedDate = watchedDate;
            this.cinemaPostcode = cinemaPostcode;
            this.comment = comment;
            this.imgPath=imgPath;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }

        public String getCinemaPostcode() {
            return cinemaPostcode;
        }

        public void setCinemaPostcode(String cinemaPostcode) {
            this.cinemaPostcode = cinemaPostcode;
        }

        public String getWatchedDate() {
            return watchedDate;
        }

        public void setWatchedDate(String watchedDate) {
            this.watchedDate = watchedDate;
        }

        public String getRating() {
            return rating;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public String getReleaseDate() {
            return releaseDate;
        }

        public void setReleaseDate(String releaseDate) {
            this.releaseDate = releaseDate;
        }

        public String getImgPath()
        {
            return imgPath;
        }

        public void setImgPath(String imgPath)
        {
            this.imgPath=imgPath;
        }

        @Override
        public String toString() {
            //change this for list view
            return "Movie [rating=" + rating + ", name=" + movieName + ", date=" + releaseDate + ", watchedDate=" + watchedDate + ", cinemaPostcode=" + cinemaPostcode + ", comment=" + comment + ", imgPath=" + imgPath + "]";
        }

    }
}
