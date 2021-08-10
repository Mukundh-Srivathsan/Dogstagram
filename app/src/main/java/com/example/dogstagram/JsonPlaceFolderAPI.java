package com.example.dogstagram;

import com.example.dogstagram.models.BreedName;
import com.example.dogstagram.models.ImageAnalysis;
import com.example.dogstagram.models.ImageURL;
import com.example.dogstagram.models.UploadImg;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceFolderAPI {

    @GET("breeds")
    Call<List<BreedName>> getBreed();

    @GET("images/search")
    Call<List<ImageURL>> getImages(@Query("breed_ids") int id);

    @GET("images/{image_id}/analysis")
    @Headers("x-api-key:6c94689e-0a22-4664-8127-6dc32130b4ae")
    Call<List<ImageAnalysis>> getAnalysis(@Path("image_id") String imageid);

    @Multipart
    @POST("images/upload")
    @Headers("x-api-key:6c94689e-0a22-4664-8127-6dc32130b4ae")
    Call<UploadImg> uploadImg(@Part MultipartBody.Part photo);




}
