package com.example.noone.alex_movies2019.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.example.noone.alex_movies2019.model.MovieFav;

import java.util.List;

/**
 * Created by NoOne on 9/30/2018.
 */
@Dao
public interface MovieFavDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovieFavorite(MovieFav movieFav);

    @Delete
    void deleteMovieFav(MovieFav movieFav);


    @Query("SELECT * FROM movieFav")
    LiveData<List<MovieFav>> getAllFavorite();


    @Query("SELECT * FROM movieFav WHERE id = :id")
    LiveData<List<MovieFav>>   getMovieFav(long id);
}
