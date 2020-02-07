package com.project.mooze.Adapter;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.project.mooze.Model.User;
import com.project.mooze.R;



public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView textView;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.main_title);

    }
    public void updateTitle(User user){
        this.textView.setText(user.getEmail());

    }
}
