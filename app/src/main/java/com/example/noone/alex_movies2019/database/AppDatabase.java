package com.example.noone.alex_movies2019.database;

import android.arch.persistence.db.SupportSQLiteOpenHelper;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.DatabaseConfiguration;
import android.arch.persistence.room.InvalidationTracker;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.noone.alex_movies2019.daos.MovieDao;
import com.example.noone.alex_movies2019.daos.MovieFavDao;
import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.MovieFav;

/**
 * Created by NoOne on 9/29/2018.
 */
@Database(entities = {Movie.class, MovieFav.class}, version = 4, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static final String TAG = "TESTAppDatabase";
    private static final String LOG_TAG = AppDatabase.class.getSimpleName();
    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "movieDB";
    private static AppDatabase sInstance;

    public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class, AppDatabase.DATABASE_NAME)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }


    public abstract MovieDao movieDao();
    public abstract MovieFavDao movieFavDao();
}
