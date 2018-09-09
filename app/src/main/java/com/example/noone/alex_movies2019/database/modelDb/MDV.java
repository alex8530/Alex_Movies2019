package com.example.noone.alex_movies2019.database.modelDb;

import com.example.noone.alex_movies2019.model.Movie;
import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by NoOne on 9/8/2018.
 */

@Entity
public class MDV {


    public MDV(Movie movie) {
        index=0;
        this.overview = movie.getOverview();
        this.title = movie.getTitle();
        this.posterPath = movie.getPosterPath();
        this.vote_average = movie.getVoteAvarage();
        this.releaseDate = movie.getReleaseDate();
        this.id = movie.getId();
    }






    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public Double getVote_average() {
        return vote_average;
    }

    public void setVote_average(Double vote_average) {
        this.vote_average = vote_average;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    private String overview;


    private String title;


    private String posterPath;

    private Double vote_average;


    private String releaseDate  ;


    private Integer id ;


    @Id
    private long index ;





    @Override
    public String toString() {
        return "MDV{" +
                "overview='" + overview + '\'' +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", vote_average=" + vote_average +
                ", releaseDate='" + releaseDate + '\'' +
                ", id=" + id +
                '}';
    }


}
