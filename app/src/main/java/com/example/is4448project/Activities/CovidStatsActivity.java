package com.example.is4448project.Activities;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.is4448project.R;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;

public class CovidStatsActivity extends AppCompatActivity {
    TextView txtConfirmed, txtDeaths, txtRecovered, txtActive, txtNewCases, txtNewDeaths, txtUpdated;
    Button btnSymptoms, btnPrevention;

    static final String API_URL = "https://api.covid19api.com/live/country/ireland";
    private static final String TAG = "*GetCovidStatsActivity*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_covidstats);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        txtConfirmed = findViewById(R.id.txtConfirmed);
        txtDeaths = findViewById(R.id.txtDeaths);
        txtRecovered = findViewById(R.id.txtRecovered);
        txtActive = findViewById(R.id.txtActive);
        txtNewCases = findViewById(R.id.txtNewCases);
        txtNewDeaths = findViewById(R.id.txtNewDeaths);
        btnPrevention = findViewById(R.id.btnPrevention);
        btnSymptoms = findViewById(R.id.btnSymptoms);
        txtUpdated = findViewById(R.id.txtUpdated);
        getStats();

        btnSymptoms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CovidStatsActivity.this, CovidWebviewActivity.class);
                intent.putExtra("type", "Symptoms");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CovidStatsActivity.this.startActivity(intent);
            }

        });

        btnPrevention.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(CovidStatsActivity.this, CovidWebviewActivity.class);
                intent.putExtra("type", "Prevention");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                CovidStatsActivity.this.startActivity(intent);

            }

        });


    }

    //some code here adapted from: https://www.itsalif.info/content/android-volley-tutorial-http-get-post-put
    public void getStats() {
        final String url = "https://api.covid19api.com/live/country/ireland";
        RequestQueue queue = Volley.newRequestQueue(CovidStatsActivity.this);
        Log.d(TAG, "get stats running  ");
        JsonArrayRequest getRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        Log.d("Response", response.toString());
                        Log.d(TAG, "on response running  ");
                        try {

                            if (response != null) {
                                Log.d(TAG, "if not null running ");

                                int length = response.length();
                                Log.d(TAG, "response length =   " + length);
                                JSONObject rec = response.getJSONObject(length - 1);
                                Log.d(TAG, "rec   " + rec);

                                String updated = rec.getString("Date");

                                String confirmed = rec.getString("Confirmed");
                                double confirmed2 = Double.parseDouble(confirmed);
                                DecimalFormat formatter = new DecimalFormat("#,###");

                                String deaths = rec.getString("Deaths");
                                double deaths2 = Double.parseDouble(deaths);

                                String recovered = rec.getString("Recovered");
                                double recovered2 = Double.parseDouble(recovered);

                                String active = rec.getString("Active");
                                double active2 = Double.parseDouble(active);

                                Log.d(TAG, "recovered is   " + recovered );

                                JSONObject prev = response.getJSONObject(length - 2);
                                String prevdeaths = prev.getString("Deaths");
                                double prevdeaths2 = Double.parseDouble(prevdeaths);

                                String prevcases = prev.getString("Confirmed");
                                double prevcases2 = Double.parseDouble(prevcases);

                                double newdeaths = deaths2 - prevdeaths2;
                                double newcases = confirmed2 - prevcases2;


                                txtConfirmed.setText(formatter.format(confirmed2));
                                txtDeaths.setText(formatter.format(deaths2));
                                txtRecovered.setText(formatter.format(recovered2));
                                txtActive.setText(formatter.format(active2));
                                txtNewDeaths.setText(formatter.format(newdeaths));
                                txtNewCases.setText(formatter.format(newcases));
                                txtUpdated.setText("Figures shown were last updated: " + updated.substring(8 , 10) + "/" + updated.substring(5, 7)+ "/" + updated.substring(0, 4));

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                      //    Log.d("Error.Response", response);
                        Log.d(TAG, "on error response running  ");
                        Log.d(TAG, "error is  " + error.getMessage());

                    }
                }
        );


        queue.add(getRequest);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {

            case R.id.Home:

                Intent intent2 = new Intent(CovidStatsActivity.this, MainActivity.class);
                startActivity(intent2);

                return true;
            case R.id.Heroes:
                Intent intent3 = new Intent(CovidStatsActivity.this, GetAllHeroesActivity.class);
                startActivity(intent3);

                return true;

            case R.id.Covid:
                Intent intent4 = new Intent(CovidStatsActivity.this, CovidStatsActivity.class);
                startActivity(intent4);

                return true;
            default: return super.onOptionsItemSelected(item);
        }


    }
}
