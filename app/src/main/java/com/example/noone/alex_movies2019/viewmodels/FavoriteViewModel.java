package com.example.noone.alex_movies2019.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.noone.alex_movies2019.daos.MovieDao;
import com.example.noone.alex_movies2019.database.AppDatabase;
import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.MovieFav;
import com.example.noone.alex_movies2019.repo.MovieRepository;
import com.example.noone.alex_movies2019.repo.Resource;
import com.example.noone.alex_movies2019.rest.ApiClient;
import com.example.noone.alex_movies2019.rest.ApiInterface;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by NoOne on 9/30/2018.
 */

public class FavoriteViewModel   extends AndroidViewModel {
    MovieRepository repository;

    @Inject
    public FavoriteViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getInstance(application);
        MovieDao movieDao = appDatabase.movieDao();
        repository = new MovieRepository(application);
    }

    public LiveData<List<MovieFav>> getAllFavMovies() {
        return repository.getAllFavMoviesLiveData();
    }
}
