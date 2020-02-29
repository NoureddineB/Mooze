package com.project.mooze.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.project.mooze.Adapter.RecyclerIngredientsHolder;
import com.project.mooze.Model.Restaurent.Ingredient;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.R;

import java.util.List;

public class RecyclerIngredientsAdapter extends RecyclerView.Adapter<RecyclerIngredientsHolder> {

    private List<Ingredient> ingredients;
    private RequestManager glide;


    // CONSTRUCTOR

    public RecyclerIngredientsAdapter(List<Ingredient> ingredients,RequestManager glide) {
        this.ingredients = ingredients;
        this.glide = glide;

    }


    @Override
    public RecyclerIngredientsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_ingredients_item, parent, false);
        return new RecyclerIngredientsHolder(view);

    }




    @Override
    public void onBindViewHolder(RecyclerIngredientsHolder viewHolder, int position) {
        viewHolder.updateIngredients(this.ingredients.get(position),this.glide);

    }




    @Override

    public int getItemCount() {
        return this.ingredients.size();

    }

    public Ingredient getRestaurents(int position){
        return this.ingredients.get(position);
    }
}
