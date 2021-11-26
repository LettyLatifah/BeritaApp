package com.example.berita_app.API;

import com.example.berita_app.models.News;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {
    @GET("top-headlines")
    Call<News> getNews
            (@Query("country") String country,
             @Query("apiKey") String apiKey);

    @GET("everything")
    Call<News> getNewsSearch(
            @Query("q") String keyword,
            @Query("language") String language,
            @Query("sortBy") String sortBy,
            @Query("apiKey") String apiKey);
}
