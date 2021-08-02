package com.example.dogstagram;

import com.google.gson.annotations.SerializedName;

public class Post {

    @SerializedName("attach_breed")
    private String breed;

    public String getBreed() {
        return breed;
    }
}
