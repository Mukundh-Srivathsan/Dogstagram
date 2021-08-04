package com.example.dogstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogstagram.Items;
import com.example.dogstagram.JsonPlaceFolderAPI;
import com.example.dogstagram.R;
import com.example.dogstagram.RecylerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BreedFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<Items> breedName = new ArrayList<>();
    private ArrayList<Items> imgUrl = new ArrayList<>();

    private JsonPlaceFolderAPI jsonPlaceFolderAPI;

    public BreedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_breed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thedogapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceFolderAPI = retrofit.create(JsonPlaceFolderAPI.class);

        getBreedList();

        for(int i=1; i<265; i++)
            getImageList(i);


        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new RecylerViewAdapter(breedName, imgUrl);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getBreedList()
    {
        Call<List<Items>> call = jsonPlaceFolderAPI.getBreed();

        call.enqueue(new Callback<List<Items>>() {
            @Override
            public void onResponse(Call<List<Items>> call, Response<List<Items>> response) {

                if(!response.isSuccessful())
                {
                    //breedName.add("Code: " + response.code());
                    return;
                }
                List<Items> items = response.body();

                for(Items item : items) {
                    breedName.add(new Items(item.getBreed()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Items>> call, Throwable t) {
                //breedName.add(t.getMessage());
            }
        });
    }

    private void getImageList(int i)
    {
        Call<List<Items>> call = jsonPlaceFolderAPI.getImages(i);

        call.enqueue(new Callback<List<Items>>() {
            @Override
            public void onResponse(Call<List<Items>> call, Response<List<Items>> response) {
                if(!response.isSuccessful())
                    return;

                List<Items> items = response.body();

                for(Items item : items) {
                    imgUrl.add(new Items(item.getImgUrl()));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Items>> call, Throwable t) {

            }
        });
    }
}