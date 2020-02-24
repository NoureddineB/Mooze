package com.project.mooze.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Adapter.RecyclerMenuHolder;
import com.project.mooze.Model.Order.Starter;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.R;


import java.util.ArrayList;
import java.util.List;

public class RecyclerMenuAdapter extends RecyclerView.Adapter<RecyclerMenuHolder> {
// FOR DATA

    private List<Menus> menus;
    private RequestManager glide;

// CONSTRUCTOR

    public RecyclerMenuAdapter(List<Menus> menus, RequestManager glide) {
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

    public Menus getRestaurent(int position) {
        return this.menus.get(position);
    }
}


