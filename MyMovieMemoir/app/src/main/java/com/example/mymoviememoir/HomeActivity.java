package com.example.mymoviememoir;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.mymoviememoir.entity.Cinema;
import com.example.mymoviememoir.entity.CinemaMap;
import com.example.mymoviememoir.entity.Credential;
import com.example.mymoviememoir.entity.Person;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    Credential credential = new Credential();
    Person person = new Person();
    NetworkConnection networkConnection = null;
    private static ArrayList<Cinema> cinemas = new ArrayList<Cinema>();
    private GeocoderController geocoderController = null;


    @SuppressLint("StaticFieldLeak")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        networkConnection = new NetworkConnection();
        geocoderController = new GeocoderController();

        GetLatAndLongTask getLatAndLongTask = new GetLatAndLongTask();

        GetCinemaPostcodesTask getCinemaPostcodesTask = new GetCinemaPostcodesTask();
        getCinemaPostcodesTask.execute();

        String address = Person.getAddress();
        int postcodeInt = Person.getPostcode();
        String postcode = String.valueOf(postcodeInt);

       getLatAndLongTask.execute(postcode, address);


        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);



        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_movieSearch:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MovieSearchFragment()).commit();
                break;
            case R.id.nav_movieMemoir:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MovieMemoirFragment()).commit();
                break;
            case R.id.nav_watchlist:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new WatchlistFragment()).commit();
                break;
            case R.id.nav_reports:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new ReportsFragment()).commit();
                break;
            case R.id.nav_maps:
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new MapsFragment()).commit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void replaceFragment(Fragment nextFragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, nextFragment);
        fragmentTransaction.commit();
    }


    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private class GetCinemaPostcodesTask extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            return networkConnection.getCinemaPostcodes();
        }

        @Override
        protected void onPostExecute(String result) {
            JsonElement je = new JsonParser().parse(result);
            JsonArray myArray = je.getAsJsonArray();

            for (JsonElement e : myArray) {
                // Access the element as a JsonObject
                JsonObject jo = e.getAsJsonObject();

                String idString = jo.get("cinemaid").toString();
                String name = jo.get("cinemaname").toString();
                String postcodeString = jo.get("postcode").toString();

                int id = Integer.parseInt(idString);
                int postcode = Integer.parseInt(postcodeString);

                Log.i("id ", idString);
                Log.i("name ", name);
                Log.i("date ", postcodeString);
                cinemas.add(new Cinema(id, name, postcode));
            }
            GetCinemaLatAndLongTask getCinemaLatAndLongTask = new GetCinemaLatAndLongTask();
            getCinemaLatAndLongTask.execute();
            }
        }

    public static ArrayList<Cinema> getCinemas() {
        return cinemas;
    }


        private class GetCinemaLatAndLongTask extends AsyncTask<Void, Void, String[]> {
            @Override
            protected String[] doInBackground(Void... params) {
                return geocoderController.getCinemaLatAndLong();
            }

            @Override
            protected void onPostExecute(String[] result) {
            }
        }


        @SuppressLint("StaticFieldLeak")
        private class GetLatAndLongTask extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... params) {
                String postcode = params[0].toString();
                String address = params[1].toString();
                return geocoderController.getLatAndLong(postcode, address);
            }

            @Override
            protected void onPostExecute(String result) {
            }
        }
    }
