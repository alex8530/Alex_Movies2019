package com.example.noone.alex_movies2019.rest;

import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.MovieResponse;
import com.example.noone.alex_movies2019.model.Review;
import com.example.noone.alex_movies2019.model.Trailer;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by NoOne on 9/8/2018.
 */

public interface ApiInterface {

    @GET("movie/top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String api_key);

    @GET("movie/popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String api_key);


    @GET("movie/{id}")
    Call<Movie> getMovieDetails(@Path("id") long id, @Query("api_key") String apiKey);


    @GET("movie/{id}/videos")
    Call<Trailer> loadTrailers(@Path("id") String id, @Query("api_key") String api_key);

    @GET("/3/movie/{id}/reviews")
    Call<Review> loadReviews(@Path("id") String id, @Query("api_key") String api_key);

}
