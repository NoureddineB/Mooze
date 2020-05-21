package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.project.mooze.Adapter.MenuAdapter.RecyclerDessertAdapter;
import com.project.mooze.Adapter.MenuAdapter.RecyclerMainAdapter;
import com.project.mooze.Adapter.MenuAdapter.RecyclerMenuAdapter;
import com.project.mooze.Adapter.MenuAdapter.RecyclerStarterAdapter;
import com.project.mooze.Model.CartItem;
import com.project.mooze.Model.Order.Starter;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;

import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.ShoppingCart;
import com.project.mooze.R;

import com.project.mooze.Utils.ItemClickSupport;
import com.project.mooze.Utils.MoozeStreams;
import com.project.mooze.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RestaurantActivity extends AppCompatActivity {


    private RecyclerView recycler_menu;
    private List<Menus> menus = new ArrayList<>();
    private List<Starter> starters = new ArrayList<>();
    private List<Dessert> desserts = new ArrayList<>();
    private List<Main> mains = new ArrayList<>();
    private EditText search_edit;
    private ImageView image_starter;
    private ImageView image_dessert;
    private ImageView image_mains;
    private ImageView image_menus;
    private ImageView image_panier;
    private TextView textView;
    private CartItem cartItem;

    private Disposable disposable;
    private int restaurentid;


    private RecyclerMenuAdapter recyclerMenuAdapter;
    private RecyclerStarterAdapter recyclerStarterAdapter;
    private RecyclerDessertAdapter recyclerDessertAdapter;
    private RecyclerMainAdapter recyclerMainAdapter;
    private RecyclerView.Adapter adapter;
    private TextView cart_size;
    private SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideStatusBar(this);
        setContentView(R.layout.activity_restaurent);
        instantiate();
        getAllRestaurent();
        configureRecyclerView();
        configureOnClickRecyclerView();
        image_menus.setActivated(true);


    }

    private void instantiate(){
        image_starter = findViewById(R.id.image_starter);
        image_mains = findViewById(R.id.image_main);
        image_menus = findViewById(R.id.image_menu);
        textView = findViewById(R.id.textView3);
        search_edit = findViewById(R.id.search_menus);
        image_dessert = findViewById(R.id.image_dessert);
        recycler_menu = findViewById(R.id.recycler_menu_ingredients);
        image_panier = findViewById(R.id.imageView_panier);
        cart_size = findViewById(R.id.cart_size);
        this.recyclerMenuAdapter = new RecyclerMenuAdapter(this.menus, Glide.with(this),this);
        this.recyclerStarterAdapter = new RecyclerStarterAdapter(this.starters, Glide.with(this),this);
        this.recyclerDessertAdapter = new RecyclerDessertAdapter(this.desserts, Glide.with(this),this);
        this.recyclerMainAdapter = new RecyclerMainAdapter(this.mains, Glide.with(this),this);
        sharedPreferences = getSharedPreferences("PREFS",0);
        if (sharedPreferences.getInt("RESTOID",0) != 0){
        restaurentid = sharedPreferences.getInt("RESTOID",0);
        }else {
            restaurentid = getIntent().getIntExtra(OrderActivity.restoID2, 0);
        }
        this.adapter = recyclerMenuAdapter;
    }

    private void updateCartSize(){
        if (ShoppingCart.getShoppingCartSize(this) != 0){
            cart_size.setVisibility(View.VISIBLE);
            cart_size.setText(String.valueOf(ShoppingCart.getShoppingCartSize(this)));
        }
        if (ShoppingCart.getShoppingCartSize(this) == 0){
            cart_size.setVisibility(View.INVISIBLE);
        }
    }

    public void startShoppingCart(View v){
        Intent intent = new Intent(RestaurantActivity.this,ShoppingCartActivity.class);
        startActivity(intent);
        Log.e("CARTTAG",ShoppingCart.getCart(RestaurantActivity.this) + " ué");

    }

    private void configureOnClickRecyclerView(){
        ItemClickSupport.addTo(recycler_menu, R.layout.recycler_menu_item)
                .setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
                    @Override
                    public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                     updateCartSize();


                    }

                });

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }
    @Override
    public void onResume() {
        super.onResume();
        updateCartSize();

    }

    @Override
    public void onStart() {
        super.onStart();
        updateCartSize();

    }

    public void onStarterClick(View v){
        image_menus.setActivated(false);
        image_dessert.setActivated(false);
        image_mains.setActivated(false);
        v.setActivated(true);

        getAllRestaurent();
        adapter = recyclerStarterAdapter;
        configureRecyclerView();



    }
    public void onMainClick(View v){
        image_menus.setActivated(false);
        image_dessert.setActivated(false);
        image_starter.setActivated(false);
        v.setActivated(true);

        getAllRestaurent();
        adapter = recyclerMainAdapter;
        configureRecyclerView();




    }
    public void onDessertClick(View v){
        image_menus.setActivated(false);
        image_starter.setActivated(false);
        image_mains.setActivated(false);
        v.setActivated(true);

        getAllRestaurent();
        adapter = recyclerDessertAdapter;
        configureRecyclerView();



    }
    public void onMenusClick(View v){
        image_starter.setActivated(false);
        image_dessert.setActivated(false);
        image_mains.setActivated(false);
        v.setActivated(true);
        search();
        getAllRestaurent();
        adapter = recyclerMenuAdapter;
        configureRecyclerView();


    }

    private void filter(String text) {
        ArrayList<Menus> filteredList = new ArrayList<>();
        for (Menus menus : menus){
            if (menus.getName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(menus);
            }
        }

        recyclerMenuAdapter.filterList(filteredList);
    }
    private void search(){
        search_edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                filter(editable.toString());
            }
        });

    }



    private void configureRecyclerView(){
        this.recycler_menu.setAdapter(this.adapter);
        recycler_menu.setLayoutManager(new LinearLayoutManager(this));


    }



    private void getAllRestaurent() {
        this.disposable = MoozeStreams.getRestaurent(restaurentid).subscribeWith(create());
    }

    private void disposeWhenDestroy() {
        if (this.disposable != null && !this.disposable.isDisposed()) this.disposable.dispose();
    }

    private DisposableObserver<Restaurent> create() {
        return new DisposableObserver<Restaurent>() {
            @Override
            public void onNext(Restaurent restaurents) {
                updateUI(restaurents);





            }

            @Override
            public void onError(Throwable e) {
                Log.e("TAG", "Error" + e);


            }

            @Override
            public void onComplete() {


            }
        };
    }


    private void updateUI(Restaurent restaurent){
        menus.addAll(restaurent.getMenus());
        starters.addAll(restaurent.getStarters());
        desserts.addAll(restaurent.getDesserts());
        mains.addAll(restaurent.getMains());
        textView.setText(restaurent.getName());
        adapter.notifyDataSetChanged();

}


}
