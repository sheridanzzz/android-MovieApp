package com.example.mymoviememoir;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class SearchGoogleAPI {
    private static final String API_KEY = "AIzaSyAYWoPpD4G0HvRMjb0ZlS7ecyOMokz8GHs";
    private static final String SEARCH_ID_cx = "013629943234444082238:wdoj6hbovlj";

    public static String search(String keyword) {
        keyword = keyword.replace(" ", "+");
        URL url = null;
        HttpURLConnection connection = null;
        String textResult = "";
        String query_parameter = "";

        try {

            url = new URL("https://www.googleapis.com/customsearch/v1?googlehost=google.co.uk&safe=medium&searchType=image&key=AIzaSyAYWoPpD4G0HvRMjb0ZlS7ecyOMokz8GHs&cx=013629943234444082238:wdoj6hbovlj&q=" + keyword);
            connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(10000);
            connection.setConnectTimeout(15000);
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Accept", "application/json");
            Scanner scanner = new Scanner(connection.getInputStream());
            while (scanner.hasNextLine()) {
                textResult += scanner.nextLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            connection.disconnect();
        }
        return textResult;
    }


    public static String getSnippet(String result){
        String snippet = null;
        try{
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArray = jsonObject.getJSONArray("items");
            if(jsonArray != null && jsonArray.length() > 0) {
                snippet =jsonArray.getJSONObject(1).getString("link");
            }
        }catch (Exception e){
            e.printStackTrace();
            snippet = "NO INFO FOUND";
        }
        return snippet;
    }
}
