package com.project.mooze.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.Restaurent.Sauce;
import com.project.mooze.R;

class RecyclerSauceHolder extends RecyclerView.ViewHolder {

    private TextView sauce_name;
    private ImageView sauce_logo;

    public RecyclerSauceHolder(@NonNull View itemView) {
        super(itemView);
        sauce_name = itemView.findViewById(R.id.sauce_name);
        sauce_logo = itemView.findViewById(R.id.sauce_logo);

    }
    public void updateCardUI(Sauce sauce, RequestManager glide){
        this.sauce_name.setText(sauce.getName());
        glide.load(sauce.getImageUrl()).apply(RequestOptions.centerCropTransform()).into(sauce_logo);

    }
}
