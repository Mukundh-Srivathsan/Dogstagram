package com.example.dogstagram;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class RecylerViewAdapter extends RecyclerView.Adapter<RecylerViewAdapter.ViewHolder> {

    private static final String TAG = "RecylerViewAdapter";

    private Context context;
    private ArrayList<Items> breedNames = new ArrayList<>();
    private ArrayList<Items> imgUrls = new ArrayList<>();

    public class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";
        RelativeLayout layout;
        TextView breed;
        ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: Called");
            layout = itemView.findViewById(R.id.layout);
            breed = itemView.findViewById(R.id.Breed);
            image = itemView.findViewById(R.id.dogImage);
        }
    }


    public RecylerViewAdapter(ArrayList<Items> breedNames, ArrayList<Items> imgUrls) {
        this.breedNames = breedNames;
        this.imgUrls = imgUrls;
        //this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecylerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Items breedName = breedNames.get(position);
        holder.breed.setText(breedName.getBreed());


        if(imgUrls.size()>0) {
            Items imgUrl = imgUrls.get(position);
            //holder.breed.setText(imgUrl.getImgUrl().toString());
            Picasso.with(holder.image.getContext())
                    .load(imgUrl.getImgUrl())
                    .resize(60, 60)
                    .into(holder.image);
        }

    }

    @Override
    public int getItemCount() {
        return breedNames.size();
    }


}
