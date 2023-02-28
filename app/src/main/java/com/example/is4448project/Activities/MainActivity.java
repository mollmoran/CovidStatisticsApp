package com.example.is4448project.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.example.is4448project.R;
// I have used the volley library in this application


public class MainActivity extends AppCompatActivity {

    LinearLayout linHeroes, linCovidStats;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        linHeroes = findViewById(R.id.linHeroes);
        linCovidStats = findViewById(R.id.linCovidStats);

        linHeroes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, GetAllHeroesActivity.class);
                startActivity(intent);
            }

        });

        linCovidStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, CovidStatsActivity.class);
                startActivity(intent);
            }

        });
    }
}