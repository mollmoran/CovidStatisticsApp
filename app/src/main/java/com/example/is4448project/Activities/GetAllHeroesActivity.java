package com.example.is4448project.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.is4448project.Adapters.GetAllHeroesAdapter;
import com.example.is4448project.Models.Hero;
import com.example.is4448project.R;
import com.example.is4448project.Utils.MyDividerItemDecoration;
import com.example.is4448project.Utils.MyTouchListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class GetAllHeroesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    private FloatingActionButton fbAdd;
    private GetAllHeroesAdapter mAdapter;
    private SearchView searchView;
    private ArrayList<Hero> herolist;
    private static final String TAG = "*GetAllHeroesActivity*";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getallheroes);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));

        recyclerView = findViewById(R.id.recyclerview);
        fbAdd = findViewById(R.id.fbAdd);

        // some code here adapted from: Search Functionality for RecyclerView - Android Studio Tutorial (YouTube)

        EditText editText = findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });



        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new MyDividerItemDecoration(this, LinearLayoutManager.VERTICAL, 16));
        recyclerView.setHasFixedSize(true);
        herolist = new ArrayList<>();

        mAdapter = new GetAllHeroesAdapter(this, herolist);
        recyclerView.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();

        getHero();

        fbAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GetAllHeroesActivity.this, CreateHeroActivity.class);
                startActivity(intent);
            }
        });



          /*
        This block of code is adapted from MyRecyclerViewApp by Michael Gleeson
         */
        recyclerView.addOnItemTouchListener(new MyTouchListener(getApplicationContext(), recyclerView, new MyTouchListener.OnTouchActionListener() {
            @Override
            public void onLeftSwipe(View view, int position) {
                final Hero h = herolist.get(position);
                Intent intent= new Intent(GetAllHeroesActivity.this, UpdateHeroActivity.class);
                intent.putExtra("id", h.getId());
                intent.putExtra("name", h.getName());
                intent.putExtra("real name", h.getRealname());
                intent.putExtra("rating", h.getRating());
                intent.putExtra("team aff", h.getTeamaffiliation());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                GetAllHeroesActivity.this.startActivity(intent);
            }

            @Override
            public void onRightSwipe(View view, int position) {
                showActionsDialog(position);

            }

            @Override
            public void onClick(View view, int position) {
                final Hero h = herolist.get(position);
                Intent intent= new Intent(GetAllHeroesActivity.this, UpdateHeroActivity.class);
                intent.putExtra("id", h.getId());
                intent.putExtra("name", h.getName());
                intent.putExtra("real name", h.getRealname());
                intent.putExtra("rating", h.getRating());
                intent.putExtra("team aff", h.getTeamaffiliation());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                GetAllHeroesActivity.this.startActivity(intent);

            }

            public void onLongClick(View view, int position){
                // showActionsDialog(position);
            }
        }
        ) );
    }

    // some code here adapted from: Search Functionality for RecyclerView - Android Studio Tutorial (YouTube)

    private void filter(String text) {
        ArrayList<Hero> filteredList = new ArrayList<>();
        for (Hero item : herolist) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }

        mAdapter.filterList(filteredList);
    }

    //some code here adapted from: https://www.itsalif.info/content/android-volley-tutorial-http-get-post-put

    public void getHero() {
        final String url = "https://gleeson.io/IS4447/HeroAPI/v1/Api.php?apicall=getheroes";
        RequestQueue queue = Volley.newRequestQueue(GetAllHeroesActivity.this);

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", response.toString());
                       //txtHero.setText(String.valueOf(response));

                        try {
                            JSONArray jsonArrayGR = response.getJSONArray("heroes");
                            if (jsonArrayGR != null) {
                                for (int i=0;i<jsonArrayGR.length();i++){
                                    JSONObject rec = jsonArrayGR.getJSONObject(i);
                                    Hero h = new Hero (
                                            rec.getString("id"),
                                            rec.getString("name"),
                                            rec.getString("realname"),
                                            rec.getString("rating"),
                                            rec.getString("teamaffiliation")
                                    );
                                    herolist.add(h);
                                }
                                mAdapter.notifyDataSetChanged();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

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

    //some code here adapted from: https://www.itsalif.info/content/android-volley-tutorial-http-get-post-put

    private void deleteHero(int position) {
        Hero curhero = herolist.get(position);
        String id = curhero.getId();
        RequestQueue queue = Volley.newRequestQueue(this);

        String url = "https://gleeson.io/IS4447/HeroAPI/v1/Api.php?apicall=deletehero&id=" + id;
        StringRequest dr = new StringRequest(Request.Method.DELETE, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(GetAllHeroesActivity.this, "Hero Deleted!", Toast.LENGTH_LONG).show();
                        herolist.remove(position);
                        mAdapter.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(dr);

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GetAllHeroesActivity.this, MainActivity.class);
        startActivity(intent);
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

                Intent intent2 = new Intent(GetAllHeroesActivity.this, MainActivity.class);
                startActivity(intent2);

                return true;
            case R.id.Heroes:
                Intent intent3 = new Intent(GetAllHeroesActivity.this, GetAllHeroesActivity.class);
                startActivity(intent3);

                return true;

            case R.id.Covid:
                Intent intent4 = new Intent(GetAllHeroesActivity.this, CovidStatsActivity.class);
                startActivity(intent4);

                return true;
            default: return super.onOptionsItemSelected(item);
        }


    }

    private void showActionsDialog(final int position) {
        CharSequence colors[] = new CharSequence[]{"Yes", "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete Hero?");
        builder.setItems(colors, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (which == 0) {
                    deleteHero(position);
                } else {
                    dialog.cancel();
                }
            }
        });
        builder.show();
    }


}
