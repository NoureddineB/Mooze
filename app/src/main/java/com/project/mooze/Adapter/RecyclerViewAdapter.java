package com.project.mooze.Adapter;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder> {
    // FOR DATA

    private List<Restaurent> restaurents;

    private RequestManager glide;
    private Location userLocation;


    // CONSTRUCTOR

    public RecyclerViewAdapter(Location userLocation,List<Restaurent> restaurents,RequestManager glide) {
        this.restaurents = restaurents;
        this.userLocation = userLocation;
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
        viewHolder.updateCardUI(userLocation,this.restaurents.get(position),this.glide);

    }




    @Override

    public int getItemCount() {
        return this.restaurents.size();

    }

    public Restaurent getRestaurents(int position){
        return this.restaurents.get(position);
    }


   public void filterList(ArrayList<Restaurent> filterList){
        restaurents = filterList;
        notifyDataSetChanged();
   }



    static class RecyclerViewHolder extends RecyclerView.ViewHolder {

        private TextView text_restaurent_name;
        private TextView text_distance;
        private ImageView image_logo;
        private Location restaurantLocation = new Location("");
        private String distance;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            text_restaurent_name = itemView.findViewById(R.id.text_restaurent_name);
            text_distance = itemView.findViewById(R.id.text_distance);
            image_logo = itemView.findViewById(R.id.fragment_main_item_image);

        }
        public void updateCardUI(Location userLocation,Restaurent restaurent, RequestManager glide){
            restaurantLocation.setLatitude(restaurent.getLatitude());
            restaurantLocation.setLongitude(restaurent.getLongitude());
            distance = String.valueOf(userLocation.distanceTo(restaurantLocation));
            this.text_distance.setText(distance.substring(0,4) + " m");
            this.text_restaurent_name.setText(restaurent.getName());
            glide.load(restaurent.getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_logo);

        }
    }
}
