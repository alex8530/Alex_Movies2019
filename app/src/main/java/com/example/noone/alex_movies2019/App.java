package com.example.noone.alex_movies2019;

import android.app.Application;
import android.util.Log;


//import com.example.noone.alex_movies2019.database.modelDb.MyObjectBox;

import com.example.noone.alex_movies2019.database.modelDb.MyObjectBox;

import io.objectbox.Box;
import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;


public class App extends Application {
    private BoxStore boxStore;
    @Override
    public void onCreate() {
        super.onCreate();

        boxStore = MyObjectBox.builder().androidContext(App.this).build();
        if (BuildConfig.DEBUG) {
            new AndroidObjectBrowser(boxStore).start(this);
        }

        Log.d("App", "Using ObjectBox " + BoxStore.getVersion() + " (" + BoxStore.getVersionNative() + ")");
    }

    public BoxStore getBoxStore() {
        return boxStore;
     }
}
