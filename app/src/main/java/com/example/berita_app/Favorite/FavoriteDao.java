package com.example.berita_app.Favorite;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {
    @Insert
    public void addData(FavoriteList favorite_table);

    @Query("select * from favorite_table")
    public List<FavoriteList> getFavoriteData();

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_table WHERE id=:id)")
    public int isFavorite(int id);

    @Delete
    public void delete(FavoriteList favorite_table);

}
