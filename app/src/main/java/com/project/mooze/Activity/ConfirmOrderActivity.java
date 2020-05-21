package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.project.mooze.R;

import kotlin.jvm.internal.Intrinsics;

public class ConfirmOrderActivity extends AppCompatActivity {
    private ConstraintLayout constraintLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_order);
        constraintLayout = findViewById(R.id.constraint_order_layout);
        constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(ConfirmOrderActivity.this,HomeActivity.class);
        startActivity(intent);
    }

}
