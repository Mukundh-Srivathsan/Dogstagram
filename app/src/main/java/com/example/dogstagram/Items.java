package com.example.dogstagram;

import android.net.Uri;

import com.google.gson.annotations.SerializedName;

public class Items {

    @SerializedName("name")
    private String breed;

    @SerializedName("url")
    private Uri imgUrl;

    public Items(String mBreed) {
        breed = mBreed;
    }

    public Items(Uri mimgUrl)
    {
        imgUrl = mimgUrl;
    }

    public String getBreed() {
        return breed;
    }

    public Uri getImgUrl()
    {
        return imgUrl;
    }
}
