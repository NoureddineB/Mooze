package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import com.bumptech.glide.Glide;
import com.project.mooze.Adapter.IngredientAdapters.RecyclerDessertAdapter;
import com.project.mooze.Adapter.IngredientAdapters.RecyclerMainAdapter;
import com.project.mooze.Adapter.RecyclerMenuAdapter;
import com.project.mooze.Adapter.IngredientAdapters.RecyclerStarterAdapter;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.Model.Restaurent.RestaurantStarter;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;
import com.project.mooze.Utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class RestaurantActivity extends AppCompatActivity {


    private RecyclerView recycler_menu;
    private List<Menus> menus = new ArrayList<>();
    private List<RestaurantStarter> starters = new ArrayList<>();
    private List<Dessert> desserts = new ArrayList<>();
    private List<Main> mains = new ArrayList<>();
    private ImageView image_starter;
    private ImageView image_dessert;
    private ImageView image_mains;
    private ImageView image_menus;
    private TextView textView;

    private Disposable disposable;
    private int restaurentid;


    private RecyclerMenuAdapter recyclerMenuAdapter;
    private RecyclerStarterAdapter recyclerStarterAdapter;
    private RecyclerDessertAdapter recyclerDessertAdapter;
    private RecyclerMainAdapter recyclerMainAdapter;
    private RecyclerView.Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Utils.hideStatusBar(this);
        setContentView(R.layout.activity_restaurent);
        instantiate();
        getAllRestaurent();
        configureRecyclerView();
    }

    private void instantiate(){
        image_starter = findViewById(R.id.image_starter);
        image_mains = findViewById(R.id.image_main);
        image_menus = findViewById(R.id.image_menu);
        textView = findViewById(R.id.textView3);
        image_dessert = findViewById(R.id.image_dessert);
        recycler_menu = findViewById(R.id.recycler_menu_ingredients);
        this.recyclerMenuAdapter = new RecyclerMenuAdapter(this.menus, Glide.with(this));
        this.recyclerStarterAdapter = new RecyclerStarterAdapter(this.starters, Glide.with(this));
        this.recyclerDessertAdapter = new RecyclerDessertAdapter(this.desserts, Glide.with(this));
        this.recyclerMainAdapter = new RecyclerMainAdapter(this.mains, Glide.with(this));
        restaurentid = getIntent().getIntExtra(OrderActivity.restoID2,0);
        this.adapter = recyclerMenuAdapter;
    }




    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    public void onStarterClick(View v){
        image_menus.setActivated(false);
        image_dessert.setActivated(false);
        image_mains.setActivated(false);
        v.setActivated(!v.isActivated());

        getAllRestaurent();
        adapter = recyclerStarterAdapter;
        configureRecyclerView();



    }
    public void onMainClick(View v){
        image_menus.setActivated(false);
        image_dessert.setActivated(false);
        image_starter.setActivated(false);
        v.setActivated(!v.isActivated());

        getAllRestaurent();
        adapter = recyclerMainAdapter;
        configureRecyclerView();




    }
    public void onDessertClick(View v){
        image_menus.setActivated(false);
        image_starter.setActivated(false);
        image_mains.setActivated(false);
        v.setActivated(!v.isActivated());

        getAllRestaurent();
        adapter = recyclerDessertAdapter;
        configureRecyclerView();



    }
    public void onMenusClick(View v){
        image_starter.setActivated(false);
        image_dessert.setActivated(false);
        image_mains.setActivated(false);
        v.setActivated(!v.isActivated());

        getAllRestaurent();
        adapter = recyclerMenuAdapter;
        configureRecyclerView();




    }



    private void configureRecyclerView(){
        this.recycler_menu.setAdapter(this.adapter);
        recycler_menu.setLayoutManager(new LinearLayoutManager(this));
        Log.e("TAGMENUSRESTO15", String.valueOf(adapter));

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
        starters.addAll(restaurent.getRestaurantStarters());
        desserts.addAll(restaurent.getDesserts());
        mains.addAll(restaurent.getMains());
        textView.setText(restaurent.getName());
        adapter.notifyDataSetChanged();

}


}
