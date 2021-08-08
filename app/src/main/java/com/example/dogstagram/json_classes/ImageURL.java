package com.example.dogstagram.json_classes;

import com.google.gson.annotations.SerializedName;

public class ImageURL {

    @SerializedName("url")
    private String imgUrl;

    public ImageURL(String mimgUrl)
    {
        imgUrl = mimgUrl;
    }

    public String getImgUrl()
    {
        return imgUrl;
    }
}
