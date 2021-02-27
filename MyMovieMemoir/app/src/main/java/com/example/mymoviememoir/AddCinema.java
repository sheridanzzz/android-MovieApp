package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymoviememoir.entity.Cinema;
import com.example.mymoviememoir.networkconnection.NetworkConnection;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class AddCinema extends AppCompatActivity {
    String username;
    NetworkConnection networkConnection = null;
    EditText cinemaNameText;
    EditText CinemaPostcodeText;
    String cinName;
    String cinPostcode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cinema);
        Button btnAddCinema2 = findViewById(R.id.btn_addCinema2);
        cinemaNameText = findViewById(R.id.CinemaNameTxt);
        CinemaPostcodeText = findViewById(R.id.CinemaPostcodeTxt);
        networkConnection = new NetworkConnection();


        AddCinemaTask addCinemaTask = new AddCinemaTask();

        btnAddCinema2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cinName = cinemaNameText.getText().toString();
                cinPostcode = CinemaPostcodeText.getText().toString();
                List<String> cinemaList = new ArrayList<String>();
                Random rand = new Random();
                int resRandom = 2002;
                for (int i = 1; i <= 10; i++) {
                    resRandom = rand.nextInt((9999 - 100) + 1) + 10;
                }

                if (validateLogin(cinName, cinPostcode)) {
                    cinemaList.add(String.valueOf(resRandom));
                    cinemaList.add(cinName);
                    cinemaList.add(cinPostcode);

                    String[] details = new String[cinemaList.size()];
                    for (int j = 0; j < cinemaList.size(); j++) {

                        // Assign each value to String array
                        details[j] = cinemaList.get(j);
                    }

                    if (details.length == 3) {
                        AddCinemaTask addCinemaTask = new AddCinemaTask();
                        addCinemaTask.execute(details);
                    }
                }
            }
        });
    }

    private boolean validateLogin(String cinName, String cinPostcode) {
        if (cinName == null || cinName.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Name is required", Toast.LENGTH_LONG).show();
            return false;
        }
        if (cinPostcode == null || cinPostcode.trim().length() == 0) {
            Toast.makeText(getApplicationContext(), "Postcode is required", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;

    }


    private class AddCinemaTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return networkConnection.addCinema(params);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.length() > 3) {
                Toast.makeText(getApplicationContext(), "Cinema Added!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(AddCinema.this, AddToMemoir.class);
                startActivity(intent);
            }
        }
    }

}
