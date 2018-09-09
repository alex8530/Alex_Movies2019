package com.example.noone.alex_movies2019.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * Created by NoOne on 9/8/2018.
 */

public  class Constant {
    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    public static final String API_KEY = "27230e1667c419dd452ea128b62c8055";
    public static String MOVIE_ID;
    public static final String BASE_IMAGE_URL="http://image.tmdb.org/t/p/w185/";



    public static boolean isConnectedToInternet(Context context)
    {
        ConnectivityManager manager= (ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager!=null)
        {
            NetworkInfo[] info = manager.getAllNetworkInfo();
            if (info!= null)
            {
                for (int i = 0; i <info.length ; i++) {
                    if (info[i].getState()==NetworkInfo.State.CONNECTED)
                        return true;
                }
            }
        }
        return false;
    }

}
