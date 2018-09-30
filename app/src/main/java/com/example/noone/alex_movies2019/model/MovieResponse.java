package com.example.noone.alex_movies2019.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by NoOne on 9/8/2018.
 */

public class MovieResponse {

    @SerializedName("page")
    private int page;


    @SerializedName("total_results")
    private String total_results;


    @SerializedName("total_pages")
    private int total_pages;

        @SerializedName("results")
    private List<Movie> results;


    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public String getTotal_results() {
        return total_results;
    }

    public void setTotal_results(String total_results) {
        this.total_results = total_results;
    }

    public int getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(int total_pages) {
        this.total_pages = total_pages;
    }

    public List<Movie> getResults() {
        return results;
    }

    public void setResults(List<Movie> results) {
        this.results = results;
    }
}
