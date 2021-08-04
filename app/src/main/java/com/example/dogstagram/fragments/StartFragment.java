package com.example.dogstagram.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.dogstagram.R;

public class StartFragment extends Fragment {

    public StartFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull  View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button breedButton = view.findViewById(R.id.breedBtn);

        NavController navControler = Navigation.findNavController(view);

        breedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navControler.navigate(R.id.action_startFragment_to_breedFragment);
            }
        });

    }
}