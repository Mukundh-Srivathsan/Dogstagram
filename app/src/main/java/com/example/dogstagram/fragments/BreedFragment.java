package com.example.dogstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.dogstagram.models.BreedName;
import com.example.dogstagram.models.ImageURL;
import com.example.dogstagram.JsonPlaceFolderAPI;
import com.example.dogstagram.R;
import com.example.dogstagram.adapters.PageRecylerViewAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BreedFragment extends Fragment{

    private RecyclerView recyclerView;
    private PageRecylerViewAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<BreedName> breedName = new ArrayList<>();
    private ArrayList<BreedName> units = new ArrayList<>();
    private ArrayList<ImageURL> imgUrl = new ArrayList<ImageURL>();

    private JsonPlaceFolderAPI jsonPlaceFolderAPI;

    private int i=0;
    int[] arr;

    NavController navController;

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
        
        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        adapter = new PageRecylerViewAdapter(this.getContext(), breedName, imgUrl, units);

        getBreedList();
        adapter.notifyDataSetChanged();



        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    private void getBreedList()
    {
        Call<List<BreedName>> call = jsonPlaceFolderAPI.getBreed();

        call.enqueue(new Callback<List<BreedName>>() {

            @Override
            public void onResponse(Call<List<BreedName>> call, Response<List<BreedName>> response) {

                if(!response.isSuccessful())
                {
                    //breedName.add("Code: " + response.code());
                    return;
                }
                List<BreedName> items = response.body();

                for(BreedName item : items) {
                    breedName.add(new BreedName(item.getBreed(), item.getId(),
                            item.getLifeSpan(), item.getOrigin(),item.getTemperament()));
                    units.add(new BreedName(item.getHeight(), item.getWeight()));
                    //adapter.notifyDataSetChanged();
                }

                arr = new int[breedName.size()];

                for (int i = 0; i < arr.length; i++)
                    arr[i] = Integer.parseInt(breedName.get(i).getId());

                getImageList(arr[i]);
            }

            @Override
            public void onFailure(Call<List<BreedName>> call, Throwable t) {
                //breedName.add(t.getMessage());
            }
        });
    }

    private void getImageList(int j)
    {
        Call<List<ImageURL>> call = jsonPlaceFolderAPI.getImages(j);

        call.enqueue(new Callback<List<ImageURL>>() {
            private static final String TAG = "BreedFragment";
            @Override
            public void onResponse(Call<List<ImageURL>> call, Response<List<ImageURL>> response) {
                if(!response.isSuccessful())
                    return;

                Log.d(TAG, "onResponse: Method");
                List<ImageURL> items = response.body();

                for(ImageURL item : items) {
                    Log.d(TAG, "onResponse:items");
                    imgUrl.add(new ImageURL(item.getImgUrl()));
                    adapter.notifyDataSetChanged();
                }

                i++;
                if(i<arr.length) {
                    getImageList(arr[i]);
                }
            }

            @Override
            public void onFailure(Call<List<ImageURL>> call, Throwable t) {

            }
        });
    }
}