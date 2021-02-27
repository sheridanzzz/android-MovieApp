package com.example.mymoviememoir;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.mymoviememoir.entity.MovieSearchEntity;
import com.example.mymoviememoir.entity.Watchlist;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.share.Sharer;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class WatchlistFragment extends Fragment {
    CallbackManager callbackManager;
    Button btnShare;
    ShareDialog shareDialog;
    ListView listViewWatchlist;
    String date;
    String movieName;
    String releaseDate;
    Watchlist watchlist;
    WatchlistAdapter adapter;
    private MovieDbAPI movieDbAPI = null;
    private ArrayList<MovieSearchEntity> movieSearchList = new ArrayList<MovieSearchEntity>();


    public static ArrayList<Watchlist> watchlistArray;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_watchlist, container, false);
        watchlistArray = new ArrayList<Watchlist>();
        movieDbAPI = new MovieDbAPI();
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        //FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        listViewWatchlist = view.findViewById(R.id.lvWatchlist);
        watchlist = new Watchlist();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        date = dtf.format(now);
        Bundle args = getArguments();
        if (args != null) {
//            TextView movieNameTxt = view.findViewById(R.id.movieNameWatchlistTxt);
//            TextView releaseDateTxt = view.findViewById(R.id.releaseDateWatchlistTXT);
//            TextView dateAddedTxt  = view.findViewById(R.id.DateAddedWatchlistTXT);
            movieName = getArguments().getString("name");
            releaseDate = getArguments().getString("date");
            watchlist.setMovieName(movieName);
            watchlist.setReleaseDate(releaseDate);
            watchlist.setDateAdded(date);
            watchlistArray.add(watchlist);
            adapter = new WatchlistAdapter(getActivity(), watchlistArray);
            listViewWatchlist.setAdapter(adapter);
            //customerViewModel.insert(customer);
        } else {
            Toast.makeText(getActivity(), "Watchlist is empty!", Toast.LENGTH_LONG).show();
        }
//        customerViewModel = new ViewModelProvider(this).get(CustomerViewModel.class);
//        customerViewModel.initalizeVars(getActivity().getApplication());
//        customerViewModel.getAllCustomers().observe(getViewLifecycleOwner(), new Observer<List<Customer>>() {
//                    @Override
//                    public void onChanged(@Nullable final List<Customer> customers) {
//                        if(customers.size() == 0)
//                        {
//                            Toast.makeText(getActivity(), "Watchlist Empty!", Toast.LENGTH_LONG).show();
//                        }else {
//                            for (int i = 0; i < customers.size(); i++) {
//                                watchlistArray.get(i).setFirstName(customers.get(i).getFirstName());
//                                watchlistArray.get(i).setLastName(customers.get(i).getLastName());
//                                watchlistArray.get(i).setSalary(1.1);
//                            }
//                            adapter = new WatchlistAdapter(getActivity(), watchlistArray);
//                            listViewWatchlist.setAdapter(adapter);
//                        }
//                    }
//                });
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                customerViewModel.deleteAll();
//                textView_delete.setText("All data was deleted");
//            }
//        });

        return view;
    }

    public class WatchlistAdapter extends ArrayAdapter<Watchlist> {
        public WatchlistAdapter(Context context, ArrayList<Watchlist> watchlist) {
            super(context, 0, watchlist);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            final Watchlist watchlist = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (convertView == null) {
                convertView = LayoutInflater.from(getContext()).inflate(R.layout.watchlist_listview, parent, false);
            }
            TextView movieNameTxt = convertView.findViewById(R.id.movieNameWatchlistTxt);
            TextView releaseDateTxt = convertView.findViewById(R.id.releaseDateWatchlistTXT);
            TextView dateAddedTxt = convertView.findViewById(R.id.DateAddedWatchlistTXT);
            movieNameTxt.setText(watchlist.getMovieName());
            releaseDateTxt.setText(watchlist.getReleaseDate());
            dateAddedTxt.setText(watchlist.getDateAdded());
            // Return the completed view to render on screen

            Button btn_movieView = convertView.findViewById(R.id.btn_view);
            Button btn_share = convertView.findViewById(R.id.btn_share);


            callbackManager = CallbackManager.Factory.create();
            shareDialog = new ShareDialog(getActivity());

            btn_share.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    shareDialog.registerCallback(callbackManager, new FacebookCallback<Sharer.Result>() {
                        @Override
                        public void onSuccess(Sharer.Result result) {
                            Toast.makeText(getActivity(), "Share done!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancel() {
                            Toast.makeText(getActivity(), "Share cancelled!", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(FacebookException error) {
                            Toast.makeText(getActivity(), error.getMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
                    ShareLinkContent linkContent = new ShareLinkContent.Builder()
                            .setQuote("I added" + " " + watchlist.getMovieName() + " " + "to my watch list")
                            .setContentUrl(Uri.parse("https://www.imdb.com/"))
                            .build();
                    if (ShareDialog.canShow(ShareLinkContent.class)) {
                        shareDialog.show(linkContent);
                    }
                }
            });

            btn_movieView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    GetMovieIdTask getMovieIdTask = new GetMovieIdTask();
                    getMovieIdTask.execute(watchlist.getMovieName());
                    Toast.makeText(getActivity(), "Loading.....", Toast.LENGTH_LONG).show();
                }
            });
            return convertView;
        }
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
                intent.putExtra("memid2", movieIDString);
                intent.putExtra("memname2", movieName);
                intent.putExtra("mempath2", posterPath);
                intent.putExtra("fromWatchlist", true);
                startActivity(intent);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}
