package com.example.noone.alex_movies2019;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Toast;

import com.example.noone.alex_movies2019.adapter.MoviesAdapter;
import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.MovieResponse;
import com.example.noone.alex_movies2019.rest.ApiClient;
import com.example.noone.alex_movies2019.rest.ApiInterface;
import com.example.noone.alex_movies2019.utils.Constant;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
      RecyclerView recyclerView;
    ApiInterface mApiInterface;
    List<Movie> moviesList;
    MoviesAdapter adapter;
    private static final String TAG = "TESTMainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


        //init recycleView
        recyclerView =   findViewById(R.id.rcycleView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setHasFixedSize(true);
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(
                recyclerView.getContext(),
                R.anim.layout_full_down);
        recyclerView.setLayoutAnimation(animationController);

        //init adapter
          adapter = new MoviesAdapter(this);

          //get getTopRatedMovies and set into recycle view
         getTopRatedMovies();

    }

    private void makeSeplashScrean() {
        final Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

//                startActivity(new Intent(this,Home_Layout.class));
//                finish();
            }
        },3000);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId  = item.getItemId();


        if (itemId==R.id.menu_popularMovies){
            getTopRatedMovies();

            return true;
        }

         if (itemId==R.id.menu_top_ratedMovies){

             getPopularMovies();
             return true;
        }


        return super.onOptionsItemSelected(item);

    }

    private void getPopularMovies() {

        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> movieResponseCall =  mApiInterface.getPopularMovies(Constant.API_KEY);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.e(TAG, "onResponse: " + response.code() );
                Log.e(TAG, "onResponse: "+response.body().getResults() );

                moviesList = response.body().getResults();
                adapter.setMoviesList(moviesList);

                recyclerView.setAdapter(adapter);
                recyclerView.scheduleLayoutAnimation();

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                Log.e(TAG, "onFailure: "+ t.getMessage() );
            }
        });
    }

    public void getTopRatedMovies(){
        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
        Call<MovieResponse> movieResponseCall =  mApiInterface.getTopRatedMovies(Constant.API_KEY);
        movieResponseCall.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                Log.e(TAG, "onResponse: " + response.code() );
                Log.e(TAG, "onResponse: "+response.body().getResults() );

                moviesList = response.body().getResults();
                adapter.setMoviesList(moviesList);

                recyclerView.setAdapter(adapter);
                recyclerView.scheduleLayoutAnimation();

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {

                Log.e(TAG, "onFailure: "+ t.getMessage() );
            }
        });
    }


}
//todo  set backgraound and scplash screen