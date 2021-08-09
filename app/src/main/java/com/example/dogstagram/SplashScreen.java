package com.example.dogstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ImageView paw = findViewById(R.id.paw);
        Animation anim = AnimationUtils.loadAnimation(this, R.anim.paw_anim);

        paw.setAnimation(anim);

        MediaPlayer mediaPlayer = MediaPlayer.create(SplashScreen.this, R.raw.dogwoof);
        mediaPlayer.start();

        Handler handler = new Handler();
        Intent intent = new Intent(this, BaseActivity.class);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mediaPlayer.stop();
                mediaPlayer.release();
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}