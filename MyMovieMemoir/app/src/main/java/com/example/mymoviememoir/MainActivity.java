package com.example.mymoviememoir;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mymoviememoir.entity.Credential;
import com.example.mymoviememoir.entity.Person;
import com.example.mymoviememoir.networkconnection.NetworkConnection;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.lang.reflect.Type;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    String username;
    NetworkConnection networkConnection = null;
    private Credential credential = new Credential();
    private Person person = new Person();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkConnection = new NetworkConnection();
        printKeyHash();

        final EditText usernameTxt = findViewById(R.id.usernameTxt);
        final EditText passwordTxt = findViewById(R.id.passwordTxt);
        final TextView reHere = findViewById(R.id.regHere);

        Button btnLogin = findViewById(R.id.btn_login);
        CheckBox showPassword = findViewById(R.id.showPassword);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                username = usernameTxt.getText().toString();
                String password = passwordTxt.getText().toString();
                GetByUsernameAndPasswordHashTask getByUsernameAndPasswordHashTask = new GetByUsernameAndPasswordHashTask();

                String passwordHash = getMd5(password);
                //validate form
                if (validateLogin(username, password)) {
                    //do login
                    getByUsernameAndPasswordHashTask.execute(username, passwordHash);

                }
            }

            private boolean validateLogin(String username, String password) {
                if (username == null || username.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Username is required", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (password == null || password.trim().length() == 0) {
                    Toast.makeText(getApplicationContext(), "Password is required", Toast.LENGTH_SHORT).show();
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

        });

        reHere.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        showPassword.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    passwordTxt.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    passwordTxt.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

    }

    private void printKeyHash()
    {
        try{
            PackageInfo info = getPackageManager().getPackageInfo("com.example.mymoviememoir", PackageManager.GET_SIGNATURES);
            for(Signature signature:info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private class GetByUsernameAndPasswordHashTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String user = params[0].toString();
            String pass = params[1].toString();
            return networkConnection.checkUsernameAndPasswordHash(user, pass);
        }

        @Override
        protected void onPostExecute(String result) {
            if (result.length() > 3) {
                Toast.makeText(getApplicationContext(), "login done", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
                try {
                    JsonElement je = new JsonParser().parse(result);
                    JsonArray myArray = je.getAsJsonArray();

                    for (JsonElement e : myArray) {
                        // Access the element as a JsonObject
                        JsonObject jo = e.getAsJsonObject();

                        String firstName = jo.getAsJsonObject("personid").get("firstname").toString();
                        String lastName = jo.getAsJsonObject("personid").get("surname").toString();
                        String gender = jo.getAsJsonObject("personid").get("gender").toString();
                        String dob = jo.getAsJsonObject("personid").get("dob").toString();
                        String address = jo.getAsJsonObject("personid").get("address").toString();
                        String state = jo.getAsJsonObject("personid").get("state").toString();
                        String postcode = jo.getAsJsonObject("personid").get("postcode").toString();
                        String personIDString = jo.getAsJsonObject("personid").get("personid").toString();

                        person.setFirstname(firstName);
                        person.setSurname(lastName);
                        person.setGender(gender);
                        person.setAddress(address);
                        person.setState(state);

                        int personid = Integer.parseInt(personIDString);
                        person.setPersonid(personid);
                        person.setPostcode(Integer.parseInt(postcode));

//                        Date date = null;
//                        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//                        date = sdf.parse(dob);
                        person.setDob(dob);
                        String username = jo.get("username").toString();
                        String passwordhash = jo.get("passwordhash").toString();
                        String credentialid = jo.get("credentialid").toString();
                        String signupdate = jo.get("signupdate").toString();
                        credential.setUsername(username);
                        credential.setPasswordhash(passwordhash);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (result.length() <= 2) {
                Toast.makeText(getApplicationContext(), "login failed", Toast.LENGTH_LONG).show();
            }
        }
    }
}
