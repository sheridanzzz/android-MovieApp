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
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymoviememoir.adapter.RecyclerViewAdapter;
import com.example.mymoviememoir.entity.MovieSearchEntity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static android.content.ContentValues.TAG;
import static android.content.Context.INPUT_METHOD_SERVICE;

public class MovieSearchFragment extends Fragment implements RecyclerViewAdapter.onNoteListerner {
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter adapter;
    private EditText editText;
    private TextView tv;
    private MovieDbAPI movieDbAPI = null;
    private ArrayList<MovieSearchEntity> movieSearchList = new ArrayList<MovieSearchEntity>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_moviesearch, container, false);
        editText = view.findViewById(R.id.ed_keyword);

        recyclerView = view.findViewById(R.id.recyclerView);
        movieDbAPI = new MovieDbAPI();
        Button btnSearch = view.findViewById(R.id.btn_search);

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("StaticFieldLeak")
            @Override
            public void onClick(View view) {
                adapter.clear();
                final String keyword = editText.getText().toString();

                if (keyword == null || keyword.trim().length() == 0) {
                    Toast.makeText(getActivity(), "Please enter a movie name!", Toast.LENGTH_LONG).show();
                }

                //create an anonymous AsyncTask
                new AsyncTask<String, Void, String>() {
                    @Override
                    protected String doInBackground(String... params) {
                        return movieDbAPI.searchMovie(keyword);
                    }

                    @Override
                    protected void onPostExecute(String result) {
                        if (result.length() < 3) {
                            Toast.makeText(getActivity(), "No results! try again", Toast.LENGTH_LONG).show();
                        }
                        try {
                            JSONObject object = new JSONObject(result);
                            JSONArray jArray = object.getJSONArray("results");

                            for (int i = 0; i < 5; i++) {
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
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }.execute();
            }
        });

        adapter = new RecyclerViewAdapter(movieSearchList, this);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setAdapter(adapter);

        layoutManager = new LinearLayoutManager(getContext());

        recyclerView.setLayoutManager(layoutManager);

        return view;
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: clicked");
        int movieId = movieSearchList.get(position).getMovieID();
        String movieIDString = String.valueOf(movieId);
        String posterPath = movieSearchList.get(position).getPosterPath();
        String movieName = movieSearchList.get(position).getMovieName();
        Intent intent = new Intent(getActivity(), MovieViewActivity.class);
        intent.putExtra("id", movieIDString);
        intent.putExtra("name", movieName);
        intent.putExtra("path", posterPath);
        intent.putExtra("fromMemoir", false);
        startActivity(intent);
    }

}
