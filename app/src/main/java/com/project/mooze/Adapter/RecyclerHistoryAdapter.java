package com.project.mooze.Adapter;

import android.content.Context;
import android.location.Location;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.History.OrderHistory;
import com.project.mooze.Model.Order.Starter;
import com.project.mooze.Model.Order.Suggestion;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerHistoryAdapter extends RecyclerView.Adapter<RecyclerHistoryAdapter.RecyclerHistoryHolder> {
    private OrderHistory orderHistories;
    private RequestManager glide;



    // CONSTRUCTOR

    public RecyclerHistoryAdapter(OrderHistory orderHistories,RequestManager glide) {
        this.orderHistories= orderHistories;
        this.glide = glide;

    }


    @Override
    public RecyclerHistoryAdapter.RecyclerHistoryHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_menu_item, parent, false);
        return new RecyclerHistoryAdapter.RecyclerHistoryHolder(view);

    }




    @Override
    public void onBindViewHolder(RecyclerHistoryAdapter.RecyclerHistoryHolder viewHolder, int position) {
        viewHolder.updateCardUI(this.orderHistories,this.glide);

    }




    @Override

    public int getItemCount() {
        return this.orderHistories.getOrderDesserts().size() + this.orderHistories.getOrderDrinks().size() + this.orderHistories.getOrderSuggestions().size() + this.orderHistories.getOrderStarters().size() +this.orderHistories.getOrderMains().size() + this.orderHistories.getOrderMenus().size();

    }

    public OrderHistory getOrderHistory(int position){
        return this.orderHistories;
    }




    static class RecyclerHistoryHolder extends RecyclerView.ViewHolder {

        private TextView text_menu;
        private TextView text_price;
        private TextView text_topping;
        public Button add_button;
        private ImageView image_view_menu;
        private List<Menus> menus;

        private List<Drink> drink;
        private List<Starter> starter;
        private List<Main> main;
        private List<Dessert> dessert;
        private List<Suggestion> suggestion;

        public RecyclerHistoryHolder(@NonNull View itemView) {
            super(itemView);
            text_menu = itemView.findViewById(R.id.text_menus_name);
            add_button = itemView.findViewById(R.id.button_add);
            text_topping = itemView.findViewById(R.id.text_menus_topping);
            text_price = itemView.findViewById(R.id.text_price);
            image_view_menu = itemView.findViewById(R.id.image_view_menu);


        }

        private void selectOrderHistory(OrderHistory orderHistory){
            for (int i = 0; i < orderHistory.getOrderMenus().size(); i++) {
                menus.add(orderHistory.getOrderMenus().get(i).getMenu());
            }
            for (int i = 0; i < orderHistory.getOrderStarters().size(); i++) {
                starter.add(orderHistory.getOrderStarters().get(i).getStarter());
            }
            for (int i = 0; i < orderHistory.getOrderMains().size(); i++) {
                main.add(orderHistory.getOrderMains().get(i).getMain());
            }
            for (int i = 0; i < orderHistory.getOrderSuggestions().size(); i++) {
                suggestion.add(orderHistory.getOrderSuggestions().get(i).getSuggestion());
            }
            for (int i = 0; i < orderHistory.getOrderDesserts().size(); i++) {
                dessert.add(orderHistory.getOrderDesserts().get(i).getDessert());
            }
            for (int i = 0; i < orderHistory.getOrderDrinks().size(); i++) {
                drink.add(orderHistory.getOrderDrinks().get(i).getDrink());
            }

        }
        public <T> void updateCardUI(OrderHistory orderHistory, RequestManager glide){
            selectOrderHistory(orderHistory);
            for (int i = 0; i < menus.size(); i++) {
                this.text_menu.setText(menus.get(i).getName());
                this.text_topping.setText(menus.get(i).getDescription());
                this.text_price.setText((menus.get(i).getPrice() + "" + "€"));
                glide.load(menus.get(i).getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_view_menu);
            }

            }
    /* for (t instanceof Starter){
                this.text_menu.setText(((Starter) t).getName());
                this.text_topping.setText(((Starter) t).getDescription());
                this.text_price.setText(((Starter) t).getPrice() + "" + "€");
                glide.load(((Starter) t).getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_view_menu);
            }
            if (t instanceof Main){
                this.text_menu.setText(((Main) t).getName());
                this.text_topping.setText((((Main) t).getDescription()));
                this.text_price.setText(((Main) t).getPrice() + "" + "€");
                glide.load(((Main) t).getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_view_menu);
            }
            if (t instanceof Dessert){
                this.text_menu.setText(((Dessert) t).getName());
                this.text_topping.setText((((Dessert) t).getDescription()));
                this.text_price.setText(((Dessert) t).getPrice() + "" + "€");
                glide.load(((Dessert) t).getImageUrl()).apply(RequestOptions.centerCropTransform()).into(image_view_menu);
            }*/
    }
}
