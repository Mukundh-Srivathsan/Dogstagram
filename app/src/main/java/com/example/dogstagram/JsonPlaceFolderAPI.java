package com.example.dogstagram;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface JsonPlaceFolderAPI {

    @GET("breeds")
    Call<List<Items>> getBreed();

    @GET("images/search")
    Call<List<Items>> getImages(@Query("breed_ids") int id);

}
