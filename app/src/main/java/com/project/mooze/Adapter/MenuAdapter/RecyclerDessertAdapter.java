package com.project.mooze.Adapter.MenuAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.project.mooze.Activity.OrderInCartActivity;
import com.project.mooze.Model.CartItem;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.ShoppingCart;
import com.project.mooze.R;

import java.util.List;

public class RecyclerDessertAdapter extends RecyclerView.Adapter<RecyclerMenuHolder> {
// FOR DATA

    private List<Dessert> menus;
    private RequestManager glide;
    private Context context;

// CONSTRUCTOR

    public RecyclerDessertAdapter(List<Dessert> menus, RequestManager glide,Context context) {
        this.menus = menus;
        this.glide = glide;
        this.context = context;
    }


    @Override
    public RecyclerMenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_menu_item, parent, false);
        return new RecyclerMenuHolder(view);

    }


// UPDATE VIEW HOLDER WITH A GITHUBUSER

    @Override
    public void onBindViewHolder(RecyclerMenuHolder viewHolder, final int position) {
        viewHolder.updateUI(this.menus.get(position), this.glide);
        viewHolder.add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notifyDataSetChanged();
                CartItem cartItem = new CartItem(null,menus.get(position),1,menus.get(position).getPrice(),menus.get(position).getRestaurantId());
                ShoppingCart shoppingCart = new ShoppingCart();
                if (ShoppingCart.getCart(context).size() > 0){
                    if (ShoppingCart.getCart(context).get(0).restoid == cartItem.restoid){
                        shoppingCart.addItem(cartItem,view.getContext());
                        Intent intent = new Intent(context, OrderInCartActivity.class);
                        context.startActivity(intent);
                    }else {
                        ShoppingCart.clearCart(ShoppingCart.getCart(context),context);
                        shoppingCart.addItem(cartItem,view.getContext());
                        Intent intent = new Intent(context, OrderInCartActivity.class);
                        context.startActivity(intent);
                    }
                }else {
                    shoppingCart.addItem(cartItem,view.getContext());
                    Intent intent = new Intent(context, OrderInCartActivity.class);
                    context.startActivity(intent);
                }
            }

        });


    }


// RETURN THE TOTAL COUNT OF ITEMS IN THE LIST

    @Override
    public int getItemCount() {
        return menus.size();

    }

    public Dessert getRestaurent(int position) {
        return this.menus.get(position);
    }
}
