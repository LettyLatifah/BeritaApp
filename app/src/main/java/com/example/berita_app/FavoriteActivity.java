package com.example.berita_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.berita_app.Favorite.FavoriteAdapter;
import com.example.berita_app.Favorite.FavoriteDatabase;
import com.example.berita_app.Favorite.FavoriteList;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class FavoriteActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    public static FavoriteDatabase database;
    private Context context;
    public FavoriteAdapter favoriteAdapter;
    private RecyclerView.LayoutManager layoutManager;
    public static List<FavoriteList> favorite_list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        database = Room.databaseBuilder(getApplicationContext(), FavoriteDatabase.class, "Favoritedb").allowMainThreadQueries().build();


        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_favorite);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_category:
                        startActivity(new Intent(getApplicationContext(),Category.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
                        startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        overridePendingTransition(0,0);
                        return true;

                    case R.id.nav_favorite:
                        return true;

                    case R.id.nav_about:
                        startActivity(new Intent(getApplicationContext(),About.class));
                        overridePendingTransition(0,0);
                        return true;
                }

                return false;
            }
        });

        recyclerView = findViewById(R.id.main_recyclerView);
        layoutManager = new LinearLayoutManager(getApplicationContext());

        favorite_list = database.favoriteDao().getFavoriteData();

        favoriteAdapter = new FavoriteAdapter(favorite_list, getApplicationContext());
        recyclerView.setAdapter(favoriteAdapter);
    }
}