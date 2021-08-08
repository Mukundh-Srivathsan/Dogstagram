package com.example.dogstagram.models;

import com.google.gson.annotations.SerializedName;

public class Weight {

    @SerializedName("metric")
    private String weight;

    public String get_Weight() {
        return weight;
    }

    public Weight(String weight) {
        this.weight = weight;
    }
}
