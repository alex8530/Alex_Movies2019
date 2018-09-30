package com.example.noone.alex_movies2019;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
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
import android.widget.Toast;

import com.example.noone.alex_movies2019.adapter.MoviesAdapter;
import com.example.noone.alex_movies2019.daos.MovieDao;
import com.example.noone.alex_movies2019.database.AppDatabase;
import com.example.noone.alex_movies2019.listener.ItemClickLitenerObject;
import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.MovieResponse;
import com.example.noone.alex_movies2019.repo.MovieRepository;
import com.example.noone.alex_movies2019.repo.Resource;
import com.example.noone.alex_movies2019.rest.ApiClient;
import com.example.noone.alex_movies2019.rest.ApiInterface;
import com.example.noone.alex_movies2019.utils.Connectivity;
import com.example.noone.alex_movies2019.utils.Constant;
import com.example.noone.alex_movies2019.viewmodels.MainViewModel;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements ItemClickLitenerObject {

    @BindView(R.id.rcycleView)
    RecyclerView recyclerView;
    List<Movie> moviesList;

    MoviesAdapter adapter;
    private static final String TAG = "TESTMainActivity";
    ProgressDialog dialog;

    Context mContext;


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

        setUpViewModelForTopRatedMovie();

    }

    private void setUpViewModelForTopRatedMovie() {

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


        return super.onOptionsItemSelected(item);

    }

//    private void getPopularMovies() {
//        //ADD THE THING HERE
////        if ( Connectivity.isConnected(mContext)&& Connectivity.isConnectedWifi(mContext)) {
////            dialog = new ProgressDialog(MainActivity.this);
////            dialog.setMessage("please wait .....");
////            dialog.show();
//
//
//        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<MovieResponse> movieResponseCall = mApiInterface.getPopularMovies(Constant.API_KEY);
//        movieResponseCall.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
////
//                moviesList = response.body().getResults();
//                adapter.setMoviesList(moviesList);
//
//                recyclerView.setAdapter(adapter);
//                recyclerView.scheduleLayoutAnimation();
//                adapter.notifyDataSetChanged();
////                    removePopularToDatabase();
////                    savePopularToDatabase(moviesList);
//
////                    dialog.dismiss();
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//                dialog.dismiss();
//                Log.e(TAG, "onFailure: " + t.getMessage());
//            }
//        });
//
//
////        }else {
//
////            if (getPopularFromDatabase()!=null){
////                if (getPopularFromDatabase().isEmpty()){
////                    //the list is  empty
////                    Log.e(TAG, "onCreate:list is empty and  null  and it is first time"  );
////                    firstTimePopularMovies=true;
////
////                }else {
////                    firstTimePopularMovies=false;
////                }
////
////
////                if (firstTimePopularMovies){
////
////                    //mean that the list is empty and no data in there
////                    Toast.makeText(MainActivity.this, "No Internet ! and No local database STORE", Toast.LENGTH_LONG).show();
////
////                }else {
////                    //mean that there are data in list and can be retraive from it
////                    Toast.makeText(MainActivity.this, "No Internet ! ,, will loaded Popular Movies from local Database", Toast.LENGTH_LONG).show();
////                    adapter.setMoviesList(getPopularFromDatabase());
////                    recyclerView.setAdapter(adapter);
////                    recyclerView.scheduleLayoutAnimation();
////                    adapter.notifyDataSetChanged();
////
////
////
////                }
////            }else {
////                Toast.makeText(MainActivity.this, "No Internet ! and No local database STORE", Toast.LENGTH_LONG).show();
////
////            }
//
//
////        }
//    }

//

//    public void getTopRatedMovies() {
//
//
////        if (Connectivity.isConnected(mContext)&& Connectivity.isConnectedWifi(mContext)) {
////            dialog = new ProgressDialog(MainActivity.this);
////            dialog.setMessage("please wait .....");
////            dialog.show();
//
//
//        mApiInterface = ApiClient.getClient().create(ApiInterface.class);
//        Call<MovieResponse> movieResponseCall = mApiInterface.getTopRatedMovies(Constant.API_KEY);
//        movieResponseCall.enqueue(new Callback<MovieResponse>() {
//            @Override
//            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
//
//                moviesList = response.body().getResults();
//                adapter.setMoviesList(moviesList);
//
//                //save to local database
////                    removeTopRatedToDatabase();
////                    saveTopRatedToDatabase(moviesList);
//
//                recyclerView.setAdapter(adapter);
//                recyclerView.scheduleLayoutAnimation();
//
////                    dialog.dismiss();
//
//            }
//
//            @Override
//            public void onFailure(Call<MovieResponse> call, Throwable t) {
//
//                dialog.dismiss();
//                Toast.makeText(MainActivity.this, "Bad internet ..Check your connection !!", Toast.LENGTH_LONG).show();
//                Log.e(TAG, "onFailure: " + t.getMessage());
//            }
//        });
//
////        }else {
////
////             if (getTopRatedFromDatabase()!=null){
////
////                if (getTopRatedFromDatabase().isEmpty()){
////                    //the list is  empty
////                    Log.e(TAG, "onCreate:list is empty and  null  and it is first time"  );
////                    firstTimeTopMovies=true;
////
////                }else {
////                    firstTimeTopMovies=false;
////                }
////
////                 if (firstTimeTopMovies){
////                     Toast.makeText(MainActivity.this, "No Internet ! and No local database STORE", Toast.LENGTH_LONG).show();
////
////                 }else {
////                     Toast.makeText(MainActivity.this, "No Internet ! ,, will load Top Movies from local database", Toast.LENGTH_LONG).show();
////
////                     adapter.setMoviesList(getTopRatedFromDatabase());
////                     recyclerView.setAdapter(adapter);
////                     recyclerView.scheduleLayoutAnimation();
////                     adapter.notifyDataSetChanged();
////                 }
////            }else {
////                 Toast.makeText(MainActivity.this, "No Internet ! and No local database STORE", Toast.LENGTH_LONG).show();
////
////             }
////
////
//
////        }
//    }




    @Override
    public void onClickItemObject(int position) {
        if (Connectivity.isConnected(mContext) && Connectivity.isConnectedWifi(mContext)) {

            Movie movie = moviesList.get(position);
            long id = movie.getId();
            goToDetailesActivity(id);


        } else {
            Toast.makeText(mContext, "No Internet", Toast.LENGTH_SHORT).show();
        }

    }

//    private void savePopularToDatabase(List<Movie> moviesList) {
//        ArrayList<Movie>  list= new ArrayList<>();
//        for (Movie movie: moviesList){
//            movie.setType(0);//0 for Popular movies
//            list.add(movie);
//        }
//
//        Log.e(TAG, "savePopularToDatabase: "+list.toString() );
//        popularDbBox.put(list);
//        Log.e(TAG, "savePopularToDatabase: sucessfully"  );
//    }
//    private List<Movie> getPopularFromDatabase() {
//        if ( popularDbBox.count()!=0){
//            Log.e(TAG, "getPopularFromDatabase:    found data sucessfull from local database !!_!! ");
//            List<Movie> movieList =  popularDbBox.query().equal(Movie_.type,0).build().find();
//            Log.e(TAG, "popularDbBox: "+popularDbBox.getAll().toString() );
//            return movieList;
//        }else {
//            Log.e(TAG, "getPopularFromDatabase:  empty"  );
//            return null;
//        }
//
//    }


//    private void saveTopRatedToDatabase(List<Movie> moviesList) {
//
//        ArrayList<Movie>  list= new ArrayList<>();
//        for (Movie movie: moviesList){
//            movie.setType(1);//0 for Popular movies
//            list.add(movie);
//
//        }
//
//        Log.e(TAG, "saveTopRatedToDatabase: list : "+ list.toString() );
//         topRateDbBox.put(list);
//        Log.e(TAG, "saveTopRatedToDatabase: sucessfully"  );
//    }


//    private List<Movie> getTopRatedFromDatabase() {
//
//        if ( topRateDbBox.count()!=0){
//            Log.e(TAG, "getTopRatedFromDatabase:    found data sucessfull from local database !!_!! ");
//             List<Movie> movieList =  topRateDbBox.query().equal(Movie_.type,1).build().find();
//            Log.e(TAG, " getTopRatedFromDatabase: After qeury"+movieList );
//            return movieList;
//        }else {
//            Log.e(TAG, "getTopRatedFromDatabase:  empty"  );
//            return null;
//        }
//
//    }


    private void goToDetailesActivity(long id) {
        Intent intent = new Intent(this, DetailsActivity.class);
        intent.putExtra(Constant.MOVIE_ID, id);

        if (Connectivity.isConnected(mContext) && Connectivity.isConnectedWifi(mContext)) {
            startActivity(intent);

        } else {
            Toast.makeText(this, "Check Your Network !!", Toast.LENGTH_SHORT).show();
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
}
//todo  set backgraound