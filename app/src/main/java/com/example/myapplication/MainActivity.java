package com.example.myapplication;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "https://backend-test-zypher.herokuapp.com/testData";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!Fresco.hasBeenInitialized())
        Fresco.initialize(this);
        new httpResponse(this).execute();
    }

    class httpResponse extends AsyncTask<String, Void, String> {
        Activity activity;

        httpResponse(Activity activity) {
            this.activity = activity;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                ShowDialog.getShowDialogInstance(jsonObject.getString("title"), jsonObject.getString("imageURL"), jsonObject.getString("success_url"), MainActivity.this);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder response = new StringBuilder();
            HttpURLConnection http = null;
            InputStream stream = null;
            InputStreamReader inputStreamReader = null;
            BufferedReader br = null;
            try {
                URL url = new URL(URL);
                URLConnection con = url.openConnection();
                http = (HttpURLConnection) con;
                http.setRequestMethod("POST");

                if (http.getResponseCode() == HttpsURLConnection.HTTP_OK) {
                    String line;
                    stream = http.getInputStream();
                    inputStreamReader = new InputStreamReader(stream);
                    br = new BufferedReader(inputStreamReader);
                    while ((line = br.readLine()) != null) {
                        response.append(line);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (br != null)
                        br.close();

                    if (inputStreamReader != null)
                        inputStreamReader.close();

                    if (stream != null)
                        stream.close();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (http != null)
                    http.disconnect();
            }
            return response.toString();
        }
    }
}
