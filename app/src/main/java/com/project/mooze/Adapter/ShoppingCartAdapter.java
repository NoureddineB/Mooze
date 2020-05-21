package com.project.mooze.Adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.project.mooze.Activity.ShoppingCartActivity;
import com.project.mooze.Model.CartItem;
import com.project.mooze.Model.Order.Order;
import com.project.mooze.Model.Order.OrderComment;
import com.project.mooze.Model.Order.Starter;
import com.project.mooze.Model.Order.Suggestion;
import com.project.mooze.Model.Restaurent.Dessert;
import com.project.mooze.Model.Restaurent.Drink;
import com.project.mooze.Model.Restaurent.Ingredient;
import com.project.mooze.Model.Restaurent.Main;
import com.project.mooze.Model.Restaurent.Menus;
import com.project.mooze.Model.Restaurent.RestaurantStarter;
import com.project.mooze.Model.Restaurent.Restaurent;
import com.project.mooze.Model.ShoppingCart;
import com.project.mooze.R;

import org.bouncycastle.asn1.cms.PasswordRecipientInfo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartHolder> {
    private List<CartItem> cartItemList;
    private Context context;
    private double totalPrice;
    private Order order;
    private boolean takeaway;
    private int countdown;
    private int userid;
    private double price;
    private String orderComments;
    private List<Menus> menus = new ArrayList<>();
    private List<Starter> starters = new ArrayList<>();
    private List<Suggestion> suggestions = new ArrayList<>();
    private List<Main> mains = new ArrayList<>();
    private List<Dessert> desserts = new ArrayList<>();
    private List<Drink> drinks = new ArrayList<>();
    private SharedPreferences preferences;
    onPriceChange priceChange;


    public interface onPriceChange {
        void onPriceChange(double price, Order order);
    }


    // CONSTRUCTOR

    public ShoppingCartAdapter(List<CartItem> cartItemList, Context context) {
        this.cartItemList = cartItemList;
        this.context = context;


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
        preferences = context.getSharedPreferences("PREFS", 0);
        countdown = preferences.getInt("COUNTDOWN", 0);
        takeaway = preferences.getBoolean("TAKEAWAY", true);
        userid = preferences.getInt("USERID", 0);
        orderComments = preferences.getString("Comment",null);
        final OrderComment orderComment = new OrderComment(orderComments);
        viewHolder.image_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShoppingCart shoppingCart = new ShoppingCart();
                shoppingCart.removeItem(cartItemList.get(position), context);
                cartItemList.remove(cartItemList.get(position));
                for (int i = 0; i < cartItemList.size(); i++) {
                    totalPrice = totalPrice + cartItemList.get(i).price;
                }
                if (cartItemList.size() == 0) {
                    totalPrice = 0.00;
                }
                notifyItemRemoved(position);
                for (CartItem cartItem : ShoppingCart.getCart(context)) {
                    menus.add(cartItem.menus);
                    starters.add(cartItem.starter);
                    mains.add(cartItem.main);
                    desserts.add(cartItem.dessert);
                    drinks.add(cartItem.drink);
                    suggestions.add(cartItem.suggestion);
                    price = price + cartItem.price;
                }
                order = new Order(userid, countdown, takeaway, starters, drinks, mains, desserts, suggestions, menus, price,orderComment);
                priceChange = (onPriceChange) context;
                priceChange.onPriceChange(totalPrice, order);
                notifyDataSetChanged();
            }
        });
    }


    @Override

    public int getItemCount() {
        return this.cartItemList.size();

    }

    public CartItem getCartItem(int position) {
        return this.cartItemList.get(position);
    }

}

class ShoppingCartHolder extends RecyclerView.ViewHolder {

    private TextView product_name;
    private TextView product_price;
    private TextView text_ingredients1;
    private TextView text_ingredients2;
    private TextView text_ingredients3;
    private List<Ingredient> selectedingredients = new ArrayList<>();
    private List<String> selectedingredientsName = new ArrayList<>();
    private List<Ingredient> ingredients;
    private List<Ingredient> unselectedingredients;
    private TextView text_drinks;
    public ImageView image_close;
    private SharedPreferences preferences;

    ShoppingCartHolder(@NonNull View itemView) {
        super(itemView);
        product_name = itemView.findViewById(R.id.text_maincart_name);
        product_price = itemView.findViewById(R.id.text_cartitem_price);
        text_ingredients1 = itemView.findViewById(R.id.text_ingredient1);
        text_ingredients2 = itemView.findViewById(R.id.text_ingredient2);
        text_ingredients3 = itemView.findViewById(R.id.text_ingredient3);
        preferences = itemView.getContext().getSharedPreferences("PREFS", 0);
        String json = preferences.getString("IngredientList", "");
        Type type = new TypeToken<List<Ingredient>>(){}.getType();
        Gson gson = new Gson();
        ingredients = gson.fromJson(json,type);
        text_drinks = itemView.findViewById(R.id.text_menu_drink);
        image_close = itemView.findViewById(R.id.image_close);

    }

    void updateShoppingList(CartItem cartItem) {
        if (cartItem.object != null) {
            if (cartItem.object instanceof Dessert) {
                Dessert dessert = (Dessert) cartItem.object;
                product_name.setText(dessert.getName());
                product_price.setText(String.format("%.2f", dessert.getPrice()) + " €");

            }
            if (cartItem.object instanceof Menus) {
                Menus menus = (Menus) cartItem.object;
                selectedingredients = menus.getMains().get(0).getIngredients();
                for (Ingredient ingredient : selectedingredients){
                    selectedingredientsName.add(ingredient.getName());
                }
                product_name.setText(menus.getMains().get(0).getName());
                setVisibility();
                text_drinks.setText(menus.getDrinks().get(0).getName());
               if (selectedingredientsName.contains(ingredients.get(0).getName())) {
                    text_ingredients1.setText("(1) " + ingredients.get(0).getName());
                } else {
                    text_ingredients1.setText("(sans) " + ingredients.get(0).getName());
                }
                if (selectedingredientsName.contains(ingredients.get(1).getName())) {
                    text_ingredients2.setText("(1) " + ingredients.get(1).getName());
                } else {
                    text_ingredients2.setText("(sans) " + ingredients.get(1).getName());
                }
                if (selectedingredientsName.contains(ingredients.get(2).getName())) {
                    text_ingredients3.setText("(1) " + ingredients.get(2).getName());
                } else {
                    text_ingredients3.setText("(sans) " + ingredients.get(2).getName());
                }
                product_price.setText(String.format("%.2f", menus.getMains().get(0).getPrice()) + " €");

            }

            if (cartItem.object instanceof Main) {
                setVisibilityGone();
                Main main = (Main) cartItem.object;
                product_name.setText(main.getName());
                product_price.setText(String.format("%.2f", main.getPrice()) + " €");
            }

            if (cartItem.object instanceof Starter) {
                setVisibilityGone();
                Starter starter = (Starter) cartItem.object;
                product_name.setText(starter.getName());
                product_price.setText(String.format("%.2f", starter.getPrice()) + " €");
            }
            if (cartItem.object instanceof Suggestion) {
                setVisibilityGone();
                Suggestion suggestion = (Suggestion) cartItem.object;
                product_name.setText(suggestion.getName());
                product_price.setText(String.format("%.2f", suggestion.getPrice()) + " €");
            }

        }
    }

    private void setVisibilityGone() {
        text_drinks.setVisibility(View.GONE);
        text_ingredients1.setVisibility(View.GONE);
        text_ingredients2.setVisibility(View.GONE);
        text_ingredients3.setVisibility(View.GONE);
    }

    private void setVisibility() {
        text_drinks.setVisibility(View.VISIBLE);
        text_ingredients1.setVisibility(View.VISIBLE);
        text_ingredients2.setVisibility(View.VISIBLE);
        text_ingredients3.setVisibility(View.VISIBLE);
    }
}