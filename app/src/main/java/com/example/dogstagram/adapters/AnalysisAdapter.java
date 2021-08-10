package com.example.dogstagram.adapters;

import android.app.Dialog;
import android.content.Context;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dogstagram.JsonPlaceFolderAPI;
import com.example.dogstagram.R;
import com.example.dogstagram.models.BreedName;
import com.example.dogstagram.models.ImageURL;
import com.example.dogstagram.models.Labels;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static java.lang.String.format;
import static java.lang.String.valueOf;

public class AnalysisAdapter extends RecyclerView.Adapter<AnalysisAdapter.ViewHolder> {

    private static final String TAG = "RecylerViewAdapter";

    private Context context;

    List<Labels> labelsList = new ArrayList<>();

    JsonPlaceFolderAPI jsonPlaceFolderAPI;

    public AnalysisAdapter(List<Labels> labelsList) {
        this.labelsList = labelsList;
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_list, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnalysisAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Labels label = labelsList.get(position);

        Log.d(TAG, "name: " + label.getName());
        //Log.d(TAG, "match: " + String.format("%.4f",label.getCofidence()));

        Formatter fmt = new Formatter();
        //fmt.format("%.4f", label.getCofidence());

        if(holder != null) {
            holder.name.setText(label.getName());
            holder.match.setText(label.getCofidence());
            holder.id.setText(valueOf(position + 1));
        }
    }


    @Override
    public int getItemCount() {
        return labelsList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private static final String TAG = "ViewHolder";
        TextView name;
        TextView match;
        TextView id;


        public ViewHolder(View itemView) {
            super(itemView);
            Log.d(TAG, "ViewHolder: Called");

            name = (TextView) itemView.findViewById(R.id.nameValue);
            match = (TextView) itemView.findViewById(R.id.matchValue);
            id = (TextView) itemView.findViewById(R.id.id);
        }
    }
}
