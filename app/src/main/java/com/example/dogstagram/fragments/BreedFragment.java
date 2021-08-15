package com.example.dogstagram.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;

import com.example.dogstagram.SearchActivity;
import com.example.dogstagram.models.BreedName;
import com.example.dogstagram.models.ImageURL;
import com.example.dogstagram.JsonPlaceFolderAPI;
import com.example.dogstagram.R;
import com.example.dogstagram.adapters.BreedNamesAdapter;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;

public class BreedFragment extends Fragment{

    private RecyclerView recyclerView;
    private BreedNamesAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private ArrayList<BreedName> breedName = new ArrayList<>();
    private ArrayList<BreedName> units = new ArrayList<>();
    private ArrayList<ImageURL> imgUrl = new ArrayList<ImageURL>();

    private JsonPlaceFolderAPI jsonPlaceFolderAPI;

    private int page = 0;
    final private int LIMIT = 5;

    private boolean isLastPage = false;

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
        adapter = new BreedNamesAdapter(this.getActivity(), breedName, imgUrl, units);

        getBreedList();

        adapter.notifyDataSetChanged();

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);

        EditText searchBar = view.findViewById(R.id.searchbar);
        ImageButton searchBtn = view.findViewById(R.id.searchbtn);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Query Succesful");

                String query = searchBar.getText().toString();

                Intent intent = new Intent(requireContext(), SearchActivity.class);
                intent.putExtra("q", query);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);

        //inflater.inflate(R.menu.search_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.search_bar);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
    }

    private void getBreedList()
    {
        Call<List<BreedName>> call = jsonPlaceFolderAPI.getBreed(LIMIT, page);

        //Call<List<BreedName>> call = jsonPlaceFolderAPI.getBreed();

        call.enqueue(new Callback<List<BreedName>>() {

            @Override
            public void onResponse(Call<List<BreedName>> call, Response<List<BreedName>> response) {

                if(!response.isSuccessful())
                {
                    return;
                }

                if(response.body().size() < 2) {

                    isLastPage=true;

                }else{
                    List<BreedName> items = response.body();

                    for (BreedName item : items) {
                        breedName.add(new BreedName(item.getBreed(), item.getId(),
                                item.getLifeSpan(), item.getOrigin(), item.getTemperament()));
                        units.add(new BreedName(item.getHeight(), item.getWeight()));
                        imgUrl.add(new ImageURL(item.getImage().getImgUrl()));
                        adapter.notifyDataSetChanged();
                    }

                    page++;

                    if(!isLastPage)
                        getBreedList();
                }
            }

            @Override
            public void onFailure(Call<List<BreedName>> call, Throwable t) {
                return;
            }
        });
    }
}