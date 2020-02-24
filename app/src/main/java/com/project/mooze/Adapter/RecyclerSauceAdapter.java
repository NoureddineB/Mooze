package com.project.mooze.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.project.mooze.Model.Restaurent.Sauce;
import com.project.mooze.R;

import java.util.List;

public class RecyclerSauceAdapter extends RecyclerView.Adapter<RecyclerSauceHolder> {
    private List<Sauce> sauces;
    private RequestManager glide;


    // CONSTRUCTOR

    public RecyclerSauceAdapter(List<Sauce> sauces,RequestManager glide) {
        this.sauces = sauces;
        this.glide = glide;

    }


    @Override
    public RecyclerSauceHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_sauce_item, parent, false);
        return new RecyclerSauceHolder(view);

    }




    @Override
    public void onBindViewHolder(RecyclerSauceHolder viewHolder, int position) {
        viewHolder.updateCardUI(this.sauces.get(position),this.glide);

    }




    @Override

    public int getItemCount() {
        return this.sauces.size();

    }

    public Sauce getSauces(int position){
        return this.sauces.get(position);
    }
}
