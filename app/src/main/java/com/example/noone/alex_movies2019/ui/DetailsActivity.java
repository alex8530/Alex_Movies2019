package com.example.noone.alex_movies2019.ui;

import android.app.ProgressDialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.noone.alex_movies2019.AppExecutors;
import com.example.noone.alex_movies2019.R;
import com.example.noone.alex_movies2019.adapter.ReviewAdapter;
import com.example.noone.alex_movies2019.adapter.TrailersAdapter;
import com.example.noone.alex_movies2019.database.AppDatabase;
import com.example.noone.alex_movies2019.listener.ItemClickLitenerObject;
import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.MovieFav;
import com.example.noone.alex_movies2019.model.MovieResponse;
import com.example.noone.alex_movies2019.model.Review;
import com.example.noone.alex_movies2019.model.Trailer;
import com.example.noone.alex_movies2019.model.TrailerResponse;
import com.example.noone.alex_movies2019.repo.MovieRepository;
import com.example.noone.alex_movies2019.rest.ApiClient;
import com.example.noone.alex_movies2019.rest.ApiInterface;
import com.example.noone.alex_movies2019.utils.Connectivity;
import com.example.noone.alex_movies2019.utils.Constant;
import com.example.noone.alex_movies2019.viewmodels.DetailesViewModel;
import com.example.noone.alex_movies2019.viewmodels.DetailesViewModelFactory;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailsActivity extends AppCompatActivity implements ItemClickLitenerObject {
    private static final String TAG = "TESTDetailsActivity";

    @BindView(R.id.details_title)
    TextView details_title;
    @BindView(R.id.details_releaseDate)
    TextView details_releaseDate;
    @BindView(R.id.details_voteAverage)
    TextView details_voteAverage;
    @BindView(R.id.details_overView)
    TextView details_overView;
    @BindView(R.id.details_image)
    ImageView details_image;
    @BindView(R.id.details_ratingBar)
    RatingBar details_ratingBar;

    @BindView(R.id.details_fav)
    ImageView details_fav;

    @BindView(R.id.details_recycle_trailers)
    RecyclerView details_recycle_trailers;

    @BindView(R.id.details_recycle_reviews)
    RecyclerView details_recycle_reviews;

    long idMovie;
    String title;
    ProgressDialog dialog;
    Context mContext;
    Movie currentMovie;

    private String STATE = "";
    private String INSERT_STATE = "INSERT";
    private String DELETE_STATE = "DELETE";
    TrailersAdapter trailersAdapter;
    ReviewAdapter mReviewAdapter ;
    ArrayList<Review> mReviewArrayList;
    ArrayList<Trailer> mTrailerArrayList;


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
        mContext = this;
        ButterKnife.bind(this);

        //toolbar
        Toolbar toolbar = findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);

        //get intent
        Intent intent = getIntent();
        if (intent != null) {
            if (intent.hasExtra(Constant.MOVIE_ID)) {
                idMovie = intent.getLongExtra(Constant.MOVIE_ID, 0);
            }
            if (intent.hasExtra(Constant.MOVIE_TITLE)) {
                  title = intent.getStringExtra(Constant.MOVIE_TITLE);
                toolbar.setTitle(title);
            }
        }


        //init  recycle_trailers
        details_recycle_trailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        details_recycle_trailers.setHasFixedSize(true);
        trailersAdapter = new TrailersAdapter(this, this);


        //init  recycle_Reviews
        details_recycle_reviews.setLayoutManager(new LinearLayoutManager(this));
        details_recycle_reviews.setHasFixedSize(true);
         mReviewAdapter = new ReviewAdapter(this );




        //getDetails from api
        getDetails(idMovie);


        details_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveToFavorite();
            }
        });

        initAnimation();



    }

    private void initAnimation() {
        Explode enterTransition = new Explode();
        enterTransition.setDuration(1000);
        getWindow().setEnterTransition(enterTransition);
    }


    private void saveToFavorite() {

        //we do not need to insert idDb because it will generate automaticly
        String title = currentMovie.getTitle();
        long id = currentMovie.getId();
        String pathImage = currentMovie.getPosterPath();
        String overview = currentMovie.getOverview();
        double voteAvarage = currentMovie.getVoteAvarage();
        String releaseDate = currentMovie.getReleaseDate();
        MovieFav movieFav = new MovieFav(id, title, pathImage, overview, voteAvarage, releaseDate);


        if (STATE.equals(INSERT_STATE)) {

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: " + title + id);

                    AppDatabase.getInstance(getApplicationContext()).movieFavDao().insertMovieFavorite(movieFav);
                    Log.d(TAG, "run: insert!!!!!!!!");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            details_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
//                            STATE=DELETE_STATE;

                        }
                    });

                }
            });

        }
        if (STATE.equals(DELETE_STATE)) {
            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "run: " + title + id);

                    AppDatabase.getInstance(getApplicationContext()).movieFavDao().deleteMovieFav(movieFav);
                    Log.d(TAG, "run: delete!!!!!!!!");

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            details_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);
//                            STATE=INSERT_STATE;
                        }
                    });

                }
            });


        }

    }


    private void getDetails(long idMovie) {
        setUpViewModel();

    }


    private void setUpViewModel() {

        //get object from view model
        Log.d(TAG, "setUpViewModel: ");
        DetailesViewModelFactory factory = new DetailesViewModelFactory(getApplication(), idMovie);
        final DetailesViewModel viewModel
                = ViewModelProviders.of(this, factory).get(DetailesViewModel.class);



        viewModel.getMovieLiveData().observe(this, new Observer<Movie>() {
            @Override
            public void onChanged(@Nullable Movie movie) {
                Log.d(TAG, "onChanged: setUpViewModel");
                currentMovie = movie;
                bindDetailsWithUi(movie);
                editFavImage();

            }
        });

        viewModel.getTrailersLiveData().observe(this, new Observer<ArrayList<Trailer>>() {
            //in this viewmodel, only will cash the dataFor keep up againse rotate  , and not save it to Room
            //so here we do not need to stor data into database becaouse trailer always open by youtube so
            //    we always reqiue from remote !!but i can use cash as viewmodel as i did here
            @Override
            public void onChanged(@Nullable ArrayList<Trailer> trailers) {

                mTrailerArrayList=trailers;
                trailersAdapter.setTrailerList(mTrailerArrayList);
                details_recycle_trailers.setAdapter(trailersAdapter);
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(
                        details_recycle_trailers.getContext(),
                        R.anim.layout_full_down);

                Log.d(TAG, "onChangedssssssssssssss1 : " +trailers.toString());
                details_recycle_trailers.setLayoutAnimation(animationController);
            }
        });

        viewModel.getReviewLiveData().observe(this, new Observer<ArrayList<Review>>() {
            //Note
            //i dont save the review in Roon database only in the cash
            @Override
            public void onChanged(@Nullable ArrayList<Review> reviews) {
                mReviewArrayList=reviews;
                mReviewAdapter.setReviewsList(mReviewArrayList);
                details_recycle_reviews.setAdapter(mReviewAdapter);
                LayoutAnimationController animationController = AnimationUtils.loadLayoutAnimation(
                        details_recycle_reviews.getContext(),
                        R.anim.layout_full_down);

                Log.d(TAG, "onChangedssssssssssssss2: "+reviews.toString());

                details_recycle_reviews.setLayoutAnimation(animationController);
            }
        });


    }

    private void editFavImage() {
        //todo //i can use view model here

        AppDatabase.getInstance(getApplicationContext())
                .movieFavDao().getMovieFav(currentMovie.getId()).observe(DetailsActivity.this, new Observer<List<MovieFav>>() {
            @Override
            public void onChanged(@Nullable List<MovieFav> list) {
                if (list != null && !list.isEmpty()) {
                    Log.d(TAG, "setUpFavoriteImage: " + list.size());
                    Log.d(TAG, "setUpFavoriteImage: " + list.toString());

                    //that mean the movie is here and we need to delete it BUT SHOW THTE ICON
                    STATE = DELETE_STATE;
                    details_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
                } else {
                    Log.d(TAG, "editFavImage: Empty");
                    STATE = INSERT_STATE;
                    details_fav.setImageResource(R.drawable.ic_favorite_border_black_24dp);

                }
            }
        });

    }

    private void bindDetailsWithUi(Movie movie) {
        details_title.setText(movie.getTitle());
        details_releaseDate.setText(movie.getReleaseDate());
        details_voteAverage.setText(String.valueOf(movie.getVoteAvarage()));
        details_overView.setText(movie.getOverview());
        details_ratingBar.setMax(10);
        String floatString = String.valueOf(movie.getVoteAvarage());
        details_ratingBar.setRating((Float.valueOf(floatString))/2f);


        String fullPath = Constant.BASE_IMAGE_URL + movie.getPosterPath();

        Picasso.get()
                .load(fullPath)
                .placeholder(R.mipmap.ic_launcher)
                .error(R.mipmap.ic_launcher_round)
                .into(details_image);




    }


    @Override
    public void onClickItemObject(int position) {
        String url = "https://www.youtube.com/watch?v=".concat(mTrailerArrayList.get(position).getKey());
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.detailes_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();


        if (itemId == R.id.details_menu_share) {
            //share the firest trailes only
            String url = "https://www.youtube.com/watch?v=".concat(mTrailerArrayList.get(0).getKey());
 
            shareTextUrl(url);
            return true;
        }


        return super.onOptionsItemSelected(item);

    }
    private void shareTextUrl(String url) {
        Intent share = new Intent(android.content.Intent.ACTION_SEND);
        share.setType("text/plain");
        share.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

        share.putExtra(Intent.EXTRA_SUBJECT, title);
        share.putExtra(Intent.EXTRA_TEXT, url);

        startActivity(Intent.createChooser(share, "Share link!"));
    }

}
