package com.project.mooze.Activity;

import androidx.annotation.ColorRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.project.mooze.Adapter.RecyclerIngredientsAdapter;
import com.project.mooze.Adapter.RecyclerToppingAdapter;
import com.project.mooze.Model.Restaurent.Ingredient;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.Restaurent.Topping;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class IngredientsActivity extends AppCompatActivity {

    private RecyclerView recycler_ingredients;
    private RecyclerView recycler_toppings;
    private List<Ingredient> ingredients;
    private List<Topping> toppings;
    private Disposable disposable;
    private ImageView image_menu;
    private double menuPrice;
    private List<Double> toppingPrice;
    private String menuName;
    private String menuImage;
    private RequestManager glide;
    private TextView menu_price;
    private TextView menu_name;
    private TextView my_topping;
    private TextView my_ingredients;
    private RecyclerIngredientsAdapter recyclerIngredientsAdapter;
    private RecyclerToppingAdapter recyclerToppingAdapter;
    private List<Ingredient> selectedIngredients;
    private List<Topping> selectedToppings;
    private SharedPreferences preferences;
    private RecyclerView.Adapter adapter;
    double price_extra;

// NO ADAPTER ATTACHED SKIPPING LAYOUT ERROR


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        preferences = getSharedPreferences("PREFS",0);
        getAllIngredients();
        instantiate();
        configureRecyclerView();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    private void instantiate(){
        toppings = new ArrayList<>();
        ingredients = new ArrayList<>();
        recycler_ingredients = findViewById(R.id.recycler_menu_ingredients);
        recycler_toppings = findViewById(R.id.recycler_menu_toppings);
        image_menu = findViewById(R.id.image_menu_ingredients);
        my_ingredients = findViewById(R.id.text_ingredients);
        glide = Glide.with(this);
        this.recyclerToppingAdapter = new RecyclerToppingAdapter(this.toppings,glide);
        this.recyclerIngredientsAdapter = new RecyclerIngredientsAdapter(this.ingredients,glide);
        this.adapter = recyclerIngredientsAdapter;
        menu_price = findViewById(R.id.text_ingredients_price);
        menu_name = findViewById(R.id.ingredient_menu_name);
        selectedIngredients = recyclerIngredientsAdapter.getQuantity();
        selectedToppings = recyclerToppingAdapter.getToppingQuantity();
        glide.load(menuImage).apply(RequestOptions.centerCropTransform()).into(image_menu);
        menuPrice = getIntent().getDoubleExtra("menuPrice",0);
        menuName = getIntent().getStringExtra("menuName");
        menuImage = getIntent().getStringExtra("menuImage");
        menu_price.setText(menuPrice + " €");
        menu_name.setText(menuName);
        glide.load(menuImage).apply(RequestOptions.centerCropTransform()).into(image_menu);

    }

    public void startCustomActivity(View v){
        Intent intent = new Intent(IngredientsActivity.this,CustomActivity.class);
        intent.putExtra("selectedIngredients", (Serializable) selectedIngredients);
        intent.putExtra("selectedToppings", (Serializable) selectedToppings);
        intent.putExtra("menuCustomID",getIntent().getIntExtra("menusIngredientID",0));
        intent.putExtra("menuCustomPrice",menuPrice);
        intent.putExtra("menuCustomName",menuName);
        Log.e("TAGSELECTEDINGREDIENT", String.valueOf(selectedIngredients));

        startActivity(intent);

    }
    public void backToRestaurant(View v){
        onBackPressed();

    }

    private void configureRecyclerView(){
        this.recycler_ingredients.setAdapter(this.recyclerIngredientsAdapter);
        recycler_ingredients.setLayoutManager(new LinearLayoutManager(IngredientsActivity.this));
        recycler_toppings.setLayoutManager(new LinearLayoutManager(IngredientsActivity.this));
        this.recycler_toppings.setAdapter(this.recyclerToppingAdapter);

    }
    private void getAllIngredients(){
        this.disposable = MoozeStreams.getMenus(getIntent().getIntExtra("menusIngredientID",0)).subscribeWith(create());

    }


    private DisposableObserver<Menus> create(){
        return new DisposableObserver<Menus>() {
            @Override
            public void onNext(Menus menus) {
                updateIngredients(menus.getMains().get(0).getIngredients());
                updateToppings(menus.getMains().get(0).getToppings());
                SharedPreferences appSharedPrefs = getSharedPreferences("PREFS",0);
                SharedPreferences.Editor prefsEditor = appSharedPrefs.edit();
                Gson gson = new Gson();
                String json = gson.toJson(menus.getMains().get(0).getIngredients());
                prefsEditor.putString("IngredientList", json);
                prefsEditor.apply();
                Log.e("TAGF", String.valueOf(menus));

            }
            @Override
            public void onError(Throwable e) {
                Log.e("TAGINGEDIENT", "Error" + e);


            }

            @Override
            public void onComplete() {




            }
        };
    }




    private void disposeWhenDestroy(){
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }


    private void updateIngredients(List<Ingredient> ingredient){
        ingredients.addAll(ingredient);
        recyclerIngredientsAdapter.notifyDataSetChanged();
    }
    private void updateToppings(List<Topping> topping){
        toppings.addAll(topping);
        recyclerToppingAdapter.notifyDataSetChanged();

    }

}
