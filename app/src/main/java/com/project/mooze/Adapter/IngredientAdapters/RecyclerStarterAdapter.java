package com.project.mooze.Adapter.IngredientAdapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.project.mooze.Adapter.RecyclerMenuHolder;
import com.project.mooze.Model.Order.Starter;
import com.project.mooze.Model.Restaurent.RestaurantStarter;
import com.project.mooze.R;

import java.util.List;

public class RecyclerStarterAdapter extends RecyclerView.Adapter<RecyclerMenuHolder> {
// FOR DATA

    private List<RestaurantStarter> menus;
    private RequestManager glide;

// CONSTRUCTOR

    public RecyclerStarterAdapter(List<RestaurantStarter> menus, RequestManager glide) {
        this.menus = menus;
        this.glide = glide;

    }


    @Override
    public RecyclerMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_menu_item, parent, false);
        return new RecyclerMenuHolder(view);

    }


// UPDATE VIEW HOLDER WITH A GITHUBUSER

    @Override
    public void onBindViewHolder(RecyclerMenuHolder viewHolder, int position) {
        viewHolder.updateUI(this.menus.get(position), this.glide);


    }


// RETURN THE TOTAL COUNT OF ITEMS IN THE LIST

    @Override
    public int getItemCount() {
        return menus.size();

    }

    public RestaurantStarter getRestaurent(int position) {
        return this.menus.get(position);
    }
}
