package com.example.is4448project.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.is4448project.R;

import java.util.HashMap;
import java.util.Map;

public class CreateHeroActivity extends AppCompatActivity {
    EditText txtName, txtRealName, txtRating, txtTeamAffiliation;
    Button btnCreate;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_createhero);
        getSupportActionBar().hide();

        txtName = findViewById(R.id.txtName);
        txtRealName = findViewById(R.id.txtRealName);
        txtRating = findViewById(R.id.txtRating);
        txtTeamAffiliation = findViewById(R.id.txtTeam);

        btnCreate = findViewById(R.id.btnCreate);
        btnCreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = txtName.getText().toString();
                String rn = txtRealName.getText().toString();
                String r = txtRating.getText().toString();
                String ta = txtTeamAffiliation.getText().toString();

                if (n.isEmpty()) {
                    Toast.makeText(CreateHeroActivity.this, "Enter a Name!", Toast.LENGTH_LONG).show();
                } else if (rn.isEmpty()) {
                    Toast.makeText(CreateHeroActivity.this, "Enter a Name!", Toast.LENGTH_LONG).show();
                } else if (r.isEmpty()) {
                    Toast.makeText(CreateHeroActivity.this, "Enter a Rating!", Toast.LENGTH_LONG).show();
                } else if (ta.isEmpty()) {
                    Toast.makeText(CreateHeroActivity.this, "Enter a Team!", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(r) > 5) {
                    Toast.makeText(CreateHeroActivity.this, "Rating cannot be more than 5!", Toast.LENGTH_LONG).show();
                }else {
                    createHero(n, rn, r, ta);
                    Intent intent = new Intent(CreateHeroActivity.this, GetAllHeroesActivity.class);
                    startActivity(intent);
                    Toast.makeText(CreateHeroActivity.this, "Hero Added!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

   //some code here adapted from: https://www.itsalif.info/content/android-volley-tutorial-http-get-post-put

    public void createHero(String name, String realname, String rating, String teamaff) {
        RequestQueue queue = Volley.newRequestQueue(CreateHeroActivity.this);
        String url = "https://gleeson.io/IS4447/HeroAPI/v1/Api.php?apicall=createhero";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Toast.makeText(CreateHeroActivity.this, "Hero Added!", Toast.LENGTH_LONG).show();
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {


                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("name", name);
                params.put("realname", realname);
                params.put("rating", rating);
                params.put("teamaffiliation", teamaff);

                return params;
            }
        };
        queue.add(postRequest);

    }



}
