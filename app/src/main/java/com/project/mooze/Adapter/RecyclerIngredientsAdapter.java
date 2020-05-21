package com.project.mooze.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.Restaurent.Ingredient;
import com.project.mooze.Model.Restaurent.Topping;
import com.project.mooze.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerIngredientsAdapter extends RecyclerView.Adapter<RecyclerIngredientsAdapter.RecyclerIngredientsHolder> {


    private List<Ingredient> ingredients;
    private RequestManager glide;
    private List<Ingredient> selectedIngredients = new ArrayList<>();


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
    public void onBindViewHolder(final RecyclerIngredientsHolder viewHolder, final int position) {
        viewHolder.updateIngredients(ingredients.get(position),glide);
        if (selectedIngredients.isEmpty()) {
            selectedIngredients.addAll(ingredients);
        }
        if (selectedIngredients.contains(ingredients.get(position))){
            viewHolder.quantity.setText("1");
        }else {
            viewHolder.quantity.setText("0");
        }
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.quantity.setText("1");
                    if (!selectedIngredients.contains(ingredients.get(position))) {
                        selectedIngredients.add(ingredients.get(position));
                    }




            }
        });
        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.quantity.setText("0");
                Log.d("TAGSAMARCHEUE","sa marthce");
                selectedIngredients.remove(ingredients.get(position));


            }
        });

    }




    @Override
    public int getItemCount() {
        return this.ingredients.size();

    }

    public Ingredient getIngredients(int position){
        return this.ingredients.get(position);
    }

    public List<Ingredient> getQuantity(){
        return  selectedIngredients;
    }

    public static class RecyclerIngredientsHolder extends RecyclerView.ViewHolder {


        private TextView text_ingredients_name;
        public ImageView add;
        public ImageView remove;
        public TextView quantity;
        public TextView price;
        private ImageView image_menu;

        public RecyclerIngredientsHolder(@NonNull View itemView) {
            super(itemView);
            text_ingredients_name = itemView.findViewById(R.id.text_ingredients_menuname);
            add = itemView.findViewById(R.id.add_ingredient);
            remove = itemView.findViewById(R.id.remove_ingredient);
            price = itemView.findViewById(R.id.text_price_ingredients);
            quantity = itemView.findViewById(R.id.quantity);
            image_menu = itemView.findViewById(R.id.image_view_ingredients);

        }
        public  void updateIngredients(Ingredient ingredient, RequestManager glide) {
            text_ingredients_name.setText(ingredient.getName());
            glide.load(ingredient.getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_menu);


        }



    }
}



