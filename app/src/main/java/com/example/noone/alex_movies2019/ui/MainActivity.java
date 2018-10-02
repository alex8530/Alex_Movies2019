package com.example.noone.alex_movies2019.ui;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;

import com.example.noone.alex_movies2019.R;
import com.example.noone.alex_movies2019.adapter.MoviesAdapter;
import com.example.noone.alex_movies2019.listener.ItemClickLitenerObject;
import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.repo.Resource;
import com.example.noone.alex_movies2019.utils.Constant;
import com.example.noone.alex_movies2019.viewmodels.MainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ItemClickLitenerObject {

    @BindView(R.id.rcycleView)
    RecyclerView recyclerView;
    MoviesAdapter adapter;
    private static final String TAG = "TESTMainActivity";
    Context mContext;
    private String CURRUNT_STAT;
    private String POPULARE_STAT = "POPULARE";
    private String TOPRATED_STAT = "TOPRATED";
    Menu mMenu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: ");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = this;
        ButterKnife.bind(this);


        //check if this time is not the first time to login to this app
        //but check befor that if there is no network connection , because we need to get data from database if there is no internt

//        if (!Connectivity.isConnected(mContext)|| !Connectivity.isConnectedWifi(mContext)) {
//            if (getTopRatedFromDatabase()!= null){
//                if (getTopRatedFromDatabase().isEmpty()){
//                    //the list is  empty
//                    Log.e(TAG, "onCreate:list is empty and  null  and it is first time"  );
//                    firstTimeTopMovies=true;
//
//                }else {
//                    firstTimeTopMovies=false;
//                }
//            }
//        }


        //toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);


//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setHasFixedSize(true);
        LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(
                recyclerView.getContext(),
                R.anim.layout_full_down);
        recyclerView.setLayoutAnimation(animationController);

        //init adapter
        adapter = new MoviesAdapter(this, this);
        //get getTopRatedMovies and set into recycle view
        //by defult i will sort it on Top rated and put in the menu check=true on top rated

        if (savedInstanceState != null && savedInstanceState.getString("KEY") != null) {

            if (savedInstanceState.getString("KEY").equals(POPULARE_STAT)) {
                setUpViewModelForPopularMovies();
            }
            if (savedInstanceState.getString("KEY").equals(TOPRATED_STAT)) {
                setUpViewModelForTopRatedMovie();
            }
        } else {
            //default state
            setUpViewModelForTopRatedMovie();

        }


    }

    private void setUpViewModelForTopRatedMovie() {
        CURRUNT_STAT = TOPRATED_STAT;

//
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getTopRatedMovie().observe(this, new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Movie>> listResource) {
                if (listResource.data != null) {

                    Log.d(TAG, "onChanged: " + listResource.data.toString());
                    adapter.setMoviesList(listResource.data);
                    recyclerView.setAdapter(adapter);
                    recyclerView.scheduleLayoutAnimation();
                    adapter.notifyDataSetChanged();

                }
            }
        });


    }

    private void setUpViewModelForPopularMovies() {


        CURRUNT_STAT = POPULARE_STAT;
//
        MainViewModel viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getPopularMovie().observe(this, new Observer<Resource<List<Movie>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Movie>> listResource) {
                if (listResource.data != null) {

                    Log.d(TAG, "onChanged: " + listResource.data.toString());
                    adapter.setMoviesList(listResource.data);
                    recyclerView.setAdapter(adapter);
                    recyclerView.scheduleLayoutAnimation();
                    adapter.notifyDataSetChanged();

                }
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();


        if (itemId == R.id.menu_popularMovies) {
            if (item.isChecked()) {
                item.setChecked(false);
            } else {
                item.setChecked(true);
                setUpViewModelForPopularMovies();
            }

            return true;
        }

        if (itemId == R.id.menu_top_ratedMovies) {
            if (item.isChecked()) {
                //No need to do this line because the checkbox in single group
                item.setChecked(false);

            } else {
                item.setChecked(true);
                setUpViewModelForTopRatedMovie();
            }
            return true;
        }

        if (itemId == R.id.menu_favorite) {
            startActivity(new Intent(MainActivity.this, FavoriteActivity.class));

        }


        return super.onOptionsItemSelected(item);

    }

    @Override
    public void onClickItemObject(int position) {
        Movie movie = adapter.getMoviesList().get(position);
        long id = movie.getId();
        Log.d(TAG, "onClickItemObject: ");
        goToDetailesActivity(id);

    }


    private void goToDetailesActivity(long id) {
        Log.d(TAG, "goToDetailesActivity: ");
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constant.MOVIE_ID, id);


        //add animation
        ActivityOptions options=null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            options = ActivityOptions.makeSceneTransitionAnimation(this);
            startActivity(intent,options.toBundle());
        }else {
            startActivity(intent );
        }




    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: ");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Log.d(TAG, "onRestart: ");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {

        Log.d(TAG, "onSaveInstanceState: " + CURRUNT_STAT);
        outState.putString("KEY", CURRUNT_STAT);
        super.onSaveInstanceState(outState);

    }
}
//todo  set backgraound