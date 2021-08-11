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

public class ImageAnalysisFragment extends Fragment {

    private RecyclerView recyclerView;
    private AnalysisAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    JsonPlaceFolderAPI jsonPlaceFolderAPI;

    List<Labels> labels = new ArrayList<>();

    String imageid;

    View v;

    private static final String TAG = "ImageAnalysisFragment";

    public ImageAnalysisFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_image_analysis, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        v=view;

        ImageAnalysisFragmentArgs args = ImageAnalysisFragmentArgs.fromBundle(getArguments());
        imageid= args.getImageID();

        Log.d(TAG, "image id: " + imageid);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thedogapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceFolderAPI = retrofit.create(JsonPlaceFolderAPI.class);


        recyclerView = v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(v.getContext());

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