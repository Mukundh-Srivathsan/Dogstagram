package com.example.dogstagram.models;

import com.google.gson.annotations.SerializedName;

public class Vote {

    @SerializedName("message")
    private String message;

    public Vote(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
