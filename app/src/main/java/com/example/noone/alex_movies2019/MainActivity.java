package com.example.noone.alex_movies2019;

import android.app.ProgressDialog;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.noone.alex_movies2019.adapter.MoviesAdapter;
import com.example.noone.alex_movies2019.listener.ItemClickLitenerObject;
import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.MovieResponse;
import com.example.noone.alex_movies2019.rest.ApiClient;
import com.example.noone.alex_movies2019.rest.ApiInterface;
import com.example.noone.alex_movies2019.utils.Constant;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.android.AndroidScheduler;
import io.objectbox.query.Query;
import io.objectbox.reactive.DataObserver;
import io.objectbox.reactive.DataSubscriptionList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemClickLitenerObject{
      RecyclerView recyclerView;
    ApiInterface mApiInterface;
    List<Movie> moviesList;
    MoviesAdapter adapter;
    private static final String TAG = "TESTMainActivity";
      ProgressDialog dialog;
    private DataSubscriptionList subscriptions = new DataSubscriptionList();


    private Box<Movie> popularMovieDbBox;
    private Query<Movie> movieDbQuery;



    boolean firstTime;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        popularMovieDbBox =  ((App) getApplication()).getBoxStore().boxFor(Movie.class);
         movieDbQuery=popularMovieDbBox.query().build();

        movieDbQuery.subscribe(subscriptions).on(AndroidScheduler.mainThread())
                .observer(new DataObserver<List<Movie>>() {
                    @Override
                    public void onData(List<Movie> movies) {


                        for (Movie mdv : movies){
                          //  Log.e(TAG, "onData: "+mdv.toString()+"\n" );

                        }

                    }
                });


        moviesList=getPopularFromDatabase();

        if (moviesList==null){
            //the list is  empty
            Log.e(TAG, "onCreate:list is empty and  null  and it is first time"  );
            firstTime=true;

        }else {
            firstTime=false;
        }




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
          adapter = new MoviesAdapter(this,this);



            //get getTopRatedMovies and set into recycle view
        //by defult i will sort it on Top rated and put in the menu check=true on top rated
            getTopRatedMovies();

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
            if (item.isChecked()){
                item.setChecked(false);
            }else {
                item.setChecked(true);
                getTopRatedMovies();
            }

            return true;
        }

         if (itemId==R.id.menu_top_ratedMovies){
             if (item.isChecked()){
                 //No need to di this line because the checkbox in single group
//                 item.setChecked(false);

             }else {
//                 item.setChecked(true);
                 getPopularMovies();
             }


             return true;
        }


        return super.onOptionsItemSelected(item);

    }

    private void getPopularMovies() {
        //todo update this if
        //ADD THE THING HERE
        if (Constant.isConnectedToInternet(getApplicationContext())) {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("please wait .....");
            dialog.show();


            mApiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<MovieResponse> movieResponseCall = mApiInterface.getPopularMovies(Constant.API_KEY);
            movieResponseCall.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                    Log.e(TAG, "onResponse: " + response.code());
                    Log.e(TAG, "onResponse: " + response.body().getResults());

                    moviesList = response.body().getResults();
                    adapter.setMoviesList(moviesList);

                    recyclerView.setAdapter(adapter);
                    recyclerView.scheduleLayoutAnimation();

                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {
                    dialog.dismiss();
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });


        }else {
        Toast.makeText(this, "No Internet !", Toast.LENGTH_SHORT).show();

        }
    }

//

    public void getTopRatedMovies(){

        if ((Constant.isConnectedToInternet(getApplicationContext()))) {
            dialog = new ProgressDialog(MainActivity.this);
            dialog.setMessage("please wait .....");
            dialog.show();


            mApiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<MovieResponse> movieResponseCall = mApiInterface.getTopRatedMovies(Constant.API_KEY);
            movieResponseCall.enqueue(new Callback<MovieResponse>() {
                @Override
                public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {

                  //  Log.e(TAG, "onResponse: " + response.code());
                  //  Log.e(TAG, "onResponse: " + response.body().getResults());

                    moviesList = response.body().getResults();
                    adapter.setMoviesList(moviesList);

                    //save to local database

                    removePopularToDatabase();
                    savePopularToDatabase(moviesList);

                    recyclerView.setAdapter(adapter);
                    recyclerView.scheduleLayoutAnimation();

                    dialog.dismiss();

                }

                @Override
                public void onFailure(Call<MovieResponse> call, Throwable t) {

                    dialog.dismiss();
                    Toast.makeText(MainActivity.this, "Bad internet ..Check your connection !!", Toast.LENGTH_LONG).show();
                    Log.e(TAG, "onFailure: " + t.getMessage());
                }
            });

        }else {

             if (firstTime==true){
                Toast.makeText(MainActivity.this, "No Internet ! and No local database STORE", Toast.LENGTH_LONG).show();

            }else {
                moviesList=getPopularFromDatabase();
                Toast.makeText(MainActivity.this, "No Internet ! ,, will load from local database", Toast.LENGTH_LONG).show();
                 adapter.setMoviesList(moviesList);
                  recyclerView.setAdapter(adapter);
                 recyclerView.scheduleLayoutAnimation();
             }
        }
    }



    @Override
    public void onClickItenOblect(int position, Movie movie) {
//        Toast.makeText(this, ""+movie.getTitle(), Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onClickItenOblect(int position) {
        Movie movie= moviesList.get(position);
        long id= movie.getId();
        goToDetailesActivity(id);

    }

    private void savePopularToDatabase(List<Movie> moviesList) {
        popularMovieDbBox.put(moviesList);
        Log.e(TAG, "savePopularToDatabase: sucessfully"  );
    }
    private List<Movie> getPopularFromDatabase() {
        if ( popularMovieDbBox.count()!=0){
            Log.e(TAG, "getPopularFromDatabase:    found data sucessfull from local database !!_!! ");
            return popularMovieDbBox.getAll();
        }else {
            Log.e(TAG, "getPopularFromDatabase:  empty"  );
            return null;
        }

    }

    private void removePopularToDatabase() {
        Log.e(TAG, "removePopularToDatabase: remove alod data sycessfully"  );
        popularMovieDbBox.removeAll();
    }



    private void goToDetailesActivity(long id) {
        Intent intent= new Intent(this,DetailsActivity.class);
        intent.putExtra(Constant.MOVIE_ID,id);

        if (Constant.isConnectedToInternet(this)){
            startActivity(intent);

        }else {
            Toast.makeText(this, "Check Your Network !!", Toast.LENGTH_SHORT).show();
        }
    }

}
//todo  set backgraound and scplash screen