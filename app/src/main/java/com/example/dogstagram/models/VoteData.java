package com.example.dogstagram.models;

import com.google.gson.annotations.SerializedName;

public class VoteData {

    @SerializedName("image_id")
    private String image_id;

    @SerializedName("value")
    private String value;

    public VoteData(String image_id, String value) {
        this.image_id = image_id;
        this.value = value;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getImage_id() {
        return image_id;
    }

    public String getValue() {
        return value;
    }
}
