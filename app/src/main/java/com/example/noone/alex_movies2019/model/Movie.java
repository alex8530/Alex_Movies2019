package com.example.noone.alex_movies2019.model;

import com.google.gson.annotations.SerializedName;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * Created by NoOne on 9/8/2018.
 */

@Entity
public class Movie {

    @SerializedName("overview")
    private String overview;

    @SerializedName("title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("vote_average")
    private Double voteAvarage;

    @SerializedName("release_date")
    private String releaseDate  ;


    @Id(assignable = true)
    @SerializedName("id")
    private long id ;



    public Movie(String overview, String title, String posterPath, Double voteAvarage, String releaseDate, long id) {
        this.overview = overview;
        this.title = title;
        this.posterPath = posterPath;
        this.voteAvarage = voteAvarage;
        this.releaseDate = releaseDate;
        this.id = id;
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

    public Double getVoteAvarage() {
        return voteAvarage;
    }

    public void setVoteAvarage(Double voteAvarage) {
        this.voteAvarage = voteAvarage;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "overview='" + overview + '\'' +
                ", title='" + title + '\'' +
                ", posterPath='" + posterPath + '\'' +
                ", voteAvarage=" + voteAvarage +
                ", releaseDate='" + releaseDate + '\'' +
                ", id=" + id +
                '}';
    }



}
