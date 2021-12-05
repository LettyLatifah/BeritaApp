package com.example.berita_app.Favorite;


import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities={FavoriteList.class},version = 1)
public abstract class FavoriteDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();
}
