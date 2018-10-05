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
import com.example.noone.alex_movies2019.model.MovieFav;
import com.example.noone.alex_movies2019.model.Trailer;
import com.example.noone.alex_movies2019.utils.Constant;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by NoOne on 10/1/2018.
 */

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailersViewholder> {

    private static final String TAG = "TrailersAdapter";
    ItemClickLitenerObject mItemClickLitenerObject;

    Context mContext;
    List<Trailer> trailerList;

    public TrailersAdapter(Context context, ItemClickLitenerObject mItemClickLitenerObject) {
        this.mItemClickLitenerObject = mItemClickLitenerObject;
        this.mContext = context;
        notifyDataSetChanged();
    }

    public List<Trailer> getTrailerList() {
        return trailerList;
    }

    public void setTrailerList(List<Trailer> trailerList) {
        this.trailerList = trailerList;
        notifyDataSetChanged();
    }

    @Override
    public TrailersViewholder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item_layout, parent, false);
        return new TrailersViewholder(view);

    }

    @Override
    public void onBindViewHolder(TrailersViewholder holder, int position) {

        String key = trailerList.get(position).getKey();

        String fullPathImage = Constant.makeThumbnailURL(key);

        Picasso.get().load(fullPathImage).placeholder(R.drawable.ic_play_arrow_black_24dp).error(R.drawable.ic_play_arrow_black_24dp).into(holder.imageTrailer);

        //todo add error check to picasso

    }

    @Override
    public int getItemCount() {
        return trailerList.size();
    }

    public class TrailersViewholder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.trailer_item_Image)
        ImageView imageTrailer;


        public TrailersViewholder(View itemView) {
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
