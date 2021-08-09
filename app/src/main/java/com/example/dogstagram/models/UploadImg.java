package com.example.dogstagram.models;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class UploadImg {
    @SerializedName("id")
    private String id;

    public UploadImg(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
