package com.example.dogstagram.json_classes;

import com.google.gson.annotations.SerializedName;

public class BreedName {

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

    @SerializedName("height")
    private Height height;

    @SerializedName("weight")
    private Weight weight;

    public BreedName(Height height, Weight weight) {
        this.height = height;
        this.weight = weight;
    }

    public BreedName(String mBreed, String mid, String mlifeSpan, String morigin, String mtemperament) {
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

    public Height getHeight() {
        return height;
    }

    public Weight getWeight() {
        return weight;
    }
}
