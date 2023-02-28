package com.example.is4448project.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.is4448project.R;

import org.json.JSONObject;

public class GetHeroActivity extends AppCompatActivity {

    TextView txtHero;

    static final String API_URL = "https://gleeson.io/IS4447/HeroAPI/v1/Api.php?apicall=getheroes";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gethero);

        txtHero = (TextView) findViewById(R.id.txtHero);

        Button btngetHero = (Button) findViewById(R.id.btnGetHero);
        btngetHero.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHero();
            }
        });
    }

    public void getHero() {
        final String url = "https://gleeson.io/IS4447/HeroAPI/v1/Api.php?apicall=getheroes";
        RequestQueue queue = Volley.newRequestQueue(GetHeroActivity.this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("Response", response.toString());
                        txtHero.setText(String.valueOf(response));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        queue.add(getRequest);

    }
}
