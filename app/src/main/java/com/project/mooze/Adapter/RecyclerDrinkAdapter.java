package com.project.mooze.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.R;

import java.util.List;

public class RecyclerDrinkAdapter extends RecyclerView.Adapter<RecyclerDrinkAdapter.RecyclerDrinkHolder> {
    private List<Drink> drinks;
    private RequestManager glide;





// CONSTRUCTOR

    public RecyclerDrinkAdapter(List<Drink> drinks, RequestManager glide) {
        this.drinks = drinks;
        this.glide = glide;

    }


    @Override
    public RecyclerDrinkHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_drink_item, parent, false);

        return new RecyclerDrinkHolder(view);

    }


    @Override
    public void onBindViewHolder(RecyclerDrinkHolder viewHolder, final int position) {
        viewHolder.updateCardUI(this.drinks.get(position), this.glide);

    }


    @Override

    public int getItemCount() {
        return this.drinks.size();

    }

    public Drink getDrinks(int position) {
        return this.drinks.get(position);
    }


    static class RecyclerDrinkHolder extends RecyclerView.ViewHolder {
        private TextView drink_name;
        public ImageView drink_logo;
        public ConstraintLayout constraintDrink;
        public ImageView image_check;



        public RecyclerDrinkHolder(@NonNull View itemView) {
            super(itemView);
            drink_name = itemView.findViewById(R.id.drink_name);
            drink_logo = itemView.findViewById(R.id.drink_logo);
            image_check = itemView.findViewById(R.id.image_check);
            constraintDrink = itemView.findViewById(R.id.constraint_drink_layout);

        }
        public void updateCardUI(Drink drink, RequestManager glide){
            this.drink_name.setText(drink.getName());
            glide.load(drink.getImageUrl()).apply(RequestOptions.centerCropTransform()).into(drink_logo);
        }
    }
}

