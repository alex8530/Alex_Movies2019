package com.example.noone.alex_movies2019.viewmodels;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

/**
 * Created by NoOne on 9/30/2018.
 */

public class DetailesViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final long movie_id;
    private Application application;

    public DetailesViewModelFactory(Application application, long movie_id) {
        this.movie_id = movie_id;
        this.application = application;

    }

    // COMPLETED (4) Uncomment the following method
    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {
        //noinspection unchecked
        return (T) new DetailesViewModel(application, movie_id);
    }


}
