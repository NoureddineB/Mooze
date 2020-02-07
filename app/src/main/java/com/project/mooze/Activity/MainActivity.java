package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.mooze.R;
import com.project.mooze.Utils.Utils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Utils.hideStatusBar(this);
    }
    public void startHome(View v){
        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
        startActivity(intent);

    }
}
