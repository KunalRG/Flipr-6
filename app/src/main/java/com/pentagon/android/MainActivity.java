package com.pentagon.android;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.pentagon.android.Fragment.ContactFragment;
import com.pentagon.android.Fragment.MediFragment;
import com.pentagon.android.Fragment.NotiFragment;
import com.pentagon.android.Fragment.StatFragment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_stat:
                    loadFragment(new StatFragment());
                    return true;
                case R.id.navigation_contact:
                    loadFragment(new ContactFragment());
                    return true;
                case R.id.navigation_notification:
                    loadFragment(new NotiFragment());
                    return true;
                case R.id.navigation_hospital:
                    loadFragment(new MediFragment());
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadFragment(new ContactFragment());
        BottomNavigationView navView = findViewById(R.id.am_nav_view);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    private boolean loadFragment(Fragment fragment) {
        if (fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.am_frame_layout, fragment)
                    .commit();
            return true;
        }
        return false;
    }


}
