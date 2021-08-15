package com.example.dogstagram;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogstagram.JsonPlaceFolderAPI;
import com.example.dogstagram.R;
import com.example.dogstagram.adapters.AnalysisAdapter;
import com.example.dogstagram.models.ImageAnalysis;
import com.example.dogstagram.models.Labels;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImageAnalysisActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private AnalysisAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    JsonPlaceFolderAPI jsonPlaceFolderAPI;

    List<Labels> labels = new ArrayList<>();

    String imageid;

    private static final String TAG = "ImageAnalysisActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_analysis);

        Intent intent = getIntent();
        imageid = intent.getStringExtra("image id");

        Log.d(TAG, "image id: " + imageid);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thedogapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceFolderAPI = retrofit.create(JsonPlaceFolderAPI.class);


        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);

        getAnalysis();

    }


    private void getAnalysis()
    {
        Call<List<ImageAnalysis>> call = jsonPlaceFolderAPI.getAnalysis(imageid);

        call.enqueue(new Callback<List<ImageAnalysis>>() {
            @Override
            public void onResponse(Call<List<ImageAnalysis>> call, Response<List<ImageAnalysis>> response) {
                if (!response.isSuccessful()) {
                    Log.d(TAG, "Analysis Unsuccessful");
                    return;
                }

                Log.d(TAG, "Analysis Successful");

                List<ImageAnalysis> items = response.body();
                ImageAnalysis item = items.get(0);

                labels = item.getLabels();

                adapter = new AnalysisAdapter(labels);

                adapter.notifyDataSetChanged();

                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<ImageAnalysis>> call, Throwable t) {

                Log.d(TAG, "Analysis Failed:" + t.getMessage());

            }
        });
    }
}