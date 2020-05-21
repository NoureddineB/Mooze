package com.project.mooze.Adapter.MenuAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;

import com.project.mooze.Activity.IngredientsActivity;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.R;


import java.util.ArrayList;
import java.util.List;

public class RecyclerMenuAdapter extends RecyclerView.Adapter<RecyclerMenuHolder> {
// FOR DATA

    private List<Menus> menus;
    private RequestManager glide;
    private Context context;


// CONSTRUCTOR

    public RecyclerMenuAdapter(List<Menus> menus, RequestManager glide,Context context) {
        this.menus = menus;
        this.glide = glide;
        this.context = context;

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
    public void onBindViewHolder(RecyclerMenuHolder viewHolder, final int position) {
        viewHolder.updateUI(this.menus.get(position), this.glide);
        viewHolder.add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                Integer menuid = menus.get(position).getId();
                Intent intent = new Intent(context, IngredientsActivity.class);
                intent.putExtra("menusIngredientID",menuid);
                intent.putExtra("menuName",menus.get(position).getName());
                intent.putExtra("menuPrice",menus.get(position).getPrice());
                intent.putExtra("menuImage",menus.get(position).getImageUrl());
                context.startActivity(intent);
            }
        });


    }


// RETURN THE TOTAL COUNT OF ITEMS IN THE LIST

    @Override
    public int getItemCount() {
        return menus.size();

    }

    public Menus getMenus(int position) {
        return this.menus.get(position);
    }

    public void filterList(ArrayList<Menus> filterList){
        menus = filterList;
        notifyDataSetChanged();
    }
}


