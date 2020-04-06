package com.mobile.aquafish;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.mobile.aquafish.fragment.FeedingFragment;
import com.mobile.aquafish.fragment.HomeFragment;
import com.mobile.aquafish.fragment.ProfileFragment;

import java.util.Objects;

public class FragmentMain extends AppCompatActivity {

    private HomeFragment homeFragment;
    private FeedingFragment feedingFragment;
    private ProfileFragment profileFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_main);
        BottomNavigationView forNav = findViewById(R.id.navMain);

        homeFragment = new HomeFragment();
        feedingFragment = new FeedingFragment();
        profileFragment = new ProfileFragment();
        setFragment(homeFragment);

        forNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("ResourceType")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.navigation_home:
                        setFragment(homeFragment);
                        return true;

                    case R.id.navigation_feeding:
                        setFragment(feedingFragment);
                        return true;

                    case R.id.navigation_profile:
                        setFragment(profileFragment);
                        return true;

                    default:
                        return false;
                }
            }

        });
    }

    private void setFragment(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frameMain, fragment);
        fragmentTransaction.commit();
    }

    public void setTitleActionBar(String titleActionBar) {
        Objects.requireNonNull(getSupportActionBar()).setTitle(titleActionBar);
    }
}