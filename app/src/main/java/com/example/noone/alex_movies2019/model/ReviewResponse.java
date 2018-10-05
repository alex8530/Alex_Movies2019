package com.example.noone.alex_movies2019.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by NoOne on 10/1/2018.
 */

public class ReviewResponse {

    @SerializedName("results")
    ArrayList<Review> results;

    public ReviewResponse(ArrayList<Review> results) {
        this.results = results;
    }

    public ArrayList<Review> getResults() {
        return results;
    }

    public void setResults(ArrayList<Review> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "ReviewResponse{" +
                "results=" + results +
                '}';
    }
}
