package com.example.noone.alex_movies2019;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;
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
import com.example.noone.alex_movies2019.viewmodels.DetailesViewModel;
import com.example.noone.alex_movies2019.viewmodels.DetailesViewModelFactory;
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
    /*

    You should note that the data install from local database forever here , because in the main activity we download all movies, so here , only thing that we should do is get that movie by id !! Alex !!

     */

    /*

    And You should note also that we donot need for use save instance method , becaose the id is came from intent ,
    so alsways whatever the zctivity rotate , it will exists for ever in the intent!!
     */
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
        setUpViewModel();
    }

    private void setUpViewModel() {

        DetailesViewModelFactory factory = new DetailesViewModelFactory(getApplication(),idMovie);
        final DetailesViewModel viewModel
                = ViewModelProviders.of(this, factory).get(DetailesViewModel.class);

        viewModel.getMovieLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                Log.d(TAG, "onChanged: Detials");
                bindDetailsWithUi(movie);

            }
        });
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
