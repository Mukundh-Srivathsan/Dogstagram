package com.example.dogstagram;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder>{

    private static final String TAG = "RecylerViewAdapter";

    private Context context;
    private ArrayList<String> breedNames = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder{

        RelativeLayout layout;
        TextView breed;


        public ViewHolder(View itemView) {
            super(itemView);
            layout = itemView.findViewById(R.id.layout);
            breed = itemView.findViewById(R.id.Breed);
        }
    }


    public RecylerViewAdapter(ArrayList<String> breedNames) {
        this.breedNames = breedNames;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_base, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder( RecylerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        holder.breed.setText(breedNames.get(position));

    }

    @Override
    public int getItemCount() {
        return breedNames.size();
    }


}
