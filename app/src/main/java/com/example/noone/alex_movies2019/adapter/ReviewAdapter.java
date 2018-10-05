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
import com.example.noone.alex_movies2019.model.Review;
import com.example.noone.alex_movies2019.model.Trailer;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NoOne on 10/1/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewholder>{

    private static final String TAG = "ReviewAdapter";


    Context mContext;
    List<Review> mReviewsList;

    public ReviewAdapter(Context context ) {
         this.mContext = context;
        notifyDataSetChanged();
    }

    public List<Review> getReviewsList() {
        return mReviewsList;
    }

    public void setReviewsList(List<Review> reviewsList) {
        this.mReviewsList = reviewsList;
    }

    @Override
    public ReviewViewholder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item_layout, parent, false);
        return new ReviewViewholder(view);
    }

    @Override
    public void onBindViewHolder(ReviewViewholder holder, int position) {

        String auther=mReviewsList.get(position).getAuthor();
        String content=mReviewsList.get(position).getContent();
        holder.author.setText(auther);
        holder.content.setText(content);
    }

    @Override
    public int getItemCount() {
        return mReviewsList.size();
    }

    public class ReviewViewholder extends RecyclerView.ViewHolder   {

        @BindView(R.id.review_item_author)
        TextView  author;
        @BindView(R.id.review_item_content)
        TextView  content;


        public ReviewViewholder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
         }




    }

}
