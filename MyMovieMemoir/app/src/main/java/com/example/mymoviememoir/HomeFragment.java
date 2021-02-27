package com.example.mymoviememoir;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.entity.Credential;
import com.example.mymoviememoir.entity.MovieSearchEntity;
import com.example.mymoviememoir.entity.Person;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class HomeFragment extends Fragment {
    Credential credential = new Credential();
    NetworkConnection networkConnection = null;
    ListView listViewHome;
    HomeAdapter adapter;
    ImageView imageView;
    private ArrayList<MovieHome> movieArray;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        networkConnection = new NetworkConnection();

        TextView firstNameTxt = view.findViewById(R.id.firstNameTxt_home);
        movieArray = new ArrayList<MovieHome>();
        imageView = view.findViewById(R.id.imageViewHome);

        TextView homeDateView = view.findViewById(R.id.current_dateTxt);
        //ratingList_home = v.findViewById(R.id.ratingList_home);

        String name = Person.getFirstname();
        name = name.replace("\"", "");
        firstNameTxt.setText(name);

        Date c = Calendar.getInstance().getTime();

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String formattedDate = df.format(c);

        homeDateView.setText(formattedDate);
        int personID = Person.getPersonid();
        String personIDString = String.valueOf(personID);
        Log.i("personid", personIDString);
        GetHomeImagePath getHomeImagePath = new GetHomeImagePath();
        getHomeImagePath.execute("cinema seats");
        GetTopMoviesTask getTopMoviesTask = new GetTopMoviesTask();
        getTopMoviesTask.execute(personIDString);

        // Attach the adapter to a ListView
        listViewHome = view.findViewById(R.id.lvHome);
//        listViewHome.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(getActivity(), "Loading.....", Toast.LENGTH_LONG).show();
//            }
//        });

        return view;
    }

    private class GetHomeImagePath extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String word = params[0];
            return SearchGoogleAPI.search(word);
        }

        @Override
        protected void onPostExecute(String result) {
            String path = SearchGoogleAPI.getSnippet(result);
            Picasso.with(getContext()).load(path).into(imageView);
        }
    }

    public class HomeAdapter extends ArrayAdapter<MovieHome> {
        public HomeAdapter(Context context, ArrayList<MovieHome> MovieHomeList) {
            super(context, 0, MovieHomeList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            MovieHome movieHome = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.homelist_view, parent, false);
            }
            // Lookup view for data population
            TextView homeMovieName = convertView.findViewById(R.id.movieNameHomeTxt);
            RatingBar homeStarBar2 = convertView.findViewById(R.id.starBarHome);
            TextView homeReleaseDate = convertView.findViewById(R.id.releaseDateHomeTXT);
            // Populate the data into the template view using the data object
            homeMovieName.setText(movieHome.movieName);
            double d = Double.parseDouble(movieHome.rating);
            homeStarBar2.setRating((float) d);
            String date = movieHome.releaseDate;
            String dateString = date.substring(0, 9);
            homeReleaseDate.setText(dateString);
            // Return the completed view to render on screen
            return convertView;
        }
    }

    private class GetTopMoviesTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String user = params[0];
            Log.i("user id 1 ", user);
            return networkConnection.getTopMovies(user);
        }

        @Override
        protected void onPostExecute(String result) {
            try {

                ArrayList<MovieHome> movieArray2 = new ArrayList<MovieHome>();
                Gson gson = new Gson();

                Type movieListType = new TypeToken<ArrayList<MovieHome>>() {
                }.getType();

                movieArray2 = gson.fromJson(result, movieListType);

                for(int i=0;i <=5 ; i++)
                {
                    movieArray.add(movieArray2.get(i));
                }
                adapter = new HomeAdapter(getActivity(), movieArray);
                listViewHome.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public class MovieHome {
        private String movieName;
        private String rating;
        private String releaseDate;

        public MovieHome() {

        }

        public MovieHome(String movieName, String rating, String releaseDate) {
            this.movieName = movieName;
            this.rating = rating;
            this.releaseDate = releaseDate;
        }

        public String getMovieName() {
            return movieName;
        }

        public void setMovieName(String movieName) {
            this.movieName = movieName;
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

        @Override
        public String toString() {
            return "Movie [rating=" + rating + ", name=" + movieName + ", date=" + releaseDate + "]";
        }

    }
}
