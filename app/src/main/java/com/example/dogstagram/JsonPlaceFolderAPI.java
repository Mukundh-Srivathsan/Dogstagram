package com.example.dogstagram;

import com.example.dogstagram.models.BreedName;
import com.example.dogstagram.models.ImageAnalysis;
import com.example.dogstagram.models.ImageURL;
import com.example.dogstagram.models.SearchBreed;
import com.example.dogstagram.models.UploadImg;
import com.example.dogstagram.models.Vote;
import com.example.dogstagram.models.VoteData;

import java.util.Calendar;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonPlaceFolderAPI {

    @GET("breeds")
    @Headers("x-api-key:<API_KEY>")
    Call<List<BreedName>> getBreed(@Query("limit") int limit, @Query("page") int page);


    @GET("images/search")
    @Headers("x-api-key:<API_KEY>")
    Call<List<ImageURL>> getImages(@Query("breed_ids") int id);


    @GET("images/{image_id}/analysis")
    @Headers("x-api-key:<API_KEY>")
    Call<List<ImageAnalysis>> getAnalysis(@Path("image_id") String imageid);


    @GET("breeds/search")
    @Headers("x-api-key:<API_KEY>")
    Call<List<SearchBreed>> getSearchResults(@Query("q") String q);


    @Multipart
    @POST("images/upload")
    @Headers("x-api-key:<API_KEY>")
    Call<UploadImg> uploadImg(@Part MultipartBody.Part photo);


    @POST("votes")
    @Headers("x-api-key:<API_KEY>")
    Call<Vote> addVote(@Body VoteData voteData);

}
