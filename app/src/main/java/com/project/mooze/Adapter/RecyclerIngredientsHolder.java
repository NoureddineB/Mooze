package com.project.mooze.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.Restaurent.Ingredient;

import com.project.mooze.R;

public class RecyclerIngredientsHolder extends RecyclerView.ViewHolder {


    private TextView text_ingredients_name;

    private ImageView image_menu;

    public RecyclerIngredientsHolder(@NonNull View itemView) {
        super(itemView);
        text_ingredients_name = itemView.findViewById(R.id.text_ingredients_menuname);
        image_menu = itemView.findViewById(R.id.image_menu_ingredients);

    }
    public void updateIngredients(Ingredient ingredient, RequestManager glide){
        text_ingredients_name.setText(ingredient.getName());
        if (image_menu != null)
        glide.load(ingredient.getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_menu);

    }
}
