package com.project.mooze.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.project.mooze.Adapter.IngredientAdapters.RecyclerIngredientsAdapter;
import com.project.mooze.Model.Restaurent.Ingredient;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.R;
import com.project.mooze.Utils.MoozeStreams;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public class IngredientsActivity extends AppCompatActivity {

    private RecyclerView recycler_ingredients;
    private List<Ingredient> ingredients;
    private Disposable disposable;
    private int restaurant_id;
    private int mains_id = 1;
    private int menu_id = 1;
    private RecyclerIngredientsAdapter recyclerIngredientsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        recycler_ingredients = findViewById(R.id.recycler_menu_ingredients);
        configureRecyclerView();
        getAllIngredients();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.disposeWhenDestroy();

    }

    private void configureRecyclerView(){
        this.ingredients = new ArrayList<>();
        this.recyclerIngredientsAdapter = new RecyclerIngredientsAdapter(this.ingredients, Glide.with(this));
        this.recycler_ingredients.setAdapter(this.recyclerIngredientsAdapter);
        recycler_ingredients.setLayoutManager(new LinearLayoutManager(IngredientsActivity.this));

    }
    private void getAllIngredients(){
        this.disposable = MoozeStreams.getAllRestaurent().subscribeWith(create());

    }


    private DisposableObserver<List<Restaurent>> create(){
        return new DisposableObserver<List<Restaurent>>() {
            @Override
            public void onNext(List<Restaurent> restaurents) {
                updateIngredients(restaurents.get(0).getMenus().get(0).getMains().get(0).getIngredients());
                Log.e("TAGF", String.valueOf(restaurents));

            }
            @Override
            public void onError(Throwable e) {
                Log.e("TAGMAINFRAGMENTERROR", "Error" + e);


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
}
