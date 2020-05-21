package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.project.mooze.Model.CartItem;
import com.project.mooze.Model.Order.RestaurantSuggestion;
import com.project.mooze.Model.Order.Suggestion;
import com.project.mooze.Model.ShoppingCart;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;

import java.util.Random;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class OrderInCartActivity extends AppCompatActivity {


    private TextView text_suggestion;
    private TextView text_price;
    private Disposable disposable;
    private int restaurantid;
    private SharedPreferences preferences;
    private Suggestion suggestion;
    private Random randomGenerator;
    private Button button_add;
    private double price;
    public Intent customintent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_in_cart);
        customintent = getIntent();
        getSuggestion();
        preferences = getSharedPreferences("PREFS",0);
        restaurantid = preferences.getInt("RESTOID",0);
        text_suggestion = findViewById(R.id.text_suggestion);
        text_price = findViewById(R.id.text_suggestion_price);
        randomGenerator = new Random();
        button_add = findViewById(R.id.button_suggestion);


    }

    public void startShoppingCart(View v){
        Intent intent = new Intent(OrderInCartActivity.this,ShoppingCartActivity.class);
        startActivity(intent);
        finish();
    }
    public void backToOrder(View v){
        Intent intent = new Intent(OrderInCartActivity.this, RestaurantActivity.class);
        intent.putExtra("ID", restaurantid);
        startActivity(intent);

    }

    public void addSuggestionToCart(View v){
        price = suggestion.getPrice();
        CartItem cartItem = new CartItem(null,suggestion,1,price,suggestion.getRestaurantId());
        ShoppingCart shoppingCart = new ShoppingCart();
        if (ShoppingCart.getCart(OrderInCartActivity.this).size() > 0){
            if (ShoppingCart.getCart(OrderInCartActivity.this).get(0).restoid == cartItem.restoid){
                shoppingCart.addItem(cartItem,OrderInCartActivity.this);
            }else {
                ShoppingCart.clearCart(ShoppingCart.getCart(OrderInCartActivity.this),OrderInCartActivity.this);
                shoppingCart.addItem(cartItem,OrderInCartActivity.this);
            }
        }else {
            shoppingCart.addItem(cartItem,OrderInCartActivity.this);
        }
        button_add.setClickable(false);
        button_add.setText("OK !");
        button_add.setBackgroundColor(Color.GREEN);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    private void getSuggestion(){
        this.disposable = MoozeStreams.getSuggestion(1).subscribeWith(create());

    }

    private DisposableObserver<RestaurantSuggestion> create(){
        return new DisposableObserver<RestaurantSuggestion>() {
            @Override
            public void onNext(RestaurantSuggestion suggestions) {
                int index = randomGenerator.nextInt(suggestions.getSuggestions().size());
                suggestion = suggestions.getSuggestions().get(index);
                if (suggestion != null) {
                    text_suggestion.setText(suggestion.getName());
                    text_price.setText(suggestion.getPrice() + " €");
                    price = suggestion.getPrice();
                }

            }

            @Override
            public void onError(Throwable e) {
               Log.e("TAGERRORCARTSUGGEST","error " + e);

            }

            @Override
            public void onComplete() {


            }
        };
    }




    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }
}
