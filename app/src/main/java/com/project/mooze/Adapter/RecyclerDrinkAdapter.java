package com.project.mooze.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.Model.Restaurent.Sauce;
import com.project.mooze.R;

import java.util.List;

public class RecyclerDrinkAdapter extends RecyclerView.Adapter<RecyclerDrinkHolder> {
private List<Drink> drinks;
private RequestManager glide;

        int index = -1;


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
        viewHolder.updateCardUI(this.drinks.get(position),this.glide);
        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
@Override
public void onClick(View view) {
        index = position;
        notifyDataSetChanged();
        }
        });
        if(index==position){
                if ( viewHolder.image_check.getVisibility() == View.INVISIBLE ){
        viewHolder.image_check.setVisibility(View.VISIBLE);
        }else{
        viewHolder.image_check.setVisibility(View.INVISIBLE);
        }
        }
        else{
        viewHolder.image_check.setVisibility(View.INVISIBLE);   //color on item unselecting item
        }

        }




@Override

public int getItemCount() {
        return this.drinks.size();

        }

public Drink getDrinks(int position){
        return this.drinks.get(position);
        }
}

