package com.example.mymoviememoir.networkconnection;

import android.annotation.SuppressLint;
import android.util.Log;

import com.example.mymoviememoir.AddToMemoir;
import com.example.mymoviememoir.entity.Cinema;
import com.example.mymoviememoir.entity.Credential;
import com.example.mymoviememoir.entity.CredentialReg;
import com.example.mymoviememoir.entity.Memoir;
import com.example.mymoviememoir.entity.MemoirPost;
import com.example.mymoviememoir.entity.Person;
import com.example.mymoviememoir.entity.PersonReg;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NetworkConnection {
    private OkHttpClient client=null;
    private String results;
    private Credential credential;
    private PersonReg personReg;
    private Cinema cinema = new Cinema();

    public static final MediaType JSON =
            MediaType.parse("application/json; charset=utf-8");
    public NetworkConnection(){
        client=new OkHttpClient();
    }
    private static final String BASE_URL =
            "http://192.168.0.9:8080/MyMovieMemoir/webresources/";

    public String checkUsernameAndPasswordHash(String username, String passwordHash){
        final String methodPath = "restws.credential/findByEmailANDPasswordhash/" + username + "/" + passwordHash;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();

            JSONObject Jobject = new JSONObject(results);
            String loudScreaming = Jobject.getString("credentialid");

            credential.setCredentialid(Integer.parseInt(loudScreaming));

        }catch (Exception e){
            e.printStackTrace();
    }
        return results;
    }

    public String checkUsernameDuplicate(String username){
        final String methodPath = "restws.credential/findByUsername/" + username ;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();


        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String addPerson(String[] details) {
        String date = details[4] + "T00:00:00+10:00";
        personReg = new PersonReg(details[0], details[1],details[2],details[3], date, details[5], details[6],details[7]);
        Gson gson = new Gson();
        String personJson = gson.toJson(personReg);
        String strResponse="";
        //this is for testing, you can check how the json looks like in Logcat
        Log.i("json " , personJson);
        final String methodPath = "restws.person";
        RequestBody body = RequestBody.create(personJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    public String addCredential(String[] details) {
        String date = details[3] + "T00:00:00+10:00";
        CredentialReg credentialReg= new CredentialReg(details[0], details[1],details[2], date);
        credentialReg.setPersonid(personReg);
        Gson gson = new Gson();
        String credentialJson = gson.toJson(credentialReg);
        String strResponse="";
        //this is for testing, you can check how the json looks like in Logcat
        Log.i("json " , credentialJson);
        final String methodPath = "restws.credential/";
        RequestBody body = RequestBody.create(credentialJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    //used to get top 5 movies watched in 2020
    public String getTopMovies(String personIDString){
        //int personid = Integer.parseInt(personIDString);
        Log.i("personIDString " , personIDString);
        final String methodPath = "restws.memoir/findMoviesWatched2020/" + personIDString ;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();


        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String getCinemaPostcodes(){
        final String methodPath = "restws.cinema/";
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();


        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String getMoviesWatchedperPostcode(String personIDString, String startDate, String endDate){
        //int personid = Integer.parseInt(personIDString);
        Log.i("personIDString " , personIDString);
        final String methodPath = "restws.memoir/findTotalMoviesWatched/" + personIDString + "/" + startDate + "/" + endDate;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();


        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String getTotalMoviesWatchedPerMonth(String personIDString, String year){

        final String methodPath = "restws.memoir/findTotalMoviesWatchedPerMonth/" + personIDString + "/" + year;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }

    public String addCinema(String[] details) {
        int cinemaID = Integer.parseInt(details[0]);
        int cinemaPostcode = Integer.parseInt((details[2]));
        Cinema cinema = new Cinema(cinemaID, details[1], cinemaPostcode);
        Gson gson = new Gson();
        String cinemaJson = gson.toJson(cinema);
        String strResponse="";
        //this is for testing, you can check how the json looks like in Logcat
        Log.i("json " , cinemaJson);
        final String methodPath = "restws.cinema";
        RequestBody body = RequestBody.create(cinemaJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    public String addMemoir(String[] details) {
        personReg = new PersonReg();
        personReg.setPersonid(String.valueOf(Person.getPersonid()));
        personReg.setFirstname(Person.getFirstname());
        personReg.setSurname(Person.getSurname());
        personReg.setGender(Person.getGender());
        personReg.setDob(Person.getDob());
        personReg.setGender(Person.getGender());
        personReg.setAddress(Person.getAddress());
        personReg.setState(Person.getState());
        personReg.setPostcode(String.valueOf(Person.getPostcode()));
        cinema = AddToMemoir.cinema();
        MemoirPost memoirPost = new MemoirPost(details[0], details[1], details[2] + "T00:00:00+10:00", details[3], details[4], details[5]);
        memoirPost.setPersonid(personReg);
        memoirPost.setCinemaid(cinema);
        Gson gson = new Gson();
        String memoirPostJson = gson.toJson(memoirPost);
        String strResponse="";
        //this is for testing, you can check how the json looks like in Logcat
        Log.i("json " , memoirPostJson);
        final String methodPath = "restws.memoir";
        RequestBody body = RequestBody.create(memoirPostJson, JSON);
        Request request = new Request.Builder()
                .url(BASE_URL + methodPath)
                .post(body)
                .build();
        try {
            Response response= client.newCall(request).execute();
            strResponse= response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strResponse;
    }

    public String getMemoirs(String personId){
        final String methodPath = "restws.memoir/findByPersonid/" + personId;
        Request.Builder builder = new Request.Builder();
        builder.url(BASE_URL + methodPath);
        Request request = builder.build();
        try {
            Response response = client.newCall(request).execute();
            results = response.body().string();
        }catch (Exception e){
            e.printStackTrace();
        }
        return results;
    }
}
