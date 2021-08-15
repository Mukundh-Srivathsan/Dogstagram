package com.example.dogstagram;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

import com.example.dogstagram.fragments.BreedFragment;
import com.example.dogstagram.fragments.FavouritesFragment;
import com.example.dogstagram.fragments.ImageSearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
;

public class BaseActivity extends AppCompatActivity {

    private static final String TAG = "Base";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        Log.d(TAG, "onCreate: Started");

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainerView, new BreedFragment())
                .commit();

        BottomNavigationView navBar = findViewById(R.id.navBar);
        navBar.setOnNavigationItemSelectedListener(navListner);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener navListner
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId())
            {
                case R.id.breedFragment:
                    selectedFragment = new BreedFragment();
                    break;

                case R.id.imageSearchFragment:
                    selectedFragment = new ImageSearchFragment();
                    break;

                case R.id.favouritesFragment:
                    selectedFragment = new FavouritesFragment();
                    break;
            }

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragmentContainerView, selectedFragment)
                    .commit();

            return true;
        }
    };
}