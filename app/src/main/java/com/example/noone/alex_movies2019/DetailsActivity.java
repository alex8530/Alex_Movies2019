package com.example.noone.alex_movies2019;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.MovieResponse;
import com.example.noone.alex_movies2019.rest.ApiClient;
import com.example.noone.alex_movies2019.rest.ApiInterface;
import com.example.noone.alex_movies2019.utils.Constant;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "TESTDetailsActivity";
    TextView details_title,details_releaseDate,details_voteAverage,details_overView;
    ImageView details_image;
    long idMovie;
    ApiInterface mApiInterface;
    ProgressDialog dialog;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);


        //get intent
        Intent intent = getIntent();
        if (intent!=null){
            if (intent.hasExtra(Constant.MOVIE_ID)){
                idMovie= intent.getLongExtra(Constant.MOVIE_ID,0);
            }
        }

        initUi();


        //getDetails from api
        getDetails(idMovie);


    }

    private void initUi() {
        details_title =findViewById(R.id.details_title);
        details_releaseDate =findViewById(R.id.details_releaseDate);
        details_voteAverage =findViewById(R.id.details_voteAverage);
        details_overView =findViewById(R.id.details_overView);
        details_image=findViewById(R.id.details_image);
    }

    private void getDetails(long idMovie) {

        if (Constant.isConnectedToInternet(getApplicationContext())) {
            dialog = new ProgressDialog(DetailsActivity.this);
            dialog.setMessage("please wait .....");
            dialog.show();


            Log.e(TAG, "getDetails: " + idMovie);
            mApiInterface = ApiClient.getClient().create(ApiInterface.class);
            Call<Movie> movieResponseCall = mApiInterface.getMovieDetails(idMovie, Constant.API_KEY);
            movieResponseCall.enqueue(new Callback<Movie>() {
                @Override
                public void onResponse(Call<Movie> call, Response<Movie> response) {
                    Movie movie = response.body();
                    bindDetailsWithUi(movie);

                    dialog.dismiss();
                }

                @Override
                public void onFailure(Call<Movie> call, Throwable t) {
                    Log.e(TAG, "onFailure: " + t.getMessage());
                    dialog.dismiss();
                }
            });

        }else {
            Toast.makeText(this, "No Internet !", Toast.LENGTH_SHORT).show();

        }
    }

    private void bindDetailsWithUi(Movie movie) {
        details_title.setText(movie.getTitle());
        details_releaseDate.setText(movie.getReleaseDate());
        details_voteAverage.setText(String.valueOf(movie.getVoteAvarage()));
        details_overView.setText(movie.getOverview());

        String fullPath= Constant.BASE_IMAGE_URL+movie.getPosterPath();

                Picasso.get()
                .load(fullPath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(details_image);

    }


}
