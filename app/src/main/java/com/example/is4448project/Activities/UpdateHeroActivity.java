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

public class UpdateHeroActivity extends AppCompatActivity {
    EditText txtName, txtRealName, txtRating, txtTeamAffiliation;
    Button btnUpdate;
    private static final String TAG = "*UpdateHeroActivity*";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updatehero);
        getSupportActionBar().hide();

        txtName = findViewById(R.id.txtUpdateName);
        txtRealName = findViewById(R.id.txtUpdateRealName);
        txtRating = findViewById(R.id.txtUpdateRating);
        txtTeamAffiliation = findViewById(R.id.txtUpdateTeam);

        txtName.setText(getIntent().getStringExtra("name"));
        txtRealName.setText(getIntent().getStringExtra("real name"));
        txtRating.setText(getIntent().getStringExtra("rating"));
        txtTeamAffiliation.setText(getIntent().getStringExtra("team aff"));


        btnUpdate = findViewById(R.id.btnUpdate);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String n = txtName.getText().toString();
                String rn = txtRealName.getText().toString();
                String r = txtRating.getText().toString();
                String ta = txtTeamAffiliation.getText().toString();

                if (n.isEmpty()) {
                    Toast.makeText(UpdateHeroActivity.this, "Enter a Name!", Toast.LENGTH_LONG).show();
                } else if (rn.isEmpty()) {
                    Toast.makeText(UpdateHeroActivity.this, "Enter a Name!", Toast.LENGTH_LONG).show();
                } else if (r.isEmpty()) {
                    Toast.makeText(UpdateHeroActivity.this, "Enter a Rating!", Toast.LENGTH_LONG).show();
                } else if (ta.isEmpty()) {
                    Toast.makeText(UpdateHeroActivity.this, "Enter a Team!", Toast.LENGTH_LONG).show();
                } else if (Integer.parseInt(r) > 5) {
                    Toast.makeText(UpdateHeroActivity.this, "Rating cannot be more than 5!", Toast.LENGTH_LONG).show();
                }else {
                    updateHero(n, rn, r, ta);
                }
            }
        });
    }




    public void updateHero(String name, String realname, String rating, String teamaff) {
        RequestQueue queue = Volley.newRequestQueue(UpdateHeroActivity.this);
        String id = getIntent().getStringExtra("id").trim();
        Log.d(TAG, "id is  " + id );
        Log.d(TAG, "new rating is  " + rating );
        String url = "https://gleeson.io/IS4447/HeroAPI/v1/Api.php?apicall=updatehero&id=" + id;
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(UpdateHeroActivity.this, "Hero Updated!", Toast.LENGTH_LONG).show();
                        Log.d("Response", response);
                        Intent intent = new Intent(UpdateHeroActivity.this, GetAllHeroesActivity.class);
                        startActivity(intent);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error!" );

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("id", id);
                params.put("name", name);
                params.put("realname", realname);
                params.put("rating", rating);
                Log.d("Params rating is", rating);
                params.put("teamaffiliation", teamaff);

                return params;
            }
        };
        queue.add(postRequest);

    }
}
