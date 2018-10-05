package com.example.noone.alex_movies2019.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.noone.alex_movies2019.BuildConfig;

/**
 * Created by NoOne on 9/8/2018.
 */

public final class Constant {
    private Constant() {
        //to prvent init
    }

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
     public static final String API_KEY = BuildConfig.ApiKey;
    public static final String BASE_IMAGE_URL="http://image.tmdb.org/t/p/w185/";
    public static final String YT_THUMB_URL = "http://img.youtube.com/vi/";
    public static String MOVIE_TITLE="MOVIE_TITLE";
    public static String MOVIE_ID="MOVIE_ID";
    public static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=";


    public static String makeThumbnailURL(String key) {
        return  YT_THUMB_URL.concat(key).concat("/hqdefault.jpg");
    }
}
