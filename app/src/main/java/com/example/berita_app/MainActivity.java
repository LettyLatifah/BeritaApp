package com.example.berita_app;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.View;

import com.example.berita_app.R;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private ArrayList<News> mNewsData;
    private NewsAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mNewsData = new ArrayList<>();
        mAdapter = new NewsAdapter(this, mNewsData);
        mRecyclerView.setAdapter(mAdapter);

        initializeData();
    }

    private void initializeData() {
        String[] newsList = getResources()
                .getStringArray(R.array.news_titles);
        String[] newsInfo = getResources()
                .getStringArray(R.array.news_info);

        TypedArray NewsImageResources =
                getResources().obtainTypedArray(R.array.news_images);

        mNewsData.clear();

        for(int i=0;i<newsList.length;i++){
            mNewsData.add(new News(newsList[i],newsInfo[i],
                    NewsImageResources.getResourceId(i,0)));
        }

        NewsImageResources.recycle();

        mAdapter.notifyDataSetChanged();
    }

    public void resetSports(View view) {
        initializeData();
    }
}
