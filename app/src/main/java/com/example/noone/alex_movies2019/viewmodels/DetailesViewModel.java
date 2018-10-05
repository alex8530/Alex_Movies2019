package com.example.noone.alex_movies2019.viewmodels;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.example.noone.alex_movies2019.database.AppDatabase;
import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.Review;
import com.example.noone.alex_movies2019.model.Trailer;
import com.example.noone.alex_movies2019.repo.MovieRepository;

import java.util.ArrayList;

/**
 * Created by NoOne on 9/30/2018.
 */

public class DetailesViewModel extends ViewModel {

    private LiveData<Movie> movieLiveData;

    private MutableLiveData<ArrayList<Trailer>> trailersLiveData;
    private MutableLiveData<ArrayList<Review>> reviewLiveData;



    public DetailesViewModel(Application application ,long movie_id) {
        MovieRepository  repository= new MovieRepository(application);
        movieLiveData=  repository.getMovieLiveData(movie_id);
        trailersLiveData= repository.getTrailers(String.valueOf(movie_id));
        reviewLiveData= repository.getReviews(String.valueOf(movie_id));

    }

    public LiveData<Movie> getMovieLiveData() {

        return movieLiveData;
    }



    public MutableLiveData<ArrayList<Trailer>> getTrailersLiveData() {
        return trailersLiveData;
    }

    public MutableLiveData<ArrayList<Review>> getReviewLiveData() {
        return reviewLiveData;
    }

}
