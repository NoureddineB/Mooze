package com.project.mooze.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.project.mooze.Model.User;
import com.project.mooze.R;

import java.util.List;

public class RecyclerOffersAdapter extends RecyclerView.Adapter<RecyclerViewHolder> {
    // FOR DATA

    private List<User> users;


    // CONSTRUCTOR

    public RecyclerOffersAdapter(List<User> users) {
        this.users = users;

    }


    @Override
    public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_main_item, parent, false);
        return new RecyclerViewHolder(view);

    }


    // UPDATE VIEW HOLDER WITH A GITHUBUSER

    @Override
    public void onBindViewHolder(RecyclerViewHolder viewHolder, int position) {
        viewHolder.updateTitle(this.users.get(position));

    }


    // RETURN THE TOTAL COUNT OF ITEMS IN THE LIST

    @Override

    public int getItemCount() {
        return this.users.size();

    }

    public User getUser(int position){
        return this.users.get(position);
    }
}
