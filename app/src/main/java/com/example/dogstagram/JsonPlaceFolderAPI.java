package com.example.dogstagram;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface JsonPlaceFolderAPI {

    @GET("breeds")
    Call<List<Items>> getBreed();
}
