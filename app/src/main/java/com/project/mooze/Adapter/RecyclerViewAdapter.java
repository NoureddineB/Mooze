package com.project.mooze.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.User.User;
import com.project.mooze.R;

import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    // FOR DATA

    private List<Restaurent> restaurents;
    private RequestManager glide;


    // CONSTRUCTOR

    public RecyclerViewAdapter(List<Restaurent> restaurents,RequestManager glide) {
        this.restaurents = restaurents;
        this.glide = glide;

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_main_item, parent, false);
        return new RecyclerViewHolder(view);

    }




    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        viewHolder.updateCardUI(this.restaurents.get(position),this.glide);

    }




    @Override

    public int getItemCount() {
        return this.restaurents.size();

    }

    public Restaurent getRestaurents(int position){
        return this.restaurents.get(position);
    }
}
