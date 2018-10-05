package com.example.noone.alex_movies2019.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.noone.alex_movies2019.daos.MovieDao;
import com.example.noone.alex_movies2019.database.AppDatabase;
import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.repo.MovieRepository;
import com.example.noone.alex_movies2019.repo.Resource;
import com.example.noone.alex_movies2019.rest.ApiClient;
import com.example.noone.alex_movies2019.rest.ApiInterface;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by NoOne on 9/29/2018.
 */

public class MainViewModel extends AndroidViewModel {
    private static final String TAG = "TESTMainViewModel";
    MovieRepository repository;


    @Inject
    public MainViewModel(@NonNull Application application) {
        super(application);

        AppDatabase appDatabase = AppDatabase.getInstance(application);
        MovieDao movieDao = appDatabase.movieDao();
        ApiInterface mApiInterface = ApiClient.getClient().create(ApiInterface.class);

        repository = new MovieRepository(application);

    }

    public LiveData<Resource<List<Movie>>> getPopularMovie() {
        return repository.loadPopularMovies();
    }

    public LiveData<Resource<List<Movie>>> getTopRatedMovie() {
        return repository.loadTopRated();
    }

}
