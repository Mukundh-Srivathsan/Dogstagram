package com.example.dogstagram;

import com.google.gson.annotations.SerializedName;

public class Items {

    @SerializedName("name")
    private String breed;

    public Items(String mBreed) {
        breed = mBreed;
    }

    public String getBreed() {
        return breed;
    }
}
