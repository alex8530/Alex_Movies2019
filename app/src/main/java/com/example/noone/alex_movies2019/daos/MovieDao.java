package com.example.noone.alex_movies2019.daos;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.MovieFav;

import java.util.List;

/**
 * Created by NoOne on 9/29/2018.
 */

@Dao
public interface MovieDao {



    @Query("SELECT * FROM movie where type = 0 ")
    LiveData<List<Movie>> loadAllPopular();

    @Query("SELECT * FROM movie where type = 1")
    LiveData<List<Movie>> loadAllTopRated();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void saveMovies(List<Movie> movieEntities);


    @Update(onConflict = OnConflictStrategy.REPLACE)
    void updateMovie(Movie movie);

    @Delete
    void deleteMovie(Movie movie);

    @Query("select * from movie where id = :id")
    LiveData<Movie> findMovieById(long id);





}



