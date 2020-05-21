package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.bumptech.glide.Glide;
import com.project.mooze.Adapter.RecyclerHistoryAdapter;
import com.project.mooze.Adapter.RecyclerViewAdapter;
import com.project.mooze.Model.History.OrderDessert;
import com.project.mooze.Model.History.OrderDrink;
import com.project.mooze.Model.History.OrderHistory;
import com.project.mooze.Model.History.OrderMain;
import com.project.mooze.Model.History.OrderMenu;
import com.project.mooze.Model.History.OrderStarter;
import com.project.mooze.Model.History.OrderSuggestion;
import com.project.mooze.Model.Order.Starter;
import com.project.mooze.Model.Order.Suggestion;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class HistoryActivity extends AppCompatActivity {
    private Disposable disposable;
    private Disposable disposable_restaurant;
    private SharedPreferences preferences;
    private int restaurantid;
    private List<Menus> menus;
    private OrderHistory orderHistories;
    private List<Drink> drink;
    private List<Starter> starter;
    private List<Main> main;
    private List<Dessert> dessert;
    private List<Suggestion> suggestion;
    private RecyclerView recycler_history;
    private RecyclerHistoryAdapter recyclerHistoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        recycler_history = findViewById(R.id.recycler_history);
        preferences = this.getSharedPreferences("PREFS",0);
        getUsersOrders();
       configureRecyclerView();

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();


    }

    private void configureRecyclerView() {
        this.menus = new ArrayList<>();
        this.dessert = new ArrayList<>();
        this.main = new ArrayList<>();
        this.suggestion = new ArrayList<>();
        this.starter = new ArrayList<>();
        this.drink = new ArrayList<>();
        this.recyclerHistoryAdapter = new RecyclerHistoryAdapter(orderHistories,Glide.with(this));
        this.recycler_history.setAdapter(this.recyclerHistoryAdapter);
        recycler_history.setLayoutManager(new LinearLayoutManager(this));


    }

    private void getUsersOrders(){
        this.disposable = MoozeStreams.getUserOrder(1).subscribeWith(create());

    }



    private DisposableObserver<OrderHistory> create(){
        return new DisposableObserver<OrderHistory>() {
            @Override
            public void onNext(OrderHistory orderHistory) {
                orderHistories = orderHistory;


            }
            @Override
            public void onError(Throwable e) {
                Log.e("TAGORDER", "Error" + e);


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
