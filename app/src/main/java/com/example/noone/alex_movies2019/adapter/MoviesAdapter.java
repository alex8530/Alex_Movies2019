package com.example.noone.alex_movies2019.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noone.alex_movies2019.R;
import com.example.noone.alex_movies2019.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by NoOne on 9/8/2018.
 */

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewholder> {

    private static final String TAG = "MoviesAdapter";

    Context mContext;
    List<Movie> moviesList;

    public MoviesAdapter(Context context ) {
        this.mContext = context;
        notifyDataSetChanged();

    }
    public void setMoviesList(List<Movie> moviesList){
        this.moviesList=moviesList;
    }


    @Override
    public MovieViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MovieViewholder(view);

    }

    @Override
    public void onBindViewHolder(MovieViewholder holder, int position) {
        holder.tv_title.setText(moviesList.get(position).getTitle());

        String pathImage=moviesList.get(position).getPosterPath();


        //todo check if there is / or not !!!!
        String fullPath="http://image.tmdb.org/t/p/w185/"+pathImage;

        Log.e(TAG, "onBindViewHolder: "+fullPath );
        Picasso.get().load(fullPath).into(holder.imageViewPoster);

    }



    @Override
    public int getItemCount() {
        return moviesList.size();
    }



    public class MovieViewholder extends RecyclerView.ViewHolder{

        ImageView imageViewPoster;
        TextView tv_title;

        public MovieViewholder(View itemView) {
            super(itemView);
            imageViewPoster= itemView.findViewById(R.id.menu_image_Poster);
            tv_title = itemView.findViewById(R.id.menu_name);
        }


    }
}
