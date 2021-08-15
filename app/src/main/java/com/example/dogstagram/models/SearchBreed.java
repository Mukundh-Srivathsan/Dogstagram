package com.example.dogstagram.models;

import com.google.gson.annotations.SerializedName;

public class SearchBreed {
    @SerializedName("name")
    private String breed;

    @SerializedName("id")
    private String id;

    @SerializedName("life_span")
    private String lifeSpan;

    @SerializedName("origin")
    private String origin;

    @SerializedName("temperament")
    private String temperament;

    @SerializedName("reference_image_id")
    private String imageid;

    @SerializedName("height")
    private Height height;

    @SerializedName("weight")
    private Weight weight;


    public SearchBreed(String imageid) {
        this.imageid = imageid;
    }


    public SearchBreed(Height height, Weight weight) {
        this.height = height;
        this.weight = weight;
    }

    public SearchBreed(String mBreed, String mid, String mlifeSpan, String morigin, String mtemperament) {
        breed = mBreed;
        id = mid;
        lifeSpan = mlifeSpan;
        origin = morigin;
        temperament = mtemperament;
    }

    public String getId() {
        return id;
    }

    public String getBreed() {
        return breed;
    }

    public String getLifeSpan() {
        return lifeSpan;
    }

    public String getOrigin() {
        return origin;
    }

    public String getTemperament() {
        return temperament;
    }

    public String getImageid() {
        return imageid;
    }

    public Height getHeight() {
        return height;
    }

    public Weight getWeight() {
        return weight;
    }

}
