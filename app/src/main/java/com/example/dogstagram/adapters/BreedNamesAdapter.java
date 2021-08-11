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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.example.dogstagram.JsonPlaceFolderAPI;
import com.example.dogstagram.R;
import com.example.dogstagram.database.AppDatabase;
import com.example.dogstagram.database.Data;
import com.example.dogstagram.models.BreedName;
import com.example.dogstagram.models.ImageURL;
import com.example.dogstagram.models.Vote;
import com.example.dogstagram.models.VoteData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class BreedNamesAdapter extends RecyclerView.Adapter<BreedNamesAdapter.ViewHolder> {

    private static final String TAG = "RecylerViewAdapter";

    private Context context;
    private ArrayList<BreedName> breedNames = new ArrayList<>();
    private ArrayList<BreedName> units = new ArrayList<>();
    private ArrayList<ImageURL> imgUrls = new ArrayList<>();

    Dialog dispItem;

    JsonPlaceFolderAPI jsonPlaceFolderAPI;

    public BreedNamesAdapter(Context context, ArrayList<BreedName> breedNames,
                             ArrayList<ImageURL> imgUrls, ArrayList<BreedName> units) {
        this.breedNames = breedNames;
        this.imgUrls = imgUrls;
        this.context = context;
        this.units = units;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list, parent, false);

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
    public void onBindViewHolder(BreedNamesAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        BreedName breedName = breedNames.get(position);
        holder.breed.setText(breedName.getBreed());
        holder.id.setText(valueOf(position + 1));


        if (imgUrls.size() > 0) {
            ImageURL imgUrl = imgUrls.get(position);
            Picasso.with(holder.image.getContext())
                    .load(Uri.parse(imgUrl.getImgUrl()))
                    .into(holder.image);
        }

    }

    @Override
    public int getItemCount() {

        if (breedNames.size() > imgUrls.size())
            return imgUrls.size();

        return breedNames.size();
    }

    private void setDialoge(int pos)
    {
        ImageView image = dispItem.findViewById(R.id.image);

        Picasso.with(image.getContext())
                .load(Uri.parse(imgUrls.get(pos).getImgUrl()))
                .into(image);

        TextView dialogName = dispItem.findViewById(R.id.name);

        TextView dialogOrigin = dispItem.findViewById(R.id.orignValue);

        TextView dialogLifeSpan = dispItem.findViewById(R.id.lifeSpanValue);

        TextView dialogWeight = dispItem.findViewById(R.id.weightValue);

        TextView dialogHeight = dispItem.findViewById(R.id.heightValue);

        TextView dialogtemperment = dispItem.findViewById(R.id.tempermentValues);

        Button upVote = dispItem.findViewById(R.id.upVote);

        Button downVote = dispItem.findViewById(R.id.downVote);

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

        upVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Up Vote Clicked");
                vote(1,pos);
                addFav(pos);
            }
        });

        downVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Down Vote Clicked");
                vote(0, pos);
            }
        });
    }

    private void vote(int i, int pos)
    {
        RequestBody image_idPart = RequestBody.create(MultipartBody.FORM, imgUrls.get(pos).getImgUrl());
        RequestBody valuePart = RequestBody.create(MultipartBody.FORM, valueOf(i));

        //Call<Vote> call = jsonPlaceFolderAPI.addVote(image_idPart, valuePart);
        int start = imgUrls.get(pos).getImgUrl().lastIndexOf('/')+1;
        int end = imgUrls.get(pos).getImgUrl().lastIndexOf('.');

        String imgID = imgUrls.get(pos).getImgUrl().substring(start, end);

        VoteData voteData = new VoteData(imgID, valueOf(i));

        Call<Vote> call = jsonPlaceFolderAPI.addVote(voteData);

        call.enqueue(new Callback<Vote>() {
            @Override
            public void onResponse(Call<Vote> call, Response<Vote> response) {
                if(!response.isSuccessful())
                {
                    Log.d(TAG, "Vote Unsuccessful");
                    return;
                }

                Log.d(TAG, "Vote Successful");

                Toast.makeText(context, "Done: " + response.body().getMessage(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onFailure(Call<Vote> call, Throwable t) {
                Log.d(TAG, "Vote failed");
            }
        });
    }

    private void addFav(int pos)
    {
        Log.d(TAG, "addFav: Started");
        
        AppDatabase appDatabase = AppDatabase.getINSTANCE(context);

        Data data = new Data();

        BreedName item = breedNames.get(pos);

        data.breed = item.getBreed();

        data.lifeSpan = item.getLifeSpan();

        data.origin = item.getOrigin();

        data.temperament = item.getTemperament();

        data.imageURL = imgUrls.get(pos).getImgUrl();

        data.height = units.get(pos).getHeight().get_Height();

        data.weight = units.get(pos).getWeight().get_Weight();

        appDatabase.dataDao().insertData(data);
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
