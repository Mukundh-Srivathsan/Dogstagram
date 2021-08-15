package com.example.dogstagram;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.dogstagram.adapters.BreedNamesAdapter;
import com.example.dogstagram.adapters.SearchAdapter;
import com.example.dogstagram.models.BreedName;
import com.example.dogstagram.models.ImageURL;
import com.example.dogstagram.models.SearchBreed;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private SearchAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    String q;

    private ArrayList<SearchBreed> breedNames = new ArrayList<>();
    private ArrayList<SearchBreed> units = new ArrayList<>();
    private ArrayList<SearchBreed> images = new ArrayList<>();

    private JsonPlaceFolderAPI jsonPlaceFolderAPI;

    private static final String TAG = "SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Log.d(TAG, "onCreate: Started");

        Intent intent = getIntent();

        q = intent.getStringExtra("q");

        Log.d(TAG, "Query: " + q);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thedogapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceFolderAPI = retrofit.create(JsonPlaceFolderAPI.class);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        adapter = new SearchAdapter(this, breedNames, images, units);

        getBreedId();

        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getBreedId() {
        Call<List<SearchBreed>> call = jsonPlaceFolderAPI.getSearchResults(q);

        call.enqueue(new Callback<List<SearchBreed>>() {
            @Override
            public void onResponse(Call<List<SearchBreed>> call, Response<List<SearchBreed>> response) {

                if (!response.isSuccessful()) {
                    Log.d(TAG, "Search Unsuccessful");
                    return;
                }

                Log.d(TAG, "Search Successful");

                List<SearchBreed> items = response.body();

                for (SearchBreed item : items) {

                    Log.d(TAG, "Id: " + item.getId());

                    Log.d(TAG, "Breed: " + item.getBreed());

                    Log.d(TAG, "Image: " + item.getImageid());

                    breedNames.add(new SearchBreed(item.getBreed(), item.getId(),
                            item.getLifeSpan(), item.getOrigin(), item.getTemperament()));

                    units.add(new SearchBreed(item.getHeight(), item.getWeight()));

                    images.add(new SearchBreed("https://cdn2.thedogapi.com/images/" + item.getImageid() + ".jpg"));
                }
            }

            @Override
            public void onFailure(Call<List<SearchBreed>> call, Throwable t) {
                Log.d(TAG, "Search Failed");

                return;
            }
        });
    }
}