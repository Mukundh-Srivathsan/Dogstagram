package com.example.dogstagram;

import com.example.dogstagram.json_classes.BreedName;
import com.example.dogstagram.json_classes.ImageURL;
import com.example.dogstagram.json_classes.UploadImg;

import java.io.File;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface JsonPlaceFolderAPI {

    @GET("breeds")
    Call<List<BreedName>> getBreed();

    @GET("images/search")
    Call<List<ImageURL>> getImages(@Query("breed_ids") int id);

    @POST("images/upload")
    Call<UploadImg> uploadImg(@Body UploadImg uploadImg);

}
