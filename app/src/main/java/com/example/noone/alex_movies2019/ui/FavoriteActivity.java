package com.example.noone.alex_movies2019.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.noone.alex_movies2019.R;
import com.example.noone.alex_movies2019.adapter.MoviesAdapter;
import com.example.noone.alex_movies2019.adapter.MoviesFavoriteAdapter;
import com.example.noone.alex_movies2019.listener.ItemClickLitenerObject;
import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.MovieFav;
import com.example.noone.alex_movies2019.utils.Constant;
import com.example.noone.alex_movies2019.viewmodels.FavoriteViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteActivity extends AppCompatActivity implements ItemClickLitenerObject {

    @BindView(R.id.favRecycleview)
    RecyclerView mFavRecycleview;
    MoviesFavoriteAdapter  mAdapter;
    private static final String TAG = "FavoriteActivity";
    Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);
        mContext=this;
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.fav_my_toolbar);

        if (toolbar!=null){
            setSupportActionBar(toolbar);
            toolbar.setNavigationIcon(R.drawable.ic_keyboard_backspace_black_24dp);
        }

//        ActionBar actionBar = this.getSupportActionBar();
//
//        // Set the action bar back button to look like an up button
//        if (actionBar != null) {
//            actionBar.setDisplayHomeAsUpEnabled(true);
//        }



        mFavRecycleview.setLayoutManager(new GridLayoutManager(this, 2));
        mFavRecycleview.setHasFixedSize(true);
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(
                mFavRecycleview.getContext(),
                R.anim.layout_full_down);
        mFavRecycleview.setLayoutAnimation(animationController);

        //init adapter
        mAdapter = new MoviesFavoriteAdapter(this, this);


        setUpViewModelForFavoriteMovie();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        // When the home button is pressed, take the user back to the VisualizerActivity
        if (id == android.R.id.home) {
            NavUtils.navigateUpFromSameTask(this);
        }
        return super.onOptionsItemSelected(item);
    }
    private void setUpViewModelForFavoriteMovie() {
        FavoriteViewModel viewModel = ViewModelProviders.of(this).get(FavoriteViewModel.class);
        viewModel.getAllFavMovies().observe(this, new Observer<List<MovieFav>>() {
            @Override
            public void onChanged(@Nullable List<MovieFav> movieFavs) {
                if (movieFavs  != null) {

                    Log.d(TAG, "onChanged: " + movieFavs.toString());
                    mAdapter.setMoviesFavList(movieFavs);
                    mFavRecycleview.setAdapter(mAdapter);
                    mFavRecycleview.scheduleLayoutAnimation();
                    mAdapter.notifyDataSetChanged();

                }
            }
        });
    }

    @Override
    public void onClickItemObject(int position) {
        MovieFav Famovie = mAdapter.getMoviesFavList().get(position);
        long id = Famovie.getId();
        Log.d(TAG, "onClickItemObject: ");
        goToDetailesActivity(id);

    }

    private void goToDetailesActivity(long id) {

        Log.d(TAG, "goToDetailesActivity: ");
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constant.MOVIE_ID, id);
        startActivity(intent);
    }


}
