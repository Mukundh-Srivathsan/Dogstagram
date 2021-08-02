package com.example.dogstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.Call;
import retrofit2.converter.gson.GsonConverterFactory;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "Base";

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<String> breedName = new ArrayList<>();

    private JsonPlaceFolderAPI jsonPlaceFolderAPI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Log.d(TAG, "onCreate: Started");

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thedogapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceFolderAPI = retrofit.create(JsonPlaceFolderAPI.class);

        getBreed();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new RecylerViewAdapter(breedName);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getBreed()
    {
        Call<List<Post>> call = jsonPlaceFolderAPI.getBreed();

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful())
                {
                    breedName.add("Code: " + response.code());
                    return;
                }
                List<Post> posts = response.body();

                for(Post post : posts)
                {
                    breedName.add(post.getBreed());
                }
            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                breedName.add(t.getMessage());
            }
        });
    }
}