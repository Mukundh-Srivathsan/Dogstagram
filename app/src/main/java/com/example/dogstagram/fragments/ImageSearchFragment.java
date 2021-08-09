package com.example.dogstagram.fragments;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.dogstagram.JsonPlaceFolderAPI;
import com.example.dogstagram.R;
import com.example.dogstagram.models.UploadImg;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.app.Activity.RESULT_OK;
import static java.lang.String.valueOf;

public class ImageSearchFragment extends Fragment {

    private static final int PICK_IMAGE = 100;

    String imgURI;
    ImageView imageView;

    JsonPlaceFolderAPI jsonPlaceFolderAPI;

    private static final String TAG = "ImageSearchFragment";

    public ImageSearchFragment() {
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
        return inflater.inflate(R.layout.fragment_image_search, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.thedogapi.com/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonPlaceFolderAPI = retrofit.create(JsonPlaceFolderAPI.class);

        imageView = view.findViewById(R.id.dogImage);

        Button browseBtn = view.findViewById(R.id.browse);
        Button uploadBtn = view.findViewById(R.id.upload);

        browseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "Browse Clicked");
                if ((ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {
                    Log.d(TAG, "onClick: Requesting Permission");
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 2000);
                } else {
                    Log.d(TAG, "onClick: Starting Gallery");
                    openGallery();
                    Log.d(TAG, "Smtg : " + imgURI);
                }
            }
        });

        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((ActivityCompat.checkSelfPermission(getActivity(),
                        Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)) {

                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                } else {
                    Log.d(TAG, "Upload Clicked");

                    File f = saveBitmap();

                    RequestBody filePart = RequestBody.create(MediaType.parse("image/png"), f);

                    MultipartBody.Part file = MultipartBody.Part.createFormData("file", f.getName(), filePart);

                    Call<RequestBody> call = jsonPlaceFolderAPI.uploadImg(file);

                    call.enqueue(new Callback<RequestBody>() {
                        @Override
                        public void onResponse(Call<RequestBody> call, Response<RequestBody> response) {
                            if (!response.isSuccessful()) {
                                Log.d(TAG, "Upload Unsuccessful " + response.message());
                                return;
                            }

                            Log.d(TAG, "Upload Successful");

                            Toast.makeText(v.getContext(),
                                    "YAYYY!!", Toast.LENGTH_SHORT);
                        }

                        @Override
                        public void onFailure(Call<RequestBody> call, Throwable t) {
                            Log.d(TAG, "Upload Failed");
                        }
                    });
                }
            }

        });

    }

    private void openGallery() {
        Log.d(TAG, "openGallery: Started");
        Intent cameraIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(cameraIntent, PICK_IMAGE);
        }
    }

    private File saveBitmap() {
        imageView.invalidate();
        BitmapDrawable bitmapDrawable = (BitmapDrawable) imageView.getDrawable();
        Bitmap bitmap = bitmapDrawable.getBitmap();

        String fileName = String.format("%d.png", System.currentTimeMillis());
        File file = Environment.getExternalStorageDirectory();
        File savebitmap = new File(file.getAbsolutePath() + File.separator + fileName);

        try {
            //savebitmap.createNewFile();
            FileOutputStream fos = new FileOutputStream(savebitmap);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return savebitmap;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        //super method removed
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imgURI = data.getData().toString();
            Picasso.with(imageView.getContext())
                    .load(imgURI)
                    .resize(300, 300)
                    .into(imageView);
        }
    }
}