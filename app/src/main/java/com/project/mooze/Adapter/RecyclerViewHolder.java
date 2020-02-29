package com.project.mooze.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.User.User;
import com.project.mooze.R;



public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private TextView text_restaurent_name;
    private TextView text_distance;
    private ImageView image_logo;

    public RecyclerViewHolder(@NonNull View itemView) {
        super(itemView);
        text_restaurent_name = itemView.findViewById(R.id.text_restaurent_name);
        text_distance = itemView.findViewById(R.id.text_distance);
        image_logo = itemView.findViewById(R.id.fragment_main_item_image);

    }
    public void updateCardUI(Restaurent restaurent, RequestManager glide){
        this.text_restaurent_name.setText(restaurent.getName());
        glide.load(restaurent.getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_logo);

    }
}
