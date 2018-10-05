package com.example.noone.alex_movies2019.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by NoOne on 9/30/2018.
 */

public class Trailer {
    @SerializedName("id")
    private String id;

    @SerializedName("key")
    private String key;

    public Trailer(String id, String key) {
        this.id = id;
        this.key = key;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }
    public void setKey(String key) {
        this.key = key;
    }
    @Override
    public String toString() {
        return "Trailer{" +
                "id='" + id + '\'' +
                ", key='" + key + '\'' +
                '}';
    }


}
