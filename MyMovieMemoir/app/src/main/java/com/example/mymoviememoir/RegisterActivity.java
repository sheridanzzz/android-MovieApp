package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoviememoir.networkconnection.NetworkConnection;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

public class RegisterActivity extends AppCompatActivity {
    RadioGroup radioGroup;
    RadioButton selectedRadioButton;
    String gender;
    String state;
    NetworkConnection networkConnection = null;
    List<String> credentialList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText dobText = findViewById(R.id.DobText);
        final EditText regUsernameTxt = findViewById(R.id.regUsernameTxt);
        final EditText regPasswordTxt = findViewById(R.id.regPasswordTxt);
        final EditText regFirstNameTxt = findViewById(R.id.regFirstNameTxt);
        final EditText regLastNameTxt = findViewById(R.id.regLastNameTxt);
        final EditText postcodeTxt = findViewById(R.id.postcodeTxt);
        final EditText addressTxt = findViewById(R.id.addressTxt);
        final Spinner stateSpinner = findViewById(R.id.stateSpinner);
        networkConnection = new NetworkConnection();


        Button btnRegister = findViewById(R.id.btn_register);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = regUsernameTxt.getText().toString();
                String password = regPasswordTxt.getText().toString();
                String firstName = regFirstNameTxt.getText().toString();
                String lastName = regLastNameTxt.getText().toString();
                String address = addressTxt.getText().toString();
                String postcodeString = postcodeTxt.getText().toString();
                String dob = dobText.getText().toString();
                String checkGender = gender;
                String checkState = state;
                String todayDate = currentDate();

                GetByUsernameDuplicateTask getByUsernameDuplicateTask = new GetByUsernameDuplicateTask();

                //create method to check hightest id and then +1
                Random rand = new Random();
                int resRandom = 2002;
                for (int i = 1; i<= 10; i++) {
                    resRandom = rand.nextInt((9999 - 100) + 1) + 10;
                }
                String personID = String.valueOf(resRandom);
                String credentialID = String.valueOf(resRandom);
                List<String> detailsList = new ArrayList<String>();
                credentialList = new ArrayList<String>();

                String passwordHash = getMd5(password);
                //validate form
                if (validateLogin(username, password, firstName, lastName, address, postcodeString, dob, checkGender, checkState)) {

                    getByUsernameDuplicateTask.execute(username);

                    detailsList.add(personID);
                    detailsList.add(firstName);
                    detailsList.add(lastName);
                    detailsList.add(gender);
                    detailsList.add(dob);
                    detailsList.add(address);
                    detailsList.add(state);
                    detailsList.add(postcodeString);

                    credentialList.add(credentialID);
                    credentialList.add(username);
                    credentialList.add(passwordHash);
                    credentialList.add(todayDate);
                    credentialList.add(personID);

                    String[] details = new String[detailsList.size()];
                    for (int j = 0; j < detailsList.size(); j++) {

                        // Assign each value to String array
                        details[j] = detailsList.get(j);
                    }

//                    String[] credentialDetails = new String[credentialList.size()];
//                    for (int j = 0; j < credentialList.size(); j++) {
//
//                        // Assign each value to String array
//                        credentialDetails[j] = credentialList.get(j);
//                    }
                    //do login


                    if (details.length == 8) {
                        AddPersonTask addPersonTask = new AddPersonTask();
                        addPersonTask.execute(details);
                    }

//                    if (credentialDetails.length == 5) {
//                        AddCredentialTask addCredentialTask = new AddCredentialTask();
//                        addCredentialTask.execute(credentialDetails);
//                    }
                }
            }

            private boolean validateLogin(String username, String password, String firstName, String lastName, String address, String postcode, String dob, String gender, String checkState) {
                if (username == null || username.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Username is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (password == null || password.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Password is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (firstName == null || firstName.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "First name is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (lastName == null || lastName.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Last name is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (address == null || address.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Address is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (postcode == null || postcode.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Postcode is required", Toast.LENGTH_LONG).show();
                    return false;
                } else if (postcode.trim().length() < 4 || postcode.trim().length() > 4) {
                    Toast.makeText(getApplicationContext(), "Postcode is 4 digits", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (dob == null || dob.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Date of birth is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (gender == null || gender.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Gender is required", Toast.LENGTH_LONG).show();
                    return false;
                }
                if (checkState == null || checkState.trim().length() == 0 || checkState.equals("Select State")) {
                    Toast.makeText(getApplicationContext(), "Please select a state", Toast.LENGTH_LONG).show();
                    return false;
                }
                return true;
            }

            public String getMd5(String password) {
                try {

                    // Static getInstance method is called with hashing MD5
                    MessageDigest md = MessageDigest.getInstance("MD5");

                    // digest() method is called to calculate message digest
                    //  of an input digest() return array of byte
                    byte[] messageDigest = md.digest(password.getBytes());

                    // Convert byte array into signum representation
                    BigInteger no = new BigInteger(1, messageDigest);

                    // Convert message digest into hex value
                    String hashtext = no.toString(16);
                    while (hashtext.length() < 32) {
                        hashtext = "0" + hashtext;
                    }
                    return hashtext;
                }

                // For specifying wrong message digest algorithms
                catch (NoSuchAlgorithmException e) {
                    throw new RuntimeException(e);
                }
            }

            public String currentDate() {
                String pattern = "yyyy-MM-dd";

                DateFormat df = new SimpleDateFormat(pattern);

                Date today = Calendar.getInstance().getTime();

                return df.format(today);
            }

        });


        dobText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                mcurrentDate.set(Calendar.MONTH, Calendar.JANUARY);
                mcurrentDate.set(Calendar.DAY_OF_MONTH, 9);
                mcurrentDate.set(Calendar.YEAR, 1995);
                int mYear = mcurrentDate.get(Calendar.YEAR);
                int mMonth = mcurrentDate.get(Calendar.MONTH);
                int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(RegisterActivity.this, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        datepicker.setMaxDate(System.currentTimeMillis());
                        selectedmonth = selectedmonth + 1;

                        String sMonth = "";
                        if (selectedmonth < 10) {
                            sMonth = "0" + String.valueOf(selectedmonth);
                        } else {
                            sMonth = String.valueOf(selectedmonth);
                        }
                        dobText.setText("" + selectedyear + "-" + sMonth + "-" + selectedday);

                    }
                }, mYear, mMonth, mDay);
                mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        radioGroup = findViewById(R.id.radioGroupGender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int selectedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonId != -1) {
                    selectedRadioButton = findViewById(selectedRadioButtonId);
                    gender = selectedRadioButton.getText().toString();
                } else {
                    Toast.makeText(getApplicationContext(), "Please select a gender!", Toast.LENGTH_LONG).show();
                }
            }
        });

        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long
                    id) {
                ((TextView) parent.getChildAt(0)).setTextColor(Color.WHITE);
                if (position > 0) {
                    state = parent.getItemAtPosition(position).toString();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private class GetByUsernameDuplicateTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String user = params[0];
            return networkConnection.checkUsernameDuplicate(user);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.length() > 3) {
                Toast.makeText(getApplicationContext(), "Duplicate Username! try again", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class AddPersonTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return networkConnection.addPerson(params);
        }

        @Override
        protected void onPostExecute(String result) {
//            if (result.length() > 3) {
//                Toast.makeText(getApplicationContext(), "Done !", Toast.LENGTH_LONG).show();
//            }
            String[] credentialDetails = new String[credentialList.size()];
            for (int j = 0; j < credentialList.size(); j++) {

                // Assign each value to String array
                credentialDetails[j] = credentialList.get(j);
            }

            if (credentialDetails.length == 5) {
                AddCredentialTask addCredentialTask = new AddCredentialTask();
                addCredentialTask.execute(credentialDetails);
            }
        }
    }

    private class AddCredentialTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            return networkConnection.addCredential(params);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.length() > 3) {
                Toast.makeText(getApplicationContext(), "Registration Done!", Toast.LENGTH_LONG).show();

                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                startActivity(intent);
            }
        }
    }
}
