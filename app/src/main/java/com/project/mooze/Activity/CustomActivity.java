package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.SyncStateContract;
import android.util.Log;
import android.util.Size;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;
import com.project.mooze.Adapter.PageAdapter;
import com.project.mooze.Fragment.DrinkFragment;
import com.project.mooze.Fragment.SauceFragment;
import com.project.mooze.Fragment.SizeFragment;
import com.project.mooze.Model.CartItem;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.Model.Restaurent.Ingredient;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;

import com.project.mooze.Model.Restaurent.Sauce;
import com.project.mooze.Model.Restaurent.Topping;
import com.project.mooze.Model.ShoppingCart;
import com.project.mooze.R;
import com.project.mooze.Utils.CustomViewPager;
import com.project.mooze.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class CustomActivity extends AppCompatActivity implements DrinkFragment.OnDrinkPass, SizeFragment.onSizePass, SauceFragment.onSaucePass {

    public static final int FRAGMENT_SIZE = 0;
    public static final int FRAGMENT_DRINKS = 1;
    public static final int FRAGMENT_SAUCE = 2;

    private TextView text_next;
    private TextView text_previous;
    private TextView text_confirm;
    private Menus menus = new Menus();
    SizeFragment sizeFragment;

    private List<Ingredient> selectedIngredients = new ArrayList<>();
    private List<Topping> selectedTopping = new ArrayList<>();
    private int sizeID = 0;
    private double menuPrice;
    private double toppingPrice;
    private double totalPrice;
    private String menuName;
    private Drink selectedDrink;
    private List<Sauce> selectedSauces;
    private SharedPreferences preferences;
    private Button button_cancel;
    private int restaurantid;
    ViewPager pager;
    ViewPager.LayoutParams layoutParams;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom);
        Utils.hideStatusBar(this);
        pager = findViewById(R.id.view_pager);
        layoutParams = new ViewPager.LayoutParams();
        text_next = findViewById(R.id.text_next);
        text_previous = findViewById(R.id.text_previous);
        text_confirm = findViewById(R.id.text_confirm);
        sizeFragment = SizeFragment.newInstance();
        preferences = getSharedPreferences("PREFS",0);
        restaurantid = preferences.getInt("RESTOID",0);
        button_cancel = findViewById(R.id.button_cancel);
        cancelButton();
        configureViewPagerAndTabs();
        showFragment(FRAGMENT_SIZE);
        Intent i = getIntent();
        selectedIngredients= (List<Ingredient>) i.getSerializableExtra("selectedIngredients");
        selectedTopping= (List<Topping>) i.getSerializableExtra("selectedToppings");
        menuPrice = getIntent().getDoubleExtra("menuCustomPrice",0);
        menuName = getIntent().getStringExtra("menuCustomName");

    }



    private void configureViewPagerAndTabs() {
        TabLayout tabs = findViewById(R.id.activity_main_tabs);
        pager.setAdapter(new PageAdapter(getSupportFragmentManager()));
        tabs.setupWithViewPager(pager);
        tabs.setTabMode(TabLayout.MODE_FIXED);
        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getPosition() == 2){
                    text_next.setVisibility(View.GONE);
                    text_confirm.setVisibility(View.VISIBLE);
                }else {
                    text_next.setVisibility(View.VISIBLE);
                    text_confirm.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

    }
    private void createMenu(){
        List<Main> menusMainList = new ArrayList<>();
        List<Drink> menusDrinkList = new ArrayList<>();
        Main menusMain = new Main();
        totalPrice = menuPrice + getToppingPrice();
        menusMain.setIngredients(selectedIngredients);
        menusMain.setToppings(selectedTopping);
        menusMain.setSauces(selectedSauces);
        menusMain.setPrice(totalPrice);
        menusMain.setName(menuName);
        menusMainList.add(menusMain);
        menusDrinkList.add(selectedDrink);
        menus.setSizeId(sizeID);
        menus.setMains(menusMainList);
        menus.setDrinks(menusDrinkList);
        menus.setPrice((int) totalPrice);
        menus.setName(menuName);
        menus.setRestaurantId(restaurantid);
    }


    public void confirm(View v) {
        if (selectedDrink == null){
            Toast.makeText(CustomActivity.this,"Choisir une boisson",Toast.LENGTH_SHORT).show();
        }else {
        createMenu();
        CartItem cartItem = new CartItem(selectedDrink,menus,1,totalPrice,menus.getRestaurantId());
        ShoppingCart shoppingCart = new ShoppingCart();
        if (ShoppingCart.getCart(CustomActivity.this).size() > 0){
            if (ShoppingCart.getCart(CustomActivity.this).get(0).restoid == cartItem.restoid){
                shoppingCart.addItem(cartItem,CustomActivity.this);
                Intent intent = new Intent(CustomActivity.this, OrderInCartActivity.class);
                intent.putExtra("FROMCUSTOM",true);
                startActivity(intent);
            }else {
                ShoppingCart.clearCart(ShoppingCart.getCart(CustomActivity.this),CustomActivity.this);
                shoppingCart.addItem(cartItem,CustomActivity.this);
                Intent intent = new Intent(CustomActivity.this, OrderInCartActivity.class);
                intent.putExtra("FROMCUSTOM",true);
                startActivity(intent);
            }
        }else {
            shoppingCart.addItem(cartItem, v.getContext());
            Intent intent = new Intent(CustomActivity.this, OrderInCartActivity.class);
            intent.putExtra("FROMCUSTOM",true);
            startActivity(intent);
        }

        }

    }

    public void nextView(View v){
        if (pager.getCurrentItem() < 3){
            pager.setCurrentItem(pager.getCurrentItem()+1);

        }

        checkNextItem();


    }
    public void previousView(View v){
        if (pager.getCurrentItem() >= 0){
            pager.setCurrentItem(pager.getCurrentItem()-1);
        }if (pager.getCurrentItem() == 0){
            onBackPressed();
            selectedIngredients.clear();
            if (selectedSauces != null){
                selectedSauces.clear();
            }
        }
        checkNextItem();

    }

    public void cancelButton(){
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
                selectedIngredients.clear();
                selectedSauces.clear();

            }
        });

    }


    private void checkNextItem(){

        if (pager.getCurrentItem() == 2){
            text_next.setVisibility(View.GONE);
            text_confirm.setVisibility(View.VISIBLE);

        }else {
            text_next.setVisibility(View.VISIBLE);
            text_confirm.setVisibility(View.GONE);
        }
    }





    private void showFragment(int fragmentIdentifier) {
        switch (fragmentIdentifier) {
            case FRAGMENT_SIZE:
                pager.setCurrentItem(FRAGMENT_SIZE);
                checkNextItem();
                break;
            case FRAGMENT_DRINKS:
                pager.setCurrentItem(FRAGMENT_DRINKS);
                checkNextItem();
                break;
            case FRAGMENT_SAUCE:
                layoutParams.gravity = Gravity.CENTER;
                checkNextItem();
                break;
        }
    }

    private double getToppingPrice(){
        if (!selectedTopping.isEmpty()) {
            for (int i = 0; i < selectedTopping.size(); i++) {
                toppingPrice += selectedTopping.get(i).getPrice();
            }
        }
        return toppingPrice;

    }

    @Override
    public void onSizePass(int size) {
        sizeID = size;
    }

    @Override
    public void onSaucePass(List<Sauce> sauces) {
        selectedSauces = sauces;
    }

    @Override
    public void onDrinkPass(Drink drink) {
        selectedDrink = drink;
    }
}




