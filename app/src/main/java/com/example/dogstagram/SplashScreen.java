package com.example.dogstagram;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

        Handler handler = new Handler();
        Intent intent = new Intent(this, BaseActivity.class);

        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(intent);
                finish();
            }
        }, 5000);
    }
}