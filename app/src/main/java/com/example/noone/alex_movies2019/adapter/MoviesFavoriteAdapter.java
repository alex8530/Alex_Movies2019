package com.example.noone.alex_movies2019.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.noone.alex_movies2019.R;
import com.example.noone.alex_movies2019.listener.ItemClickLitenerObject;
import com.example.noone.alex_movies2019.model.Movie;
import com.example.noone.alex_movies2019.model.MovieFav;
import com.example.noone.alex_movies2019.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NoOne on 9/30/2018.
 */

public class MoviesFavoriteAdapter extends RecyclerView.Adapter<MoviesFavoriteAdapter.MovieFavViewholder>{

    private static final String TAG = "MoviesFavoriteAdapter";
    ItemClickLitenerObject mItemClickLitenerObject;

    Context mContext;


    List<MovieFav> moviesFavList;

    public MoviesFavoriteAdapter(Context context , ItemClickLitenerObject mItemClickLitenerObject) {
        this.mItemClickLitenerObject=mItemClickLitenerObject;
        this.mContext = context;
        notifyDataSetChanged();
     }

    public void setMoviesFavList(List<MovieFav> moviesFavList) {
        this.moviesFavList = moviesFavList;
    }


    @Override
    public MovieFavViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
        return new MoviesFavoriteAdapter.MovieFavViewholder(view);
    }

    @Override
    public void onBindViewHolder(MovieFavViewholder holder, int position) {
        holder.tv_title.setText(moviesFavList.get(position).getTitle());

        String pathImage=moviesFavList.get(position).getPosterPath();


         String fullPath= Constant.BASE_IMAGE_URL+pathImage;

        Picasso.get().load(fullPath).placeholder(R.drawable.ic_play_arrow_black_24dp).error(R.drawable.ic_play_arrow_black_24dp).into(holder.imageViewPoster);
    }

    @Override
    public int getItemCount() {
        return moviesFavList.size();
    }

    public List<MovieFav> getMoviesFavList() {
        return moviesFavList;
    }



    public class MovieFavViewholder extends RecyclerView.ViewHolder implements View.OnClickListener{

        @BindView(R.id.menu_image_Poster)
        ImageView imageViewPoster;
        @BindView(R.id.menu_name)
        TextView tv_title;



        public MovieFavViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            mItemClickLitenerObject.onClickItemObject(getAdapterPosition());
        }
    }
}
