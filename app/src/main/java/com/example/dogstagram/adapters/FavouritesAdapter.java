package com.example.dogstagram.adapters;

import android.app.Dialog;
import android.content.ClipData;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v4.os.IResultReceiver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogstagram.R;
import com.example.dogstagram.database.AppDatabase;
import com.example.dogstagram.database.Data;
import com.example.dogstagram.models.BreedName;
import com.example.dogstagram.models.ImageURL;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.valueOf;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.ViewHolder> {

    private static final String TAG = "FavouritesAdapter";

    Dialog dispItem;

    List<Data> favDatas = new ArrayList<>();

    public FavouritesAdapter(List<Data> favDatas) {
        this.favDatas = favDatas;
    }


    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_fav, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        dispItem = new Dialog(parent.getContext());
        dispItem.setContentView(R.layout.dialog_info);
        dispItem.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        viewHolder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                setDialoge(viewHolder.getAdapterPosition());

                dispItem.show();
            }
        });

        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Data favData = favDatas.get(position);
        holder.breed.setText(favData.breed);
        holder.id.setText(valueOf(position + 1));


        String imgUrl = favData.imageURL;
        Picasso.with(holder.image.getContext())
                .load(Uri.parse(imgUrl))
                .into(holder.image);

    }

    @Override
    public int getItemCount() {
        return favDatas.size();
    }

    private void setDialoge(int pos)
    {
        Data data = favDatas.get(pos);

        ImageView image = dispItem.findViewById(R.id.image);

        Picasso.with(image.getContext())
                .load(Uri.parse(data.imageURL))
                .into(image);

        TextView dialogName = dispItem.findViewById(R.id.name);

        TextView dialogOrigin = dispItem.findViewById(R.id.orignValue);

        TextView dialogLifeSpan = dispItem.findViewById(R.id.lifeSpanValue);

        TextView dialogWeight = dispItem.findViewById(R.id.weightValue);

        TextView dialogHeight = dispItem.findViewById(R.id.heightValue);

        TextView dialogtemperment = dispItem.findViewById(R.id.tempermentValues);

        Button upVote = dispItem.findViewById(R.id.upVote);

        Button downVote = dispItem.findViewById(R.id.downVote);

        upVote.setVisibility(View.INVISIBLE);

        downVote.setVisibility(View.INVISIBLE);

        dialogName.setText(data.breed);

        if(data.origin != null)
            dialogOrigin.setText(data.origin);
        else
            dialogOrigin.setText("Unknown");

        dialogLifeSpan.setText(data.lifeSpan);

        dialogWeight.setText(data.weight + " Kg");

        dialogHeight.setText(data.height + " cm");

        dialogtemperment.setText(data.temperament);

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private static final String TAG = "ViewHolder";
        RelativeLayout layout;
        TextView breed;
        TextView id;
        ImageView image;

        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: Called");

            layout = itemView.findViewById(R.id.layout);
            breed = itemView.findViewById(R.id.Breed);
            id = itemView.findViewById(R.id.id);
            image = itemView.findViewById(R.id.dogImage);

        }
    }
}
