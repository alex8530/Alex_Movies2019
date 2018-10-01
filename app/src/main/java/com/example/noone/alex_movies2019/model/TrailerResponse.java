package com.example.noone.alex_movies2019.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by NoOne on 10/1/2018.
 */

public class TrailerResponse {
    @SerializedName("results")
    ArrayList<Trailer> results;

    public TrailerResponse(ArrayList<Trailer> results) {
        this.results = results;
    }


    public ArrayList<Trailer> getResults() {
        return results;
    }

    public void setResults(ArrayList<Trailer> results) {
        this.results = results;
    }

    @Override
    public String toString() {
        return "TrailerResponse{" +
                "results=" + results +
                '}';
    }
}
