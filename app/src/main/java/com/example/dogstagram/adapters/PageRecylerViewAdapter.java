package com.example.dogstagram.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dogstagram.json_classes.BreedName;
import com.example.dogstagram.json_classes.ImageURL;
import com.example.dogstagram.JsonPlaceFolderAPI;
import com.example.dogstagram.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class PageRecylerViewAdapter extends RecyclerView.Adapter<PageRecylerViewAdapter.ViewHolder> {

    private static final String TAG = "RecylerViewAdapter";

    private Context context;
    private ArrayList<BreedName> breedNames = new ArrayList<>();
    private ArrayList<BreedName> units = new ArrayList<>();
    private ArrayList<ImageURL> imgUrls = new ArrayList<>();

    Dialog dispItem;

    JsonPlaceFolderAPI jsonPlaceFolderAPI;

    public PageRecylerViewAdapter(Context context, ArrayList<BreedName> breedNames,
                                  ArrayList<ImageURL> imgUrls, ArrayList<BreedName> units) {
        this.breedNames = breedNames;
        this.imgUrls = imgUrls;
        this.context = context;
        this.units = units;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list1, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thedogapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceFolderAPI = retrofit.create(JsonPlaceFolderAPI.class);


        dispItem = new Dialog(context);
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
    public void onBindViewHolder(PageRecylerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        BreedName breedName = breedNames.get(position);
        holder.breed.setText(breedName.getBreed());
        holder.id.setText(valueOf(position + 1));


        if (imgUrls.size() > 0) {
            ImageURL imgUrl = imgUrls.get(position);
            Picasso.with(holder.image.getContext())
                    .load(Uri.parse(imgUrl.getImgUrl()))
                    .resize(140, 140)
                    .into(holder.image);
        }

    }

    @Override
    public int getItemCount() {

        if (breedNames.size() > imgUrls.size())
            return imgUrls.size();

        return breedNames.size();
    }

    public void setDialoge(int pos)
    {
        TextView dialogName = dispItem.findViewById(R.id.name);

        TextView dialogOriginTitle = dispItem.findViewById(R.id.origin);
        TextView dialogOrigin = dispItem.findViewById(R.id.orignValue);

        TextView dialogLifeTitle = dispItem.findViewById(R.id.lifeSpan);
        TextView dialogLifeSpan = dispItem.findViewById(R.id.lifeSpanValue);

        TextView dialogWeightTitle = dispItem.findViewById(R.id.weight);
        TextView dialogWeight = dispItem.findViewById(R.id.weightValue);

        TextView dialogHeightTitle = dispItem.findViewById(R.id.height);
        TextView dialogHeight = dispItem.findViewById(R.id.heightValue);

        TextView dialogtemperment = dispItem.findViewById(R.id.tempermentValues);

        ImageView image = dispItem.findViewById(R.id.image);

        Picasso.with(image.getContext())
                .load(Uri.parse(imgUrls.get(pos).getImgUrl()))
                .resize(200, 200)
                .into(image);

        BreedName item = breedNames.get(pos);

        dialogName.setText(item.getBreed());

        if(item.getOrigin() != null)
            dialogOrigin.setText(item.getOrigin());
        else
            dialogOrigin.setText("Unknown");

        dialogLifeSpan.setText(item.getLifeSpan());

        dialogWeight.setText(units.get(pos).getWeight().get_Weight() + " Kg");

        dialogHeight.setText(units.get(pos).getHeight().get_Height() + " cm");

        dialogtemperment.setText(item.getTemperament());
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
