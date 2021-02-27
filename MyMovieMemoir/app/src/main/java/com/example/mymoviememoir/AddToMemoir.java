package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.mymoviememoir.entity.Cinema;
import com.example.mymoviememoir.entity.Person;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

public class AddToMemoir extends AppCompatActivity {
    private static Cinema cinema;
    public TextView releaseDateTxt;
    public TextView movieNameTxt;
    String date;
    String cinemaPos;
    String movieName;
    String releaseDate;
    EditText dateTimeAddedTxt;
    EditText commentTxt;
    public RatingBar rateBar;
    NetworkConnection networkConnection = null;
    Spinner cinemaSpinner;
    ArrayAdapter<String> spinnerAdapter;
    List<String> list = new ArrayList<String>();
    //public Cinema cinema;
    private static ArrayList<Cinema> cinemas = new ArrayList<Cinema>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_memoir);
        networkConnection = new NetworkConnection();
        Bundle bundle = getIntent().getExtras();
        Button btnAddCinema = findViewById(R.id.btn_cinAdd);
        Button btnMem = findViewById(R.id.btn_mem);
        cinemaSpinner = findViewById(R.id.cinemaSpinner);
        movieName = bundle.getString("movieName");
        releaseDate = bundle.getString("releaseDate");
        String posterPath = bundle.getString("posterPath");
        dateTimeAddedTxt = findViewById(R.id.dateTimeAddedTxt);
        rateBar = findViewById(R.id.starBarAM);
        cinema = new Cinema();

        GetCinemaNamesTask getCinemaNamesTask = new GetCinemaNamesTask();
        getCinemaNamesTask.execute();


        movieNameTxt = findViewById(R.id.movieNameAM);
        releaseDateTxt = findViewById(R.id.movieReleaseDateAM);
        commentTxt = findViewById(R.id.commentTxt);

        final ImageView imageView = findViewById(R.id.imageViewAM);

        movieNameTxt.setText(movieName);
        releaseDateTxt.setText(releaseDate);
        Picasso.with(this).load(posterPath).into(imageView);

        spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list);
        spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        cinemaSpinner.setAdapter(spinnerAdapter);


        cinemaSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long
                    id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                if (position > 0) {
                    cinemaPos = parent.getItemAtPosition(position).toString();
                }

                //Toast.makeText(parent.getContext(), "Selected: " + cinemaPos, Toast.LENGTH_LONG).show();

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        dateTimeAddedTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                mcurrentDate.set(Calendar.MONTH, Calendar.MAY);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, 31);
                mcurrentDate.set(Calendar.YEAR, 2020);
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog mDatePicker = new DatePickerDialog(AddToMemoir.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        datepicker.setMaxDate(System.currentTimeMillis());
                        selectedmonth = selectedmonth + 1;

                        String sMonth = "";
                        if (selectedmonth < 10) {
                            sMonth = "0" + String.valueOf(selectedmonth);
                        } else {
                            sMonth = String.valueOf(selectedmonth);
                        }
                        date = ("" + selectedyear + "-" + sMonth + "-" + selectedday);
                        dateTimeAddedTxt.setText("" + selectedyear + "-" + sMonth + "-" + selectedday);
                        timePicker();
                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();

            }
        });

        btnAddCinema.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AddToMemoir.this, AddCinema.class);
                startActivity(intent);
            }
        });

        btnMem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random rand = new Random();
                int resRandom = 2002;
                for (int i = 1; i<= 10; i++) {
                    resRandom = rand.nextInt((9999 - 100) + 1) + 10;
                }
                String memoirID = String.valueOf(resRandom);
                int personID = Person.getPersonid();
                movieName = movieName;
                releaseDate = releaseDate;
                String comment = commentTxt.getText().toString();
                String rating = String.valueOf(rateBar.getRating());
                String watcheddatetime = dateTimeAddedTxt.getText().toString();
                String cinemaId = null;
                String personIDString = String.valueOf(personID);



                List<String> detailsList = new ArrayList<String>();

                if (validateLogin(movieName, releaseDate, comment, rating, watcheddatetime, cinemaPos)) {

                    String str = cinemaPos.replaceAll("\\d", "");
                    str = str.replaceAll("\\s+$", "");
                    int i = cinemaSpinner.getSelectedItemPosition();
                    cinema = new Cinema(cinemas.get(i));

                    detailsList.add(memoirID);
                    detailsList.add(movieName);
                    detailsList.add(releaseDate);
                    detailsList.add(comment);
                    detailsList.add(rating);
                    detailsList.add(watcheddatetime);

                    String[] details = new String[detailsList.size()];
                    for (int j = 0; j < detailsList.size(); j++) {
                        // Assign each value to String array
                        details[j] = detailsList.get(j);
                    }

                    if (details.length == 6) {

                        AddMemoirTask addMemoirTask = new AddMemoirTask();
                        addMemoirTask.execute(details);
                    }
                }
            }

            private boolean validateLogin(String movieName, String releaseDate, String comment, String rating, String watcheddatetime, String cinemaPos) {
                if (movieName == null || movieName.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Movie Name is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (releaseDate == null || releaseDate.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Release Date is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (comment == null || comment.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Comment is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (rating == null || rating.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Rating is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (watcheddatetime == null || watcheddatetime.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Watched date time is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (cinemaPos == null || cinemaPos.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Cinema is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;

            }
        });

    }

    private void timePicker() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        Calendar mcurrentDate = Calendar.getInstance();
        int mHour = mcurrentDate.get((Calendar.HOUR_OF_DAY));
        int mMinute = mcurrentDate.get((Calendar.MINUTE));

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        dateTimeAddedTxt.setText(date + "T"+ hourOfDay + ":" + minute + ":43.388+11:00");
                    }
                }, mHour, mMinute, true);
        timePickerDialog.show();
    }

    private class GetCinemaNamesTask extends AsyncTask<Void, Void, String> {
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

            String cinName;
            for (int i = 0; i < cinemas.size(); i++) {
                cinName = cinemas.get(i).getCinemaname();
                cinName = cinName.replace("\"", "");
                String cinemaName = cinName + " " + String.valueOf(cinemas.get(i).getPostcode());
                list.add(cinemaName);
            }

            spinnerAdapter.notifyDataSetChanged();
        }
    }

    private class AddMemoirTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return networkConnection.addMemoir(params);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.length() > 3) {
                Toast.makeText(getApplicationContext(), "Memoir Added!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public static ArrayList<Cinema> getCinemas() {
        return cinemas;
    }

    public static Cinema cinema() {

        return cinema;
    }

}
