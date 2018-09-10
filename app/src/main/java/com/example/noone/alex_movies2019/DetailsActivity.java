package com.example.noone.alex_movies2019;

import android.app.ProgressDialog;
import android.content.Context;
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
import com.example.noone.alex_movies2019.utils.Connectivity;
import com.example.noone.alex_movies2019.utils.Constant;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity {
    private static final String TAG = "TESTDetailsActivity";

    @BindView(R.id.details_title) TextView details_title;
    @BindView(R.id.details_releaseDate) TextView details_releaseDate;
    @BindView(R.id.details_voteAverage) TextView details_voteAverage;
    @BindView(R.id.details_overView) TextView  details_overView;
    @BindView(R.id.details_image)ImageView details_image;

    long idMovie;
    ApiInterface mApiInterface;
    ProgressDialog dialog;
    Context mContext;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
         mContext=this;
         ButterKnife.bind(this);

        //get intent
        Intent intent = getIntent();
        if (intent!=null){
            if (intent.hasExtra(Constant.MOVIE_ID)){
                idMovie= intent.getLongExtra(Constant.MOVIE_ID,0);
            }
        }

        //getDetails from api
        getDetails(idMovie);


    }


    private void getDetails(long idMovie) {

        if (Connectivity.isConnected(mContext)&& Connectivity.isConnectedWifi(mContext)) {
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
