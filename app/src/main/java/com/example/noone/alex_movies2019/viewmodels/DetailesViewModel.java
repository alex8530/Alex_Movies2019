package com.example.noone.alex_movies2019.viewmodels;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.example.noone.alex_movies2019.database.AppDatabase;
import com.example.noone.alex_movies2019.model.Movie;

/**
 * Created by NoOne on 9/30/2018.
 */

public class DetailesViewModel extends ViewModel {

    private LiveData<Movie> movieLiveData;


    public DetailesViewModel(Application application ,long movie_id) {
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        movieLiveData=  appDatabase.movieDao().findMovieById(movie_id);

    }

    public LiveData<Movie> getMovieLiveData() {

        return movieLiveData;
    }
}
