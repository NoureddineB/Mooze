package com.project.mooze.Adapter;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.Restaurent.Sauce;
import com.project.mooze.R;

class RecyclerSauceHolder extends RecyclerView.ViewHolder {

    private TextView sauce_name;
    private ImageView sauce_logo;
    public ConstraintLayout constraintLayout;
    public ImageView image_check;

    public RecyclerSauceHolder(@NonNull View itemView) {
        super(itemView);
        sauce_name = itemView.findViewById(R.id.sauce_name);
        sauce_logo = itemView.findViewById(R.id.sauce_logo);
        image_check = itemView.findViewById(R.id.image_check);
        constraintLayout = itemView.findViewById(R.id.constraint_sauce_layout);

    }
    public void updateCardUI(Sauce sauce, RequestManager glide){
        this.sauce_name.setText(sauce.getName());
        switch (sauce.getName()){
                case "Sauce moutarde miel":
                    glide.load(R.drawable.mayo).apply(RequestOptions.centerCropTransform()).into(sauce_logo);
                    break;
                case  "Sauce algérienne":
                    glide.load(R.drawable.ketchup).apply(RequestOptions.centerCropTransform()).into(sauce_logo);
                    break;
            }


    }
}
