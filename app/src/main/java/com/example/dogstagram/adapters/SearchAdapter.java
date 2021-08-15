package com.example.dogstagram.adapters;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Vibrator;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.example.dogstagram.models.SearchBreed;
import com.example.dogstagram.models.Vote;
import com.example.dogstagram.models.VoteData;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.String.valueOf;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder>
{

    private static final String TAG = "RecylerViewAdapter";

    private Context context;
    private ArrayList<SearchBreed> images = new ArrayList<>();
    private ArrayList<SearchBreed> breedData = new ArrayList<>();
    private ArrayList<SearchBreed> units = new ArrayList<>();

    Dialog dispItem;

    JsonPlaceFolderAPI jsonPlaceFolderAPI;

    public SearchAdapter(Context context, ArrayList<SearchBreed> breedData,
                         ArrayList<SearchBreed> images, ArrayList<SearchBreed> units) {
        this.breedData = breedData;
        this.units = units;
        this.images = images;
        this.context = context;
    }

    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list, parent, false);

        SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(view);

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

                setDialog(viewHolder.getAdapterPosition());

                dispItem.show();
            }
        });

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SearchAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        SearchBreed breedName = breedData.get(position);
        holder.breed.setText(breedName.getBreed());
        holder.id.setText(valueOf(position + 1));


        if (images.size() > 0) {
            SearchBreed imgUrl = images.get(position);
            Picasso.with(holder.image.getContext())
                    .load(Uri.parse(imgUrl.getImageid()))
                    .into(holder.image);
        }

    }

    @Override
    public int getItemCount() {

       // if(images.size()<breedData.size())
         //   return images.size();

        return breedData.size();
    }

    private void setDialog(int pos)
    {

        ImageView image = dispItem.findViewById(R.id.image);

        Picasso.with(image.getContext())
                .load(Uri.parse(images.get(pos).getImageid()))
                .into(image);

        TextView dialogName = dispItem.findViewById(R.id.name);

        TextView dialogOrigin = dispItem.findViewById(R.id.orignValue);

        TextView dialogLifeSpan = dispItem.findViewById(R.id.lifeSpanValue);

        TextView dialogWeight = dispItem.findViewById(R.id.weightValue);

        TextView dialogHeight = dispItem.findViewById(R.id.heightValue);

        TextView dialogtemperment = dispItem.findViewById(R.id.tempermentValues);

        Button upVote = dispItem.findViewById(R.id.upVote);

        Button downVote = dispItem.findViewById(R.id.downVote);

        ImageButton share = dispItem.findViewById(R.id.shareBtn);

        share.setVisibility(View.INVISIBLE);

        SearchBreed item = breedData.get(pos);

        dialogName.setText(item.getBreed());

        if(item.getOrigin() != null)
            dialogOrigin.setText(item.getOrigin());
        else
            dialogOrigin.setText("Unknown");

        dialogLifeSpan.setText(item.getLifeSpan());

        dialogWeight.setText(units.get(pos).getWeight().get_Weight() + " Kg");

        dialogHeight.setText(units.get(pos).getHeight().get_Height() + " cm");

        dialogtemperment.setText(item.getTemperament());

        Vibrator vibe = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);

        upVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Up Vote Clicked");

                vibe.vibrate(100);

                vote(1,pos);
                addFav(pos);
            }
        });

        downVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Down Vote Clicked");

                vibe.vibrate(100);

                vote(0, pos);
                Toast.makeText(context, "Down Voted", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void vote(int i, int pos)
    {
        RequestBody image_idPart = RequestBody.create(MultipartBody.FORM, images.get(pos).getImageid());
        RequestBody valuePart = RequestBody.create(MultipartBody.FORM, valueOf(i));

        //Call<Vote> call = jsonPlaceFolderAPI.addVote(image_idPart, valuePart);
        int start = images.get(pos).getImageid().lastIndexOf('/')+1;
        int end = images.get(pos).getImageid().lastIndexOf('.');

        String imgID = images.get(pos).getImageid().substring(start, end);

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

        List<String> imageURLS = appDatabase.dataDao().getImgURLs();
        boolean doesExist=false;

        for(String url : imageURLS) {
            if (url.equals(images.get(pos).getImageid()))
                doesExist = true;
        }

        if(!doesExist) {
            SearchBreed item = breedData.get(pos);

            Data data = new Data();

            data.breed = item.getBreed();

            data.lifeSpan = item.getLifeSpan();

            data.origin = item.getOrigin();

            data.temperament = item.getTemperament();

            data.imageURL = images.get(pos).getImageid();

            data.height = units.get(pos).getHeight().get_Height();

            data.weight = units.get(pos).getWeight().get_Weight();

            appDatabase.dataDao().insertData(data);

            Toast.makeText(context, "Added to Favourites <3", Toast.LENGTH_SHORT).show();
        }else
        {
            Toast.makeText(context, "Already a Favourite :)", Toast.LENGTH_SHORT).show();
        }
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

            Picasso.with(image.getContext())
                    .load(R.mipmap.image_loading)
                    .fit()
                    .into(image);

        }
    }
}
