package com.project.mooze.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.Restaurent.Sauce;
import com.project.mooze.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerSauceAdapter extends RecyclerView.Adapter<RecyclerSauceAdapter.RecyclerSauceHolder> {
    private List<Sauce> sauces;
    private List<Sauce> selectedSauces = new ArrayList<>();
    private RequestManager glide;
    int index = -1;


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
    public void onBindViewHolder(RecyclerSauceHolder viewHolder, final int position) {
        viewHolder.updateCardUI(this.sauces.get(position),this.glide);
        viewHolder.constraintLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                index = position;
                notifyDataSetChanged();
            }
        });
        if(index==position){
            if ( viewHolder.image_check.getVisibility() == View.INVISIBLE ){
            viewHolder.image_check.setVisibility(View.VISIBLE);
            selectedSauces.add(sauces.get(position));
                 }else{
                viewHolder.image_check.setVisibility(View.INVISIBLE);
                selectedSauces.remove(sauces.get(position));
            }
        }
        if (index != position && selectedSauces.size() == 3 ){
            viewHolder.image_check.setVisibility(View.INVISIBLE);   //color on item unselecting item
        }

    }




    @Override
    public int getItemCount() {
        return this.sauces.size();

    }

    public Sauce getSauces(int position){
        return this.sauces.get(position);
    }
    public List<Sauce> getSelectedSauces(){
        return selectedSauces;
    }

    static class RecyclerSauceHolder extends RecyclerView.ViewHolder {

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
}
