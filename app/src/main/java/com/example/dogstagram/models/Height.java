package com.example.dogstagram.models;

import com.google.gson.annotations.SerializedName;

public class Height {

    @SerializedName("metric")
    private String height;

    public String get_Height() {
        return height;
    }

    public Height(String height) {
        this.height = height;
    }
}
