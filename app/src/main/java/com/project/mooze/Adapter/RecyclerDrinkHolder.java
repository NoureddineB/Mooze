package com.project.mooze.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Fragment.DrinkFragment;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.Model.Restaurent.Sauce;
import com.project.mooze.R;

class
RecyclerDrinkHolder extends RecyclerView.ViewHolder {
    private TextView drink_name;
    private ImageView drink_logo;
    public ConstraintLayout constraintLayout;
    public ImageView image_check;



    public RecyclerDrinkHolder(@NonNull View itemView) {
        super(itemView);
        drink_name = itemView.findViewById(R.id.sauce_name);
        drink_logo = itemView.findViewById(R.id.sauce_logo);
        image_check = itemView.findViewById(R.id.image_check);
        constraintLayout = itemView.findViewById(R.id.constraint_drink_layout);

    }
    public void updateCardUI(Drink drink, RequestManager glide){
        this.drink_name.setText(drink.getName());
        if (drink.getName() == "Coca Cola") {
            glide.load(R.drawable.coca).apply(RequestOptions.centerCropTransform()).into(drink_logo);
        }else{
            glide.load(drink.getImageUrl()).apply(RequestOptions.centerCropTransform()).into(drink_logo);
        }


    }
}
