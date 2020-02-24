package com.project.mooze.Adapter;


import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.Order.Starter;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.R;

public class RecyclerMenuHolder extends RecyclerView.ViewHolder {

    private TextView text_menu;
    private TextView text_price;
    private TextView text_topping;
    private ImageView image_view_menu;

    public RecyclerMenuHolder(@NonNull View itemView) {
        super(itemView);
        text_menu = itemView.findViewById(R.id.text_menus_name);
        text_topping = itemView.findViewById(R.id.text_menus_topping);
        text_price = itemView.findViewById(R.id.text_price);
        image_view_menu = itemView.findViewById(R.id.image_view_menu);

    }

    public <T> void updateUI(T t, RequestManager glide){
        if (t instanceof Menus){
            for (int i=0; i<((Menus) t).getMains().size(); i++) {
                this.text_menu.setText(((Menus) t).getMains().get(i).getName());
                this.text_topping.setText(((Menus) t).getMains().get(i).getIngredients().get(i).getName());
                this.text_price.setText(((Menus) t).getPrice() + "" + "€");
                glide.load(((Menus) t).getMains().get(i).getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_view_menu);
            }}
        if (t instanceof Starter){
            this.text_menu.setText(((Starter) t).getName());
            this.text_topping.setText(((Starter) t).getIngredients().get(0).getName());
            this.text_price.setText(((Starter) t).getPrice() + "" + "€");
            glide.load(((Starter) t).getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_view_menu);
        }
        if (t instanceof Main){
            this.text_menu.setText(((Main) t).getName());
            this.text_price.setText(((Main) t).getPrice() + "" + "€");
            glide.load(((Main) t).getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_view_menu);
        }
        if (t instanceof Dessert){
            this.text_menu.setText(((Dessert) t).getName());
            this.text_price.setText(((Dessert) t).getPrice() + "" + "€");
            glide.load(((Dessert) t).getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_view_menu);
        }
        Log.e("TAGVALUEOFT",String.valueOf(t));

    }
}