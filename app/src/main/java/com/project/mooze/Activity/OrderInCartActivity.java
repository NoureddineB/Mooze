package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.project.mooze.Model.ShoppingCart;
import com.project.mooze.R;

public class OrderInCartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_in_cart);
    }

    public void startShoppingCart(View v){
        Intent intent = new Intent(OrderInCartActivity.this,ShoppingCartActivity.class);
        startActivity(intent);
        finish();
    }
    public void backToOrder(View v){
        onBackPressed();
    }
}
