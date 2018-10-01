package com.example.noone.alex_movies2019.repo;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.noone.alex_movies2019.AppExecutors;
import com.example.noone.alex_movies2019.daos.MovieDao;
import com.example.noone.alex_movies2019.daos.MovieFavDao;
import com.example.noone.alex_movies2019.database.AppDatabase;
import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.MovieFav;
import com.example.noone.alex_movies2019.model.MovieResponse;
import com.example.noone.alex_movies2019.model.Review;
import com.example.noone.alex_movies2019.model.ReviewResponse;
import com.example.noone.alex_movies2019.model.Trailer;
import com.example.noone.alex_movies2019.model.TrailerResponse;
import com.example.noone.alex_movies2019.rest.ApiClient;
import com.example.noone.alex_movies2019.rest.ApiInterface;
import com.example.noone.alex_movies2019.utils.Constant;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * Created by NoOne on 9/29/2018.
 */

public class MovieRepository {
    private final MovieDao movieDao;
    private final MovieFavDao movieFavDao;
    private final ApiInterface apiInterface;
    Application application;

    private MutableLiveData<ArrayList<Trailer>> trailersList=new MutableLiveData<>();
    private MutableLiveData<ArrayList<Review>> reviewlist=new MutableLiveData<>();
    private static final String TAG = "TESTMovieRepository";

    @Inject
    public MovieRepository(Application application) {
        this.application=application;
        AppDatabase appDatabase = AppDatabase.getInstance(application);
        movieDao = appDatabase.movieDao();
        movieFavDao = appDatabase.movieFavDao();
        apiInterface = ApiClient.getClient().create(ApiInterface.class);


    }


    public LiveData<Resource<List<Movie>>> loadPopularMovies() {
        return new NetworkBoundResource<List<Movie>, MovieResponse>() {

            @Override
            protected void saveCallResult(@NonNull MovieResponse item) {
                List<Movie> list = new CopyOnWriteArrayList<Movie>();
                list.addAll(item.getResults());
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        for (Movie movie : list) {
                            movie.setType(0);//0 for Popular movies
                            list.add(movie);
                        }

                        movieDao.saveMovies(list);
                        Log.d(TAG, "saveCallResult: ");
                    }
                });


            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                Log.d(TAG, "shouldFetch: ");
                return data == null || data.isEmpty();

            }


            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                Log.d(TAG, "loadFromDb: ");
                return movieDao.loadAllPopular();
            }

            @NonNull
            @Override
            protected Call<MovieResponse> createCall() {
                Log.d(TAG, "createCall: ");
                return apiInterface.getPopularMovies(Constant.API_KEY);


            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<Movie>>> loadTopRated() {
        return new NetworkBoundResource<List<Movie>, MovieResponse>() {

            @Override
            protected void saveCallResult(@NonNull MovieResponse item) {
                List<Movie> list = new CopyOnWriteArrayList<Movie>();
                list.addAll(item.getResults());

                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        for (Movie movie : list) {
                            movie.setType(1);//0 for top rated movies
                            list.add(movie);
                        }

                        movieDao.saveMovies(list);
                        Log.d(TAG, "saveCallResult:  TopRatedMovies");
                    }
                });


            }

            @Override
            protected boolean shouldFetch(@Nullable List<Movie> data) {
                // first thing here ... will call loadFromDb and check if null or not
                Log.d(TAG, "shouldFetch: TopRatedMovies ");
                return data == null || data.isEmpty();

            }


            @NonNull
            @Override
            protected LiveData<List<Movie>> loadFromDb() {
                Log.d(TAG, "loadFromDb: TopRatedMovies");
                return movieDao.loadAllTopRated();
            }

            @NonNull
            @Override
            protected Call<MovieResponse> createCall() {
                Log.d(TAG, "createCall:  TopRatedMovies");

                return apiInterface.getTopRatedMovies(Constant.API_KEY);
            }

            @Override
            protected void onFetchFailed() {
                Log.d(TAG, "onFetchFailed: Check Your Conn");
            }
        }.getAsLiveData();
    }

    public LiveData<Movie> getMovieLiveData(long movie_id) {
        //get from local database
        return movieDao.findMovieById(movie_id);

    }


    public LiveData<List<MovieFav>> getAllFavMoviesLiveData() {
        return movieFavDao.getAllFavorite();
    }



    private void getAllTrailers(String id ) {

        Call<TrailerResponse> call = apiInterface.loadTrailers(id, Constant.API_KEY);
        call.enqueue(new Callback<TrailerResponse>() {
            @Override
            public void onResponse(@NonNull Call<TrailerResponse> call, @NonNull Response<TrailerResponse> response) {
                ArrayList<Trailer> trailers = response.body().getResults();

                /**
                 *
                 *
                 * i create MediatorLiveData array list here because i need to observe the arraylist
                 * and i can assign arraylist from here ((response.body().getResults())) to my Livedata arraylist
                 * so i create MediatorLiveData and use set method
                **/
                trailersList.setValue(trailers) ;

            }

            @Override
            public void onFailure(Call<TrailerResponse> call, Throwable t) {

                Toast.makeText(application, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }
    private void getAllReviews(String id ) {

        Call<ReviewResponse> call = apiInterface.loadReviews(id, Constant.API_KEY);
        call.enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(@NonNull Call<ReviewResponse> call, @NonNull Response<ReviewResponse> response) {
                ArrayList<Review>  reviews = response.body().getResults();
                reviewlist.setValue(reviews) ;
            }
            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {

                Toast.makeText(application, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    public MutableLiveData<ArrayList<Trailer>> getTrailers(String id){
        getAllTrailers(id);
        return trailersList;
    }

    public MutableLiveData<ArrayList<Review>> getReviews(String id){
        getAllReviews(id);
        return reviewlist;
    }
}
