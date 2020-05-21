package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;
import com.project.mooze.Utils.Utils;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class SplashActivity extends AppCompatActivity {

    private final int SPLASH_DISPLAY_LENGTH = 2500;

    private Intent mainIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Utils.hideStatusBar(this);
        startSplash();


    }




    private void startSplash(){
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                if (Utils.isLogged(SplashActivity.this)){
                    mainIntent = new Intent(SplashActivity.this,HomeActivity.class);
                }else {
                    if (Utils.phoneAdded(SplashActivity.this)){
                        mainIntent = new Intent(SplashActivity.this,HomeActivity.class);
                    }else {
                        mainIntent = new Intent(SplashActivity.this, MainActivity.class);
                    }
                }

                SplashActivity.this.startActivity(mainIntent);
                SplashActivity.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
    }









