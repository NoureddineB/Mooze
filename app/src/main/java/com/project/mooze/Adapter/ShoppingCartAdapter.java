package com.project.mooze.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.project.mooze.Model.CartItem;
import com.project.mooze.Model.Order.Starter;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.Model.Restaurent.RestaurantStarter;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.ShoppingCart;
import com.project.mooze.R;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartHolder>{
    private List<CartItem> cartItemList;
    private Context context;



    // CONSTRUCTOR

    public ShoppingCartAdapter(List<CartItem> cartItemList) {
        this.cartItemList = cartItemList;


    }


    @Override
    public ShoppingCartHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // CREATE VIEW HOLDER AND INFLATING ITS XML LAYOUT
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.recycler_shopping_item, parent, false);
        return new ShoppingCartHolder(view);

    }




    @Override
    public void onBindViewHolder(ShoppingCartHolder viewHolder, final int position) {
        viewHolder.updateShoppingList(this.cartItemList.get(position));
    }




    @Override

    public int getItemCount() {
        return this.cartItemList.size();

    }

    public CartItem getCartItem(int position){
        return this.cartItemList.get(position);
    }

}

class ShoppingCartHolder extends RecyclerView.ViewHolder {

    private TextView product_name;
    private TextView product_price;
    private TextView product_quantity;

     ShoppingCartHolder(@NonNull View itemView) {
        super(itemView);
        product_name = itemView.findViewById(R.id.text_maincart_name);
        product_price = itemView.findViewById(R.id.text_cartitem_price);
     }
    void updateShoppingList(CartItem cartItem){
         if (cartItem.object != null){
         if (cartItem.object instanceof Dessert){
             Dessert dessert = (Dessert) cartItem.object;
             product_name.setText(dessert.getName());
             product_price.setText(dessert.getPrice() + " €");

         }if (cartItem.object instanceof Menus){
            for (int i=0; i<(cartItem.menus.getMains().size());i++) {
                Menus menus = (Menus) cartItem.object;
                product_name.setText(menus.getMains().get(i).getName());
                product_price.setText(menus.getMains().get(i).getPrice()+ " €");
            }
         }

        if (cartItem.object instanceof  Main){
            Main main = (Main) cartItem.object;
            product_name.setText(main.getName());
            product_price.setText(main.getPrice() + " €");
        }
        if (cartItem.object instanceof RestaurantStarter){
            RestaurantStarter starter = (RestaurantStarter) cartItem.object;
            product_name.setText(starter.getName());
            product_price.setText(starter.getPrice() + " €");
        }

    }
     }
}