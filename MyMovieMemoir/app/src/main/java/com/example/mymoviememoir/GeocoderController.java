package com.example.mymoviememoir;

import android.util.Log;

import com.example.mymoviememoir.entity.Cinema;
import com.example.mymoviememoir.entity.CinemaMap;
import com.example.mymoviememoir.entity.PersonLocation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

public class GeocoderController {
    private OkHttpClient client = null;
    private String results;
    public static ArrayList<CinemaMap> cinemaMaps = new ArrayList<>();
    private PersonLocation personLocation = new PersonLocation();

    public GeocoderController() {
        client = new OkHttpClient();
    }

    private static final String BASE_URL =
            "https://maps.googleapis.com/maps/api/geocode/json?address=";

    public String getLatAndLong(String postcode, String address) {
        final String methodPath = postcode + address + "AU&key=AIzaSyAYWoPpD4G0HvRMjb0ZlS7ecyOMokz8GHs";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
            Log.i("json ", results);

            try {
                JSONObject object = new JSONObject(results);
                JSONArray jArray = object.getJSONArray("results");

                for (int i = 0; i < jArray.length(); i++) {
                    String Latitude = jArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat");
                    String Longitude = jArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng");
                    Log.i("lat ", Latitude);
                    Log.i("long ", Longitude);
                    personLocation.setLatitude(Latitude);
                    personLocation.setLongitude(Longitude);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return results;
    }

    public String[] getCinemaLatAndLong() {
        ArrayList<Cinema> cinemas = HomeActivity.getCinemas();
        for (int j = 0; j < cinemas.size(); j++) {
            int postcodeInt =cinemas.get(j).getPostcode();
            String postcode;
            if(postcodeInt == 3008)
            {
                postcode = "Docklands";
            }else {
                postcode = String.valueOf(postcodeInt);
            }
            String name = cinemas.get(j).getCinemaname();
            final String methodPath = postcode + ",AU&key=AIzaSyAYWoPpD4G0HvRMjb0ZlS7ecyOMokz8GHs";
            Request.Builder builder = new Request.Builder();
            builder.url(BASE_URL + methodPath);
            Request request = builder.build();
            try {

                Response response = client.newCall(request).execute();
                results = response.body().string();
                Log.i("json ", results);

                JSONObject object = new JSONObject(results);
                JSONArray jArray = object.getJSONArray("results");

                for (int i = 0; i < jArray.length(); i++) {
                    String Latitude = jArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lat");
                    String Longitude = jArray.getJSONObject(i).getJSONObject("geometry").getJSONObject("location").getString("lng");
                    Log.i("lat ", Latitude);
                    Log.i("long ", Longitude);
                    double lat = Double.parseDouble(Latitude);
                    double lng = Double.parseDouble(Longitude);

                    cinemaMaps.add(new CinemaMap(name, lat, lng));
                }

            } catch (IOException | JSONException ex) {
                ex.printStackTrace();
            }

        }
        String[] result = new String[2];
        result[0] = null;
        result[1] = results;
        return result;
    }

    public static ArrayList<CinemaMap> getCinemaMapsList() {
        return cinemaMaps;
    }
}
