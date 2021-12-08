package com.example.berita_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;


import com.example.berita_app.ActivityCategory.BusinessActivity;
import com.example.berita_app.ActivityCategory.EntertainmentActivity;
import com.example.berita_app.ActivityCategory.HealthActivity;
import com.example.berita_app.ActivityCategory.ScienceActivity;
import com.example.berita_app.ActivityCategory.SportsActivity;
import com.example.berita_app.ActivityCategory.TechnologyActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.card.MaterialCardView;

public class Category extends AppCompatActivity implements View.OnClickListener  {

    MaterialCardView cvHead, cvSports, cvTechno, cvBusiness, cvHealth, cvEntertaiment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_category);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_category:
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

//                    case R.id.nav_favorite:
//                        startActivity(new Intent(getApplicationContext(),FavoriteActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;

                    case R.id.nav_about:
                        startActivity(new Intent(getApplicationContext(),About.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });


        cvHead = findViewById(R.id.cardHeadLine);
        cvSports = findViewById(R.id.cardSports);
        cvTechno = findViewById(R.id.cardTechno);
        cvBusiness = findViewById(R.id.cardBusiness);
        cvHealth = findViewById(R.id.cardHealth);
        cvEntertaiment = findViewById(R.id.cardEnter);

        cvHead.setOnClickListener(this);
        cvSports.setOnClickListener(this);
        cvTechno.setOnClickListener(this);
        cvBusiness.setOnClickListener(this);
        cvHealth.setOnClickListener(this);
        cvEntertaiment.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cardHeadLine) {
            Intent intent = new Intent(Category.this, ScienceActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.cardSports) {
            Intent intent = new Intent(Category.this, SportsActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.cardTechno) {
            Intent intent = new Intent(Category.this, TechnologyActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.cardBusiness) {
            Intent intent = new Intent(Category.this, BusinessActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.cardHealth) {
            Intent intent = new Intent(Category.this, HealthActivity.class);
            startActivity(intent);
        } else if (v.getId() == R.id.cardEnter) {
            Intent intent = new Intent(Category.this, EntertainmentActivity.class);
            startActivity(intent);
        }
    }

}
