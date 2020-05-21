package com.project.mooze.Adapter;

import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.math.MathUtils;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.Restaurent.Ingredient;
import com.project.mooze.Model.Restaurent.Topping;
import com.project.mooze.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerToppingAdapter extends RecyclerView.Adapter<RecyclerToppingAdapter.RecyclerToppingHolder> {



    private List<Topping> toppings;
    private RequestManager glide;


    private List<Topping> selectedToppings = new ArrayList<>();

    // CONSTRUCTOR

    public RecyclerToppingAdapter(List<Topping> toppings,RequestManager glide) {
        this.toppings = toppings;
        this.glide = glide;

    }



    @Override
    public RecyclerToppingAdapter.RecyclerToppingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_ingredients_item, parent, false);
        return new RecyclerToppingAdapter.RecyclerToppingHolder(view);

    }

    @Override
    public void onBindViewHolder(final RecyclerToppingAdapter.RecyclerToppingHolder viewHolder, final int position) {
        viewHolder.updateTopping(toppings.get(position),glide);
        viewHolder.price.setText(toppings.get(position).getPrice() + " €");
        if (selectedToppings.contains(toppings.get(position))){
            viewHolder.quantity.setText("1");
        }else {
            viewHolder.quantity.setText("0");

        }
        viewHolder.add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.quantity.setText("1");
                if (toppings != null) {
                    if (!selectedToppings.contains(toppings.get(position))) {
                        selectedToppings.add(toppings.get(position));

                    }
                }


            }
        });
        viewHolder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewHolder.quantity.setText("0");
                selectedToppings.remove(toppings.get(position));
                if (toppings != null) {
                    selectedToppings.remove(toppings.get(position));
                    viewHolder.price.setText("0.00 €");

                }

            }
        });

    }




    @Override
    public int getItemCount() {
        return this.toppings.size();

    }

    public Topping getToppings(int position){
        return this.toppings.get(position);
    }

    public List<Topping> getToppingQuantity(){
        return  selectedToppings;
    }

    public static class RecyclerToppingHolder extends RecyclerView.ViewHolder {


        private TextView text_ingredients_name;
        public ImageView add;
        public ImageView remove;
        public TextView quantity;
        public TextView price;
        private ImageView image_menu;

        public RecyclerToppingHolder(@NonNull View itemView) {
            super(itemView);
            text_ingredients_name = itemView.findViewById(R.id.text_ingredients_menuname);
            add = itemView.findViewById(R.id.add_ingredient);
            remove = itemView.findViewById(R.id.remove_ingredient);
            price = itemView.findViewById(R.id.text_price_ingredients);
            quantity = itemView.findViewById(R.id.quantity);
            image_menu = itemView.findViewById(R.id.image_view_ingredients);

        }
        public  void updateTopping(Topping topping, RequestManager glide) {
            text_ingredients_name.setText(topping.getName());
            glide.load(topping.getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_menu);


        }



    }
}
