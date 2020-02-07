package com.project.mooze.Activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.project.mooze.Fragment.MainFragment;
import com.project.mooze.Fragment.MapFragment;
import com.project.mooze.Fragment.QRfragment;
import com.project.mooze.Fragment.SettingsFragment;
import com.project.mooze.R;
import com.project.mooze.Utils.Utils;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {

    final Fragment fragment_main = new MainFragment();
    final Fragment fragment_map = new MapFragment();
    final Fragment fragment_QR = new QRfragment();
    final Fragment fragment_settings = new SettingsFragment();
    final FragmentManager fm = getSupportFragmentManager();


    private boolean isQRcode;
    Fragment active = fragment_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Utils.hideStatusBar(HomeActivity.this);
        checkifQRcode();
        configureBottomNav();
        setFragment();





    }

    //Configure fragment manager and add the fragment
    private void setFragment(){
        fm.beginTransaction().add(R.id.main_frame_layout, fragment_settings, "4").hide(fragment_settings).commit();
        fm.beginTransaction().add(R.id.main_frame_layout, fragment_QR, "3").hide(fragment_QR).commit();
        fm.beginTransaction().add(R.id.main_frame_layout, fragment_map, "2").hide(fragment_map).commit();
        fm.beginTransaction().add(R.id.main_frame_layout,fragment_main, "1").commit();

    }
    //Configure Bottom Navigation
    private void configureBottomNav(){
        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    //Check if the current fragment is fragment_qrcode and save it in shared preferences
    private void checkifQRcode(){
        SharedPreferences preferences = this.getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        isQRcode = active == fragment_QR;
        editor.putBoolean("isQRcode",isQRcode);
        editor.apply();
    }

    //Configure click on bottom navigation's item
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fm.beginTransaction().hide(active).show(fragment_main).commit();
                    active = fragment_main;
                    return true;
                case R.id.navigation_map:
                    fm.beginTransaction().hide(active).show(fragment_map).commit();
                    active = fragment_map;
                    return true;
                case R.id.navigation_QRcode:
                    fm.beginTransaction().hide(active).show(fragment_QR).commit();
                    active = fragment_QR;
                    return true;
                case R.id.navigation_settings:
                    fm.beginTransaction().hide(active).show(fragment_settings).commit();
                    active = fragment_settings;
                    return true;
            }
            return false;
        }
    };


}
