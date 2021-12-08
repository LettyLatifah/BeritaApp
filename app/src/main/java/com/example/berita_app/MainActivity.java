package com.example.berita_app;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Room;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.berita_app.API.APIClient;
import com.example.berita_app.API.APIInterface;
import com.example.berita_app.Favorite.FavoriteDatabase;
import com.example.berita_app.models.Article;
import com.example.berita_app.models.News;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    public static final String country_id = "id";
    public static final String language_id = "id";
    public static final String API_KEY = "762ca07378aa4145b832435604b871cc";

    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private List<Article> articles = new ArrayList<>();
    private Adapter adapter;
    private String TAG = MainActivity.class.getSimpleName();
    public static FavoriteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        database = Room.databaseBuilder(getApplicationContext(),
                FavoriteDatabase.class, "Favoritedb").allowMainThreadQueries().build();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_home);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_category:
                        startActivity(new Intent(getApplicationContext(),Category.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.nav_home:
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

        recyclerView = findViewById(R.id.main_recyclerView);
        layoutManager = new LinearLayoutManager(MainActivity.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        loadJson("");
    }

    public void loadJson(final String keyword){
        APIInterface apiInterface = APIClient.getApiClient().create(APIInterface.class);


        String language = Utils.getLanguage();

        Call<News> call;
        if(keyword.length()>0){
            call = apiInterface.getNewsSearch(keyword, language_id, "publishedAt",API_KEY);
        } else {
            call = apiInterface.getNews(country_id, API_KEY);
        }



        call.enqueue(new Callback<News>() {
            @Override
            public void onResponse(Call<News> call, Response<News> response){
                if (response.isSuccessful() && response.body().getArticle() != null) {

                    if (!articles.isEmpty()) {
                        articles.clear();
                    }

                    articles = response.body().getArticle();
                    adapter = new Adapter(articles, MainActivity.this);
                    recyclerView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    initListener();

                }else{
                    Toast.makeText(MainActivity.this, "No Result", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<News> call, Throwable t){

            }

        });

    }


    private void initListener(){

        adapter.setOnItemClickListener(new Adapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                ImageView imageView = view.findViewById(R.id.img);
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);

                Article article = articles.get(position);
                intent.putExtra("url", article.getUrl());
                intent.putExtra("title", article.getTitle());
                intent.putExtra("img",  article.getUrlToImage());
                intent.putExtra("date",  article.getPublishedAt());
                intent.putExtra("source",  article.getSource().getName());
                intent.putExtra("author",  article.getAuthor());

                startActivity(intent);


            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        final SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        MenuItem searchMenuItem = menu.findItem(R.id.action_search);

        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Mencari Berita Terbaru....");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if (query.length() > 2){
                    loadJson(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                loadJson(newText);
                return false;
            }
        });
        searchMenuItem.getIcon().setVisible(false, false);

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id=item.getItemId();

        if (id==R.id.action_notifications){
            startActivity(new Intent(getApplicationContext(),NotificationAlarm.class));
            overridePendingTransition(0,0);
            return true;
        }
        return false;
    }

}